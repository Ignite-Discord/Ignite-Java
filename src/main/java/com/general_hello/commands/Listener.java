package com.general_hello.commands;

import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.commands.Emoji.Emoji;
import com.general_hello.commands.commands.GroupOfGames.Games.GuessNumber;
import com.general_hello.commands.commands.GroupOfGames.Games.GuessNumberCommand;
import com.general_hello.commands.commands.PrefixStoring;
import com.general_hello.commands.commands.RankingSystem.LevelPointManager;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Listener extends ListenerAdapter {
    public static CommandManager manager;
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    public static HashMap<String, Integer> count = new HashMap<>();
    public static JDA jda;

    public Listener(EventWaiter waiter) {
        manager = new CommandManager(waiter);
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        System.out.println(event.getAuthor().getName() + " sent " + event.getMessage().getContentRaw() + " in #" + event.getChannel().getName());
        EmbedBuilder em;

        if (event.getAuthor().isBot() || event.isWebhookMessage()) {
            return;
        }

        Message m = (event).getMessage();

        //add xp :D
        LevelPointManager.feed(event.getAuthor());

        final long guildID = event.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, DatabaseManager.INSTANCE::getPrefix);
        String raw = event.getMessage().getContentRaw();

        if (event.getMessage().getContentRaw().equals(prefix + " commands")) {
            if (event.getAuthor().getId().equals(Config.get("owner_id"))) {
                em = new EmbedBuilder().setTitle("Command Count details!!!!").setColor(Color.red).setFooter("Commands used until now ").setTimestamp(LocalDateTime.now());
                em.addField("Command made by ", event.getAuthor().getName(), false);
                em.addField("Date", LocalDateTime.now().getMonthValue() + "/" + LocalDateTime.now().getDayOfMonth() + "/" + LocalDateTime.now().getYear(), false);
                em.addField("Total number of Commands used in this session....", CommandManager.commandNames.size() + " commands", false);
                em.addField("List of Commands used in this session....", commandsCount(), false);
                event.getAuthor().openPrivateChannel().complete().sendMessageEmbeds(em.build()).queue();
            }
        }


        jda = event.getJDA();

        System.out.println(prefix);
        if (raw.equalsIgnoreCase(prefix + " shutdown") && event.getAuthor().getId().equals(Config.get("owner_id"))) {
            shutdown(event, true);
            return;
        } else if (raw.equalsIgnoreCase(prefix + " shutdown") && event.getAuthor().getId().equals(Config.get("owner_id_partner"))) {
            shutdown(event, false);
            return;
        }

        if (raw.toLowerCase().startsWith(prefix)) {
            try {
                manager.handle(event, prefix);
            } catch (InterruptedException | IOException | SQLException e) {
                e.printStackTrace();
            }
        } else {

            List<String> lol = new ArrayList<>();
            lol.add(event.getMessage().getContentRaw());

            GuessNumber gn = GuessNumberCommand.guessNumberHashMap.get(event.getAuthor());
            try {
                if (!gn.isEnded)
                    gn.sendInput(lol, event);
            } catch (NullPointerException en) {
                LOGGER.info(en + this.getClass().getName(), "Game haven't started.");
            } catch (NumberFormatException ignored) {
            }
        }
    }

    public static String commandsCount() {
        int x = 0;
        int size = CommandManager.commandNames.size();
        StringBuilder result = new StringBuilder();

        while (x < size) {
            String commandName = CommandManager.commandNames.get(x);
            result.append(x+1).append(".) ").append(commandName).append(" - ").append(count.get(commandName)).append("\n");
            x++;
        }

        return String.valueOf(result);
    }

    public static void shutdown(GuildMessageReceivedEvent event, boolean isOwner) {
        LOGGER.info("The bot " + event.getAuthor().getAsMention() + " is shutting down.\n" +
                "Thank you for using General_Hello's Code!!!");

        event.getChannel().sendMessage("Shutting down... " + Emoji.LOADING).queue();
        event.getChannel().sendMessage("Bot successfully shutdown! " + Emoji.USER).queue();
        EmbedBuilder em = new EmbedBuilder().setTitle("Shutdown details!").setColor(Color.red).setFooter("Shutdown on ").setTimestamp(LocalDateTime.now());
        em.addField("Shutdown made by ", event.getAuthor().getName(), false);
        em.addField("Date", LocalDateTime.now().getMonthValue() + "/" + LocalDateTime.now().getDayOfMonth() + "/" + LocalDateTime.now().getYear(), false);
        em.addField("Total number of Commands used during this session....", CommandManager.commandNames.size() + " commands", false);
        em.addField("List of Commands used during this session....", commandsCount(), false);
        event.getAuthor().openPrivateChannel().complete().sendMessageEmbeds(em.build()).queue();

        if (!isOwner) {
            User owner = event.getJDA().retrieveUserById(Config.get("owner_id")).complete();
            owner.openPrivateChannel().complete().sendMessageEmbeds(em.build()).queue();
        }


        event.getJDA().shutdown();
        BotCommons.shutdown(event.getJDA());
    }
}