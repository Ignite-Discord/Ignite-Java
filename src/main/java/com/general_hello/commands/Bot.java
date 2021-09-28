package com.general_hello.commands;

import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.OtherEvents.*;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.time.OffsetDateTime;
import java.util.Scanner;

public class Bot {
    public static JDA jda;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";

    private Bot() throws LoginException {
        DatabaseManager.INSTANCE.getPrefix(-1);
        WebUtils.setUserAgent("IgnBot");
        EmbedUtils.setEmbedBuilder(
                () -> new EmbedBuilder()
                        .setColor(Color.cyan)
                        .setFooter("ignt help")
        );

        EventWaiter waiter = new EventWaiter();

        jda = JDABuilder.createDefault(Config.get("token"),
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.DIRECT_MESSAGE_TYPING,
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.GUILD_EMOJIS,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.GUILD_INVITES
        )
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(new Listener(waiter), waiter)
                .addEventListeners(new OnButtonClick())
                .addEventListeners(new OnPrivateMessage())
                .addEventListeners(new OtherEvents())
                .addEventListeners(new OnSelectionMenu())
                .addEventListeners(new OnButtonChessClick())
                .addEventListeners(new OnReadyEvent())
                .setActivity(Activity.watching("ignt help"))
                .setStatus(OnlineStatus.ONLINE)
                .setChunkingFilter(ChunkingFilter.ALL) // enable member chunking for all guilds
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableCache(CacheFlag.ACTIVITY)
                .enableCache(CacheFlag.ONLINE_STATUS)
                .build();
    }

    public static void main(String[] args) throws LoginException {
        commandPrompt();
    }

    public static void commandPrompt() throws LoginException {
        boolean question = false;
        boolean question1 = false;
        boolean channel = false;
        TextChannel textChannel = null;

        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_RED + "Program Loaded!\n" +
                "Welcome to Ignite Bot Command Line (UBCL) ! Have a great day!" + ANSI_RESET);
        while (true) {
            String s = scanner.nextLine();
            JDA jda = Listener.jda;

            if (s.equalsIgnoreCase("msgshutdown")) {
                EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Status").setColor(Color.RED).setFooter("This maintenance is for all Plenary bots").setDescription(jda.getSelfUser().getAsMention() + " is currently offline due to some maintenance!");
                jda.getTextChannelById(852342009288851516L).sendMessageEmbeds(embedBuilder.build()).queue();
                System.out.println("Successfully sent the message!");
            }

            if (s.equalsIgnoreCase("msgstart")) {
                EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Status").setColor(Color.GREEN).setFooter("This status is for all Plenary bots").setDescription(jda.getSelfUser().getAsMention() + " is now online! The problem has been resolved and the maintenance is complete!");
                jda.getTextChannelById(852342009288851516L).sendMessageEmbeds(embedBuilder.build()).queue();
                System.out.println("Successfully sent the message!");
            }

            if (s.equalsIgnoreCase("help")) {
                System.out.println(ANSI_BLUE + "Option 1: startbot = To start the bot\n" +
                        "Option 2: botinfo = To get info of the bot\n" +
                        "Option 3: sendmsg = To send a message with the bot\n" +
                        "Option 4: disconnect channel = To disconnect the channel of the server!\n" +
                        "Option 5: msgshutdown = To send the shutdown reason in #shutdown!\n" +
                        "Option 6: msgstart = To send the start up message in #startups!\n" +
                        "Option 7: startactivity = To start the activity changing crap!\n" +
                        "Option 8: stop = To stop running the program!" + ANSI_RESET);
            }

            if (s.equalsIgnoreCase("disconnect channel")) {
                question1 = false;
                System.out.println("Successfully disconnected!");
            }

            if (question1) {
                System.out.println("The message you want to send is " + s);
                System.out.println("Sending the message now.....");
                try {
                    textChannel.sendMessage(s).queue();
                    System.out.println("Message successfully sent!");
                } catch (Exception ignored) {
                    System.out.println("Message failed to send!");

                }
            }

            if (s.equalsIgnoreCase("jda")) {
                System.out.println(jda);
            }

            if (s.equalsIgnoreCase("startactivity")) {
                while (true) {
                    System.out.println("Yeet");
                    jda.getPresence().setActivity(Activity.watching("Made by General Hello#0101"));

                    try {
                        Thread.sleep(10000);
                    } catch (Exception ignored) {}

                    jda.getPresence().setActivity(Activity.watching("Happy Moon festival!"));

                    try {
                        Thread.sleep(10000);
                    } catch (Exception ignored) {}

                    jda.getPresence().setActivity(Activity.watching("ignt help"));

                    try {
                        Thread.sleep(10000);
                    } catch (Exception ignored) {}

                    jda.getPresence().setActivity(Activity.watching("Made by HELLO66#0066"));

                    try {
                        Thread.sleep(10000);
                    } catch (Exception ignored) {}

                    jda.getPresence().setActivity(Activity.watching("Made by unjown#4644"));

                    try {
                        Thread.sleep(10000);
                    } catch (Exception ignored) {}

                    jda.getPresence().setActivity(Activity.watching("Design made by SkyacinthClues#0822"));

                    try {
                        Thread.sleep(10000);
                    } catch (Exception ignored) {}

                    jda.getPresence().setActivity(Activity.watching("Deuteronomy 23 1. No one who has been emasculated by crushing or cutting may enter the assembly of the LORD."));

                    try {
                        Thread.sleep(10000);
                    } catch (Exception ignored) {}
                }
            }

            if (question) {
                try {
                    System.out.println(jda.getTextChannelById(s).getName() + " is the channel that the message will be sent in!");
                } catch (Exception ignored) {
                }
                System.out.println("What will the message be?");
                question1 = true;
                question = false;
                textChannel = jda.getTextChannelById(s);
            }

            if (s.equalsIgnoreCase("startbot")) {
                System.out.println("Starting the bot up!");
                new Bot();
            }
        

            if (s.equalsIgnoreCase("botinfo")) {
                String asTag = jda.getSelfUser().getAsTag();
                String avatarUrl = jda.getSelfUser().getAvatarUrl();
                OffsetDateTime timeCreated = jda.getSelfUser().getTimeCreated();
                String id = jda.getSelfUser().getId();
                System.out.println("Tag of the bot: " + asTag);
                System.out.println("Avatar url: " + avatarUrl);
                System.out.println("Time created: " + timeCreated);
                System.out.println("Id: " + id);
                System.out.println("Shard info: " + jda.getShardInfo().getShardString());
            }

            if (s.equalsIgnoreCase("sendmsg")) {
                System.out.println("What is the channel id that you wish to send the message in?");
                question = true;
            }

            if (s.equalsIgnoreCase("stop")) {
                System.out.println("Thank you for using UBCL have a great day!");
                jda.shutdown();
                break;
            }
        }
    }
}
