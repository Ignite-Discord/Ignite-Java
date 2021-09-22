package com.general_hello.commands.commands.DefaultCommands;

import com.general_hello.commands.SlashCommands.SlashCommand;
import com.general_hello.commands.SlashCommands.SlashCommandContext;
import com.general_hello.commands.commands.Emoji.Emoji;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;

public class PingSlashCommand extends SlashCommand {
    public PingSlashCommand()
    {
        setCommandData(new CommandData("ping", "Shows the bots"));
        setRunnableInDM(false);
    }

    @Override
    public void executeCommand(@NotNull SlashCommandEvent event, @Nullable Member sender, @NotNull SlashCommandContext ctx) throws SQLException {
        JDA jda = event.getJDA();

        jda.getRestPing().queue(
                (ping) -> event.getChannel()
                        .sendMessageFormat("Server Night:\n" +
                                "Ping: %sms" + Emoji.LOADING, ping).queue()
        );
    }
}
