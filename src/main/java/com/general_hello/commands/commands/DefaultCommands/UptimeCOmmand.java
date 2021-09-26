package com.general_hello.commands.commands.DefaultCommands;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Info.InfoUserCommand;
import net.dv8tion.jda.api.EmbedBuilder;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.OffsetDateTime;

public class UptimeCOmmand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long duration = runtimeMXBean.getUptime();

        final long years = duration / 31104000000L;
        final long months = duration / 2592000000L % 12;
        final long days = duration / 86400000L % 30;
        final long hours = duration / 3600000L % 24;
        final long minutes = duration / 60000L % 60;
        final long seconds = duration / 1000L % 60;
        final long milliseconds = duration % 1000;

        String uptime = (years == 0 ? "" : "**" + years + "** Years, ") + (months == 0 ? "" : "**" + months + "** Months, ") + (days == 0 ? "" : "**" + days + "** Days, ") + (hours == 0 ? "" : "**" + hours + "** Hours, ")
                + (minutes == 0 ? "" : "**" + minutes + "** Minutes, ") + (seconds == 0 ? "" : "**" + seconds + "** Seconds, **") + (milliseconds == 0 ? "" : milliseconds + "** Milliseconds, ");

        uptime = replaceLast(uptime, ", ", "");
        uptime = replaceLast(uptime, ",", " and");

        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Uptime").setTimestamp(OffsetDateTime.now()).setColor(InfoUserCommand.randomColor());

        embedBuilder.setDescription("My uptime is " + uptime + "");
        ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }

    private String replaceLast(final String text, final String regex, final String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    @Override
    public String getName() {
        return "uptime";
    }

    @Override
    public String getHelp(String prefix) {
        return "Shows the current uptime of the bot\n" +
                "Usage: `" + prefix + getName() + "`";
    }


    @Override
    public CommandType getCategory() {
        return CommandType.SPECIAL;
    }
}
