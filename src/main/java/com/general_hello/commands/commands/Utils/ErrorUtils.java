package com.general_hello.commands.commands.Utils;

import com.general_hello.commands.Config;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.Emoji.Emojis;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class ErrorUtils {
    public static void error(CommandContext event, Exception e) {
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle(Emojis.ERROR + " An error occurred while executing a command!")
                .addField("Guild", (event.getGuild() == null ? "None (Direct message)" : event.getGuild().getIdLong()+" ("+event.getGuild().getName()+")"),true)
                .addField("User", event.getAuthor().getAsMention()+" ("+event.getAuthor().getAsTag()+")", true)
                .addField("Command", event.getMessage().getContentRaw(), false)
                .setDescription("```\n"+ e.getLocalizedMessage() +"\n```")
                .setColor(Color.RED);
        event.getJDA().openPrivateChannelById(Config.get("owner_id"))
                .flatMap(c -> c.sendMessageEmbeds(builder.build()))
                .queue();

        event.getJDA().openPrivateChannelById(Config.get("owner_id_partner"))
                .flatMap(c -> c.sendMessageEmbeds(builder.build()))
                .queue();

        event.getMessage().reply(Emojis.ERROR + " An unknown error occurred! The owner of the bot has been notified of this!").queue(s -> {}, ex -> {});
        e.printStackTrace();
    }

    public static void error(GuildMessageReceivedEvent event, Exception e) {
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle(Emojis.ERROR + " An error occurred while executing a command!")
                .addField("Guild", event.getGuild().getIdLong()+" ("+event.getGuild().getName()+")",true)
                .addField("User", event.getAuthor().getAsMention()+" ("+event.getAuthor().getAsTag()+")", true)
                .addField("Command", event.getMessage().getContentRaw(), false)
                .setDescription("```\n"+ e.getMessage() +"\n```")
                .setColor(Color.RED);
        event.getJDA().openPrivateChannelById(Config.get("owner_id"))
                .flatMap(c -> c.sendMessageEmbeds(builder.build()))
                .queue();

        event.getJDA().openPrivateChannelById(Config.get("owner_id_partner"))
                .flatMap(c -> c.sendMessageEmbeds(builder.build()))
                .queue();

        e.printStackTrace();
        event.getMessage().reply(Emojis.ERROR + " An unknown error occurred! The owner of the bot has been notified of this!").queue(s -> {}, ex -> {});
    }
}
