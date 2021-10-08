package com.general_hello.commands.commands.MusicPlainCommand;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Music.AudioScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;

public class LeaveCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            final AudioManager audioManager = ctx.getGuild().getAudioManager();
            final Member member = ctx.getMember();
            final GuildVoiceState memberVoiceState = member.getVoiceState();
            final VoiceChannel memberChannel = memberVoiceState.getChannel();
            audioManager.openAudioConnection(memberChannel);
            audioManager.setSelfDeafened(true);
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
        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        audioManager.closeAudioConnection();

        EmbedBuilder em = new EmbedBuilder().setTitle("Music").setColor(Color.RED);
        em.setDescription("I have left the voice channel");
        channel.sendMessageEmbeds(em.build()).queue();
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getHelp(String prefix) {
        return "The bot leaves the voice channel\n" +
                "Usage: `" + prefix + "leave`";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.MUSIC;
    }
}