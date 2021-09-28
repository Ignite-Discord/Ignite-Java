package com.general_hello.commands.OtherEvents;

import com.general_hello.commands.Bot;
import com.general_hello.commands.commands.Emoji.Emoji;
import com.general_hello.commands.commands.Others.UpdateIgniteCoinsCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class OnReadyEvent extends ListenerAdapter {
    public static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        Bot.jda = event.getJDA();
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Status").setColor(Color.GREEN).setTimestamp(OffsetDateTime.now()).setDescription(event.getJDA().getSelfUser().getAsMention() + " is now online! The problem has been resolved and the maintenance/update is complete!" + Emoji.USER);
        event.getJDA().getTextChannelById(891816519498096650L).sendMessageEmbeds(embedBuilder.build()).queue();

        try {
            UpdateIgniteCoinsCommand.loadData(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
