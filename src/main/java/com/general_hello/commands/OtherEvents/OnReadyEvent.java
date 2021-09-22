package com.general_hello.commands.OtherEvents;

import com.general_hello.commands.Bot;
import com.general_hello.commands.SlashCommands.SlashCommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class OnReadyEvent extends ListenerAdapter {
    public static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        Bot.jda = event.getJDA();
        new SlashCommandHandler();
        SlashCommandHandler.initialize();
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Status").setColor(Color.GREEN).setTimestamp(OffsetDateTime.now()).setDescription(event.getJDA().getSelfUser().getAsMention() + " is now online! The problem has been resolved and the maintenance is complete!");
        event.getJDA().getTextChannelById(876363940144242699L).sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
