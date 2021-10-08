package com.general_hello.commands.commands.MusicPlainCommand;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Music.AudioScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;

public class StopCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        final Member self = ctx.getMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        final AudioManager audioManager = ctx.getGuild().getAudioManager();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("I need to be in a voice channel to play music").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You need to be in a voice channel for this command to work").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("You need to be in the same voice channel as me for this to work").queue();
            return;
        }

        AudioScheduler scheduler = com.general_hello.commands.commands.Music.AudioManager.getAudioPlayer(ctx.getGuild().getIdLong()).getScheduler();
        scheduler.getPlayer().stopTrack();
        scheduler.getQueue().clear();
        audioManager.closeAudioConnection();

        EmbedBuilder em = new EmbedBuilder().setTitle("Stopped").setColor(Color.RED);
        em.setDescription("The player has been stopped and the queue has been cleared");
        channel.sendMessageEmbeds(em.build()).queue();
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getHelp(String prefix) {
        return "Stops the current song and clears the queue\n" +
                "Usage: `" + prefix + "stop`";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.MUSIC;
    }
}