package com.general_hello.commands.commands.Info;


import com.general_hello.commands.Config;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.PrefixStoring;
import com.jagrosh.jdautilities.commons.JDAUtilitiesInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;

import java.awt.*;

public class AboutCommand implements ICommand {
    String[] featureses = new String[2];
    public boolean IS_AUTHOR = true;
    public String REPLACEMENT_ICON = "+";
    public Color color;
    public String description;
    public String oauthLink;

    public AboutCommand(Color color, String description) {
        this.color = color;
        this.description = description;
    }

    @Override
    public void handle(CommandContext event) throws InterruptedException {

        final long guildID = event.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        if (oauthLink == null) {
               oauthLink = "https://discord.com/api/oauth2/authorize?client_id=861133487948300308&permissions=1626729696&scope=bot%20applications.commands";
        }
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.cyan);
        builder.setAuthor("All about " + event.getSelfUser().getName() + "!", null, event.getSelfUser().getAvatarUrl());
        event.getChannel().createInvite().complete().getUrl();
        boolean join = !event.getChannel().retrieveInvites().complete().isEmpty();
        boolean inv = !oauthLink.isEmpty();
        String invline = "\n" + (join ? "Join my server [here](https://discord.gg/UeQ7NJcpNd)" : (inv ? "Please " : ""))
                + (inv ? (join ? ", or " : "") + "[invite](" + oauthLink + ") me to your server" : "") + "!";
        String author = event.getJDA().getUserById(Config.get("owner_id"))==null ? "<@" + event.getJDA().getUserById(Config.get("owner_id"))+">"
                : event.getJDA().getUserById(Config.get("owner_id")).getName();
        StringBuilder descr = new StringBuilder().append("Hello! I am **").append(event.getSelfUser().getName()).append("**, ")
                .append(description).append("\nI ").append(IS_AUTHOR ? "was written in Java" : "am owned").append(" by **")
                .append(author).append("** using " + JDAUtilitiesInfo.AUTHOR + "'s [Commands Extension](" + JDAUtilitiesInfo.GITHUB + ") (")
                .append(JDAUtilitiesInfo.VERSION).append(") and the [JDA library](https://github.com/DV8FromTheWorld/JDA) (")
                .append(JDAInfo.VERSION).append(")\nType `").append(prefix).append("help")
                .append("` to see my commands!").append(join || inv ? invline : "").append("\n\nSome of my features include: ```html\n");
       descr.append("IGNITE COINS WALLET: Wondering how much Ignite Coins you have? Wonder no more cause IgntBot will now serve as your digital wallet! Simply register your account to know your balance!\n" +
               "\n" +
               "IGNITE FAQs: New to the Ignite Community? Worry not, cause IgntBot got you covered! Ask anything Ignite related and our bot will answer your questions for you! In case the bot doesn't know what to say... just ask us!\n" +
               "\n" +
               "MUSIC: Never miss a beat with IgntBot! Simply hop in to a voice channel and enjoy music on demand!\n" +
               "\n" +
               "GAMES: IgntBot also contains games that Igniters can play such as UNO, Hangman, Guess the Number and so much more!\n" +
               "\n" +
               "MEME GENERATOR: Are you bored and would like to have a good laugh? IgntBot got you covered! Ask for a meme and it'll give you a good one!\n" +
               "\n" +
               "BAD WORD BLOCKER: To make COIL a safe space for everybody, IgntBot also blocks inappropriate words in the text channels!");
        descr.append(" ```");

        descr.append("\n");
        descr.append("Total memory: ").append((int) (Runtime.getRuntime().totalMemory() * 0.000001)).append(" mb\n");
        descr.append("Free memory: ").append((int) (Runtime.getRuntime().freeMemory() * 0.000001)).append(" mb\n");
        descr.append("Max memory: ").append((int) (Runtime.getRuntime().maxMemory() * 0.000001)).append(" mb\n");
        descr.append("Memory Usage: ").append((int) ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) * 0.00000001)).append(" mb");

        //Here
        System.out.println("Total Memory: " + Runtime.getRuntime().totalMemory());
        System.out.println("Free Memory: " + Runtime.getRuntime().freeMemory());
        System.out.println("Max Memory: " + Runtime.getRuntime().maxMemory());

        builder.setDescription(descr);
        event.getJDA().getShardInfo();
        builder.addField("Stats", (event.getJDA().getGuilds().size() + " Servers\nShard " + (event.getJDA().getShardInfo().getShardId() + 1)
                + "/" + event.getJDA().getShardInfo().getShardTotal()), true);
        builder.addField("This shard", event.getJDA().getUsers().size() + " Users\n" + event.getJDA().getGuilds().size() + " Servers", true);
        builder.addField("", event.getJDA().getTextChannels().size() + " Text Channels\n" + event.getJDA().getVoiceChannels().size() + " Voice Channels", true);
        builder.setFooter("Among sus", null);
        event.getMessage().replyEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "about";
    }

    @Override
    public String getHelp(String prefix) {
        return "Shows information about the bot!\n" +
                "Usage: `" + prefix + "about`";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.SPECIAL;
    }
}
