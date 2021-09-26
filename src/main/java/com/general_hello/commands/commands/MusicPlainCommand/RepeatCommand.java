package com.general_hello.commands.commands.MusicPlainCommand;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Music.AudioManager;
import com.general_hello.commands.commands.Music.GuildAudioPlayer;
import com.general_hello.commands.commands.Utils.EmbedUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepeatCommand implements ICommand
{

    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        GuildAudioPlayer player = AudioManager.getAudioPlayer(ctx.getGuild().getIdLong());
        if (player.getPlayer().getPlayingTrack() == null)
        {
            ctx.getMessage().replyEmbeds(EmbedUtil.errorEmbed("I'm currently not playing any music!")).queue();
            return;
        }
        if (!player.getScheduler().isRepeat())
        {
            player.getScheduler().setRepeat(true);
            ctx.getChannel().sendMessageEmbeds(EmbedUtil.successEmbed("\uD83D\uDD01 Repeat mode turned **ON**\nUse this command again to turn it off.")).queue();
        } else
        {
            player.getScheduler().setRepeat(false);
            ctx.getChannel().sendMessageEmbeds(EmbedUtil.successEmbed("\uD83D\uDD01 Repeat mode turned **OFF**")).queue();
        }
    }

    @Override
    public String getName() {
        return "loop";
    }

    @Override
    public String getHelp(String prefix) {
        return "Loops the current queue/song\n" +
                "Usage: `" + prefix + getName() + "`";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();
        aliases.add("repeat");
        aliases.add("loops");
        return aliases;
    }

    @Override
    public CommandType getCategory() {
        return CommandType.MUSIC;
    }
}
