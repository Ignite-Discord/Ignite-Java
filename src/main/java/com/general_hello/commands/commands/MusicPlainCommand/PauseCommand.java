package com.general_hello.commands.commands.MusicPlainCommand;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.Emoji.Emoji;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Music.AudioManager;
import com.general_hello.commands.commands.Music.GuildAudioPlayer;
import com.general_hello.commands.commands.Utils.EmbedUtil;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import java.io.IOException;
import java.sql.SQLException;

public class PauseCommand implements ICommand
{

    @Override
    public void handle(CommandContext event) throws InterruptedException, IOException, SQLException {
        GuildAudioPlayer guildAudioPlayer = AudioManager.getAudioPlayer(event.getGuild().getIdLong());
        AudioPlayer player = guildAudioPlayer.getPlayer();
        if (player.getPlayingTrack() == null)
        {
            event.getMessage().replyEmbeds(EmbedUtil.errorEmbed("I'm currently not playing any music!")).queue();
        }
        if (player.isPaused())
        {
            event.getMessage().replyEmbeds(EmbedUtil.errorEmbed("The player is already paused! Use `/resume`")).queue();
            return;
        }
        player.setPaused(true);
        event.getChannel().sendMessageEmbeds(EmbedUtil.successEmbed("The player is now paused! " + Emoji.USER)).queue();
    }

    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public String getHelp(String prefix) {
        return "Pause the currently playing song";
    }


    @Override
    public CommandType getCategory() {
        return CommandType.MUSIC;
    }
}