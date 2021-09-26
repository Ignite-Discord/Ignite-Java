package com.general_hello.commands.commands.MusicPlainCommand;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.Emoji.Emoji;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Music.AudioManager;
import com.general_hello.commands.commands.Music.GuildAudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.io.IOException;
import java.sql.SQLException;

public class SkipCommand implements ICommand
{

    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        GuildAudioPlayer guildAudioPlayer = AudioManager.getAudioPlayer(ctx.getGuild().getIdLong());
        if (guildAudioPlayer.getScheduler().isRepeat())
        {
            guildAudioPlayer.getScheduler().setRepeat(false);
        }
        if (guildAudioPlayer.getPlayer().getPlayingTrack() == null)
        {
            ctx.getMessage().reply(Emoji.ERROR + " There is no music to skip!").queue();
            return;
        }
        guildAudioPlayer.getScheduler().nextTrack();
        AudioTrack nextTrack = guildAudioPlayer.getPlayer().getPlayingTrack();
        if (nextTrack == null)
        {
            ctx.getMessage().reply("**Skipped!**").queue();
            return;
        }
        ctx.getMessage().reply("**Skipped!** Now playing: `" + nextTrack.getInfo().title + "`").queue();
    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getHelp(String prefix) {
        return "Skips the currently playing track";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.MUSIC;
    }
}
