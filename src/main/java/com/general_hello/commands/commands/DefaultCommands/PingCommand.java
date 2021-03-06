package com.general_hello.commands.commands.DefaultCommands;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.Emoji.Emojis;
import com.general_hello.commands.commands.ICommand;
import net.dv8tion.jda.api.JDA;

public class PingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        jda.getRestPing().queue(
                (ping) -> ctx.getChannel()
                        .sendMessageFormat("Server Night:\n" +
                                "Ping: %sms " + Emojis.LOADING, ping).queue()
        );
    }

    @Override
    public String getHelp(String prefix) {
        return "Usage: `" + prefix + "ping`\n" +
                "Shows the current ping from the bot to the discord servers";
    }

    @Override
    public String getName() {
        return "ping";
    }


    @Override
    public CommandType getCategory() {
        return CommandType.SPECIAL;
    }
}
