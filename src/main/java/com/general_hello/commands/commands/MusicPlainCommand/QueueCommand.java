package com.general_hello.commands.commands.MusicPlainCommand;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.Emoji.Emoji;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Music.AudioManager;
import com.general_hello.commands.commands.Music.ButtonPaginator;
import com.general_hello.commands.commands.Music.FormatUtil;
import com.general_hello.commands.commands.Music.GuildAudioPlayer;
import com.general_hello.commands.commands.Utils.EmbedUtil;
import com.general_hello.commands.commands.Utils.Util;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class QueueCommand implements ICommand
{


    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        ButtonPaginator.Builder builder = new ButtonPaginator.Builder(ctx.getJDA())
                .setEventWaiter(new EventWaiter())
                .setItemsPerPage(10)
                .setTimeout(1, TimeUnit.MINUTES);
        int page = 1;
        if (!ctx.getArgs().isEmpty()) page = Integer.parseInt(ctx.getArgs().get(0));
        GuildAudioPlayer guildAudioPlayer = AudioManager.getAudioPlayer(ctx.getGuild().getIdLong());
        Queue<AudioTrack> queue = guildAudioPlayer.getScheduler().getQueue();
        if (queue.isEmpty())
        {
            if (guildAudioPlayer.getPlayer().getPlayingTrack() != null)
            {
                if (guildAudioPlayer.getScheduler().isRepeat())
                    ctx.getChannel().sendMessageEmbeds(EmbedUtil.successEmbed("\uD83D\uDD01 **Currently playing**: " + Util.titleMarkdown(guildAudioPlayer.getPlayer().getPlayingTrack()))).queue();
                else
                    ctx.getChannel().sendMessageEmbeds(EmbedUtil.defaultEmbed("**Currently playing**: " + Util.titleMarkdown(guildAudioPlayer.getPlayer().getPlayingTrack()))).queue();
            } else
                ctx.getChannel().sendMessageEmbeds(EmbedUtil.errorEmbed("There is no music playing!")).queue();
            return;
        }
        String[] tracks = queue.stream().map(x -> "`[" + FormatUtil.formatTime(x.getDuration()) + "]` " + Util.titleMarkdown(x) + " (<@" + x.getUserData(Long.class) + ">)").toArray(String[]::new);
        builder.setTitle(getQueueTitle(guildAudioPlayer))
                .setItems(tracks)
                .addAllowedUsers(ctx.getAuthor().getIdLong())
                .setColor(Color.decode("#452350"));

        int finalPage = page;
        ctx.getMessage().replyEmbeds(new EmbedBuilder()
                .setColor(0x452350)
                .setDescription(Emoji.LOADING + " Loading...")
                .build())
                .queue(hook -> hook.editMessageEmbeds().queue(message -> builder.build().paginate(message, finalPage)));
    }

    private String getQueueTitle(GuildAudioPlayer player)
    {
        final StringBuilder sb = new StringBuilder();
        if (player.getPlayer().getPlayingTrack() != null)
        {
            sb.append(player.getPlayer().isPaused() ? "\u23f8" : "\u25b6").append(player.getScheduler().isRepeat() ? "\uD83D\uDD01" : "").append(" ").append(Util.titleMarkdown(player.getPlayer().getPlayingTrack())).append("\n");
        }
        int entries = player.getScheduler().getQueue().size();
        long length = 0;
        for (AudioTrack track : player.getScheduler().getQueue())
        {
            length += track.getDuration();
        }
        return FormatUtil.filter(sb.append(entries).append(entries == 1 ? " entry | `" : " entries | `").append(FormatUtil.formatTime(length)).append("`").toString());
    }

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getHelp(String prefix) {
        return "Shows the current queue\n" +
                "Optional parameter: Page number";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> q = new ArrayList<>();
        q.add("q");
        return q;
    }

    @Override
    public CommandType getCategory() {
        return CommandType.MUSIC;
    }
}