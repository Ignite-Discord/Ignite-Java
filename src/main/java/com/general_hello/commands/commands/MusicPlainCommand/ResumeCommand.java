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
import java.util.ArrayList;
import java.util.List;

public class ResumeCommand implements ICommand
{

    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        GuildAudioPlayer guildAudioPlayer = AudioManager.getAudioPlayer(ctx.getGuild().getIdLong());
        AudioPlayer player = guildAudioPlayer.getPlayer();
        if (player.getPlayingTrack() == null)
        {
            ctx.getMessage().reply(Emoji.ERROR + " I'm currently not playing any music!").queue();
        }
        if (!player.isPaused())
        {
            ctx.getMessage().reply("The player is not paused!").queue();
            return;
        }
        player.setPaused(false);
        ctx.getChannel().sendMessageEmbeds(EmbedUtil.successEmbed("Resumed! " + Emoji.USER)).queue();
    }

    @Override
    public String getName() {
        return "resume";
    }

    @Override
    public String getHelp(String prefix) {
        return "Resumes the music/player";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("resumeplaying");
        arrayList.add("continue");
        arrayList.add("resume");
        return arrayList;
    }

    @Override
    public CommandType getCategory() {
        return CommandType.MUSIC;
    }
}