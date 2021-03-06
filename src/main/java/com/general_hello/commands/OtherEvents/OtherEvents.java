package com.general_hello.commands.OtherEvents;

import com.general_hello.commands.Config;
import com.general_hello.commands.Listener;
import com.general_hello.commands.commands.GroupOfGames.Blackjack.GameHandler;
import com.general_hello.commands.commands.Info.InfoUserCommand;
import com.general_hello.commands.commands.Uno.ImageHandler;
import com.general_hello.commands.commands.Uno.UnoGame;
import com.general_hello.commands.commands.Uno.UnoHand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OtherEvents extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    public static int disconnectCount = 0;
    private static OffsetDateTime timeDisconnected = OffsetDateTime.now();

    @Override
    public void onDisconnect(@NotNull DisconnectEvent event) {
        timeDisconnected = event.getTimeDisconnected();
    }

    @Override
    public void onShutdown(@NotNull ShutdownEvent event) {
        System.out.println("Shut downed the bot at " +
                event.getTimeShutdown().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+ " due to maintenance.\n" +
                "With response number of " + event.getResponseNumber() + "\n" +
                "With the code of " + event.getCloseCode().getCode() + "\n");
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        if (!event.getUser().isBot()) handleUnoReaction(event.getMember(), event.retrieveMessage().complete(), event.getReactionEmote());
    }

    public void handleUnoReaction(Member member, Message message, MessageReaction.ReactionEmote emoji) {
        Guild guild = message.getGuild();
        UnoGame unoGame = GameHandler.getUnoGame(guild.getIdLong());
        if (emoji.isEmoji() && unoGame != null && message.getIdLong() == unoGame.getMessageID()) {
            ArrayList<UnoHand> hands = unoGame.getHands();
            switch (emoji.getEmoji()) {
                case "??????":
                    if (unoGame.getStarter() == member.getIdLong() && unoGame.getTurn() == -1) {
                        int turn = unoGame.start();
                        if (turn != -1) {
                            guild.createCategory("Uno")
                                    .addMemberPermissionOverride(guild.getSelfMember().getIdLong(), Collections.singletonList(Permission.VIEW_CHANNEL), Collections.emptyList())
                                    .addRolePermissionOverride(guild.getIdLong(), Collections.emptyList(), Collections.singletonList(Permission.VIEW_CHANNEL)).queue(category -> {
                                        unoGame.setCategory(category.getIdLong());
                                        guild.modifyCategoryPositions().selectPosition(category.getPosition()).moveTo(Math.min(guild.getCategories().size() - 1, 2)).queue();
                                        for (UnoHand hand : hands) {
                                            category.createTextChannel(String.format("%s-uno", hand.getPlayerName()))
                                                    .addMemberPermissionOverride(hand.getPlayerId(), Collections.singletonList(Permission.VIEW_CHANNEL), Collections.emptyList())
                                                    .addMemberPermissionOverride(guild.getSelfMember().getIdLong(), Collections.singletonList(Permission.VIEW_CHANNEL), Collections.emptyList())
                                                    .addRolePermissionOverride(guild.getIdLong(), Collections.emptyList(), Collections.singletonList(Permission.VIEW_CHANNEL)).setTopic("Run !help to show which commands you can use").queue(channel -> {
                                                        channel.sendFile(ImageHandler.getCardsImage(hand.getCards()), "hand.png").setEmbeds(unoGame.createEmbed(hand.getPlayerId()).setColor(guild.getSelfMember().getColor()).build()).queue(mes -> {
                                                            hand.setChannelId(channel.getIdLong());
                                                            hand.setMessageId(mes.getIdLong());
                                                        });
                                                    });
                                        }
                                    });
                        } else {
                            message.removeReaction(emoji.getEmote(), member.getUser()).queue();
                        }
                    }
                    break;
                case "???":
                    if (unoGame.getStarter() == member.getIdLong()) {
                        for (long channelId : unoGame.getHands().stream().map(UnoHand::getChannelId).collect(Collectors.toList())) {
                            if (channelId != -1) guild.getTextChannelById(channelId).delete().queue();
                        }
                        if (unoGame.getTurn() != -1) {
                            guild.getCategoryById(unoGame.getCategory()).delete().queue();
                        }
                        MessageEmbed me = message.getEmbeds().get(0);
                        EmbedBuilder eb = new EmbedBuilder(me);
                        eb.setTitle("The game of uno has been canceled");
                        message.editMessageEmbeds(eb.build()).queue();
                        GameHandler.removeUnoGame(guild.getIdLong());
                        message.delete().queueAfter(20, TimeUnit.SECONDS);
                    }
                    break;
                case "\uD83D\uDD90???":
                    if (unoGame.getTurn() == -1 && !hands.stream().map(UnoHand::getPlayerId).collect(Collectors.toList()).contains(member.getIdLong())) {
                        unoGame.addPlayer(member.getIdLong(), member.getEffectiveName());
                        MessageEmbed me = message.getEmbeds().get(0);
                        EmbedBuilder eb = new EmbedBuilder(me);
                        eb.clearFields();
                        MessageEmbed.Field f = me.getFields().get(0);
                        StringBuilder sb = new StringBuilder();
                        for (String name : hands.stream().map(UnoHand::getPlayerName).collect(Collectors.toList())) {
                            sb.append(name);
                            sb.append("\n");
                        }
                        eb.addField(f.getName(), sb.toString().trim(), false);
                        message.editMessageEmbeds(eb.build()).queue();
                    }
                    break;
            }
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onReconnected(@NotNull ReconnectedEvent event) {
        LOGGER.info("{} is reconnected!! Response number {}", event.getJDA().getSelfUser().getAsTag(), event.getResponseNumber());
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        final String useGuildSpecificSettingsInstead = String.format("Thank you for adding %s to %s!!!\n" +
                        "\nTo learn more about this bot feel free to type **ignt about**\n" +
                        "You can change the prefix by typing **ignt setprefix [prefix]**\n" +
                        "To learn more about a command **ignt help [command name]**",
                event.getJDA().getSelfUser().getAsMention(), event.getGuild().getName());

        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Hello!").setDescription(useGuildSpecificSettingsInstead);
        event.getJDA().getUserById(Config.get("owner_id")).openPrivateChannel().complete().sendMessage("Someone added me to " + event.getGuild().getName()).queue();
        event.getJDA().getUserById(Config.get("owner_id")).openPrivateChannel().complete().sendMessage("Invite link is " + event.getGuild().retrieveInvites().complete().get(0).getUrl()).queue();
        embedBuilder.setColor(InfoUserCommand.randomColor());
        event.getGuild().getOwner().getUser().openPrivateChannel().complete().sendMessageEmbeds(embedBuilder.build()).queue();
        try {
            event.getGuild().getTextChannels().get(0).sendMessageEmbeds(embedBuilder.build()).queue();
        } catch (Exception e) {
            try {
                event.getGuild().getTextChannels().get(1).sendMessageEmbeds(embedBuilder.build()).queue();
            } catch (Exception ignored) {}
        }
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        String name = event.getMember().getEffectiveName().replace(" ", "%20");
        String guildName = event.getGuild().getName().replace(" ", "+");
        String pngUrl = "https://api.popcat.xyz/welcomecard?background=https://media.discordapp.net/attachments/862860280963137576/894423966968598558/images_78.png&text1=" + name + "&text2=Welcome+To+" + guildName + "&text3=" + event.getGuild().getMembers().size() + "th+Member&avatar=" + event.getUser().getEffectiveAvatarUrl();
        event.getGuild().getDefaultChannel().sendMessage(pngUrl).queue();
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        String name = event.getUser().getName().replace(" ", "%20");
        String guildName = event.getGuild().getName().replace(" ", "+");
        String pngUrl = "https://api.popcat.xyz/welcomecard?background=https://media.discordapp.net/attachments/862860280963137576/894423966968598558/images_78.png&text1=" + name + "&text2=Left+" + guildName + "&text3=" + event.getGuild().getMembers().size() + "+Members+Left&avatar=" + event.getUser().getEffectiveAvatarUrl();
        event.getGuild().getDefaultChannel().sendMessage(pngUrl).queue();
    }
}
