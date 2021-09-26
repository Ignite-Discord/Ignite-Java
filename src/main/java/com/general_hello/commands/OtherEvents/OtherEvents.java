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
import net.dv8tion.jda.api.events.*;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
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
                case "▶️":
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
                case "❌":
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
                case "\uD83D\uDD90️":
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
                        "\nTo learn more about this bot feel free to type **ign about**\n" +
                        "You can change the prefix by typing **ign setprefix [prefix]**\n" +
                        "To learn more about a command **ign help [command name]**",
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
    public void onResumed(@NotNull ResumedEvent event)  {
        TextChannel guildChannelById = event.getJDA().getTextChannelById(852338750519640116L);
        EmbedBuilder em = new EmbedBuilder().setColor(Color.RED).setTitle("🔴 Disconnected");
        disconnectCount++;
        em.setDescription("The bot disconnected for " +
                (OffsetDateTime.now().getHour() - timeDisconnected.getHour())  + " hour(s) " +
                (OffsetDateTime.now().getMinute() - timeDisconnected.getMinute()) + " minute(s) " +
                (OffsetDateTime.now().getSecond() - timeDisconnected.getSecond()) + " second(s) and " +
                (timeDisconnected.getNano() /1000000) + " milliseconds due to connectivity issues!\n" +
                "Response number: " + event.getResponseNumber()).setTimestamp(OffsetDateTime.now()).setFooter("The bot disconnected " + disconnectCount + " times already since the last restart!");
        guildChannelById.sendMessageEmbeds(em.build()).queue();
        User owner_id = event.getJDA().getUserById(Config.get("owner_id"));
        owner_id.openPrivateChannel().complete().sendMessageEmbeds(em.build()).queue();
    }
}
