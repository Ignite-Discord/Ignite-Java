package com.general_hello.commands.commands.MusicPlainCommand;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Music.AudioManager;
import com.general_hello.commands.commands.Music.GuildAudioPlayer;
import com.general_hello.commands.commands.Utils.EmbedUtil;
import net.dv8tion.jda.api.entities.Member;

import java.io.IOException;
import java.sql.SQLException;

public class VolumeCommand implements ICommand
{
    @Override
    public void handle(CommandContext event) throws InterruptedException, IOException, SQLException {
        Member member = event.getMember();
        String option = event.getArgs().get(0);
        int volume = Math.max(1, Math.min(100, Integer.parseInt(option)));
        GuildAudioPlayer guildAudioPlayer = AudioManager.getAudioPlayer(event.getGuild().getIdLong());
        guildAudioPlayer.getPlayer().setVolume(volume);
        event.getMessage().replyEmbeds(EmbedUtil.successEmbed("The volume has been adjusted to `" + volume + "%`!")).queue();
    }

    @Override
    public String getName() {
        return "volume";
    }

    @Override
    public String getHelp(String prefix) {
        return "Change the volume of the bot\n" +
                "Usage: `" + prefix + getName() + " [volume 1-100]`";
    }


    @Override
    public CommandType getCategory() {
        return CommandType.MUSIC;
    }
}