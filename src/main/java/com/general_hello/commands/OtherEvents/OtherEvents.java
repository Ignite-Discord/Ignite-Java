package com.general_hello.commands.OtherEvents;

import com.general_hello.commands.Config;
import com.general_hello.commands.Listener;
import com.general_hello.commands.commands.Info.InfoUserCommand;
import com.general_hello.commands.commands.Logs.AutoMod;
import com.general_hello.commands.commands.Logs.BasicLogger;
import com.general_hello.commands.commands.Logs.MessageCache;
import com.general_hello.commands.commands.Logs.TextUploader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.*;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.MessageBulkDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateDiscriminatorEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class OtherEvents extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    public static int disconnectCount = 0;
    private static OffsetDateTime timeDisconnected = OffsetDateTime.now();
    public static BasicLogger logger = new BasicLogger();
    public static MessageCache messageCache = new MessageCache();
    public static AutoMod autoMod = new AutoMod();
    public static TextUploader uploader = new TextUploader("https://discord.com/api/webhooks/877034647400349696/ZG9eOzP6UN1k1_U5i6YkBEpPmcNliFE5gEdAE3aJBsqYSh85Q9OdpaD2HJjuh_h5pOXF");

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
    public void onGuildMessageUpdate(@NotNull GuildMessageUpdateEvent event) {
        Message m = (event).getMessage();

        if(!m.getAuthor().isBot()) // ignore bot edits
        {
            // Run automod on the message
            OtherEvents.autoMod.performAutomod(m);

            // Store and log the edit
            MessageCache.CachedMessage old = messageCache.putMessage(m);
            logger.logMessageEdit(m, old);
        }
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        // Log the join
        logger.logGuildJoin(event);
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

    @Override
    public void onGuildMessageDelete(@NotNull GuildMessageDeleteEvent event) {

        // Log the deletion
        MessageCache.CachedMessage old = messageCache.pullMessage(event.getGuild(), event.getMessageIdLong());
        logger.logMessageDelete(old);
    }

    @Override
    public void onMessageBulkDelete(@NotNull MessageBulkDeleteEvent event) {
        MessageBulkDeleteEvent gevent = event;

        // Get the messages we had cached

        List<MessageCache.CachedMessage> logged = gevent.getMessageIds().stream()
                .map(id -> messageCache.pullMessage(gevent.getGuild(), Long.parseLong(id)))
                .filter(m -> m != null)
                .collect(Collectors.toList());

        // Log the deletion
        logger.logMessageBulkDelete(logged, gevent.getMessageIds().size(), gevent.getChannel());
    }

    @Override
    public void onUserUpdateName(@NotNull UserUpdateNameEvent event) {
        // Log the name change
        logger.logNameChange(event);
        event.getUser().getMutualGuilds().stream().map(g -> g.getMember(event.getUser())).forEach(m -> OtherEvents.autoMod.dehoist(m));
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {

        // Log the member leaving
        logger.logGuildLeave(event);
    }

    @Override
    public void onUserUpdateDiscriminator(@NotNull UserUpdateDiscriminatorEvent event) {
        logger.logNameChange(event);

    }

    @Override
    public void onGuildMemberUpdateNickname(@NotNull GuildMemberUpdateNicknameEvent event) {
        OtherEvents.autoMod.dehoist(event.getMember());

    }

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {

        // Log the voice join
        if (!event.getMember().getUser().isBot()) // ignore bots
            logger.logVoiceJoin(event);
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {

        // Log the voice move
        if(!event.getMember().getUser().isBot()) // ignore bots
            logger.logVoiceMove(event);
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {

        // Log the voice leave
        if(!event.getMember().getUser().isBot()) // ignore bots
            logger.logVoiceLeave(event);
    }
}
