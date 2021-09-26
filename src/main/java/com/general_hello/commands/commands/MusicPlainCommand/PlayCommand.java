package com.general_hello.commands.commands.MusicPlainCommand;

import com.general_hello.commands.Config;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Music.FormatUtil;
import com.general_hello.commands.commands.Music.GuildAudioPlayer;
import com.general_hello.commands.commands.Music.MusicUtil;
import com.general_hello.commands.commands.Utils.EmbedUtil;
import com.general_hello.commands.commands.Utils.Util;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.StageChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayCommand implements ICommand
{
    @Override
    public void handle(CommandContext event) throws InterruptedException, IOException, SQLException {
        try {
            Member member = event.getMember();
            GuildVoiceState voiceState = member.getVoiceState();
            AudioManager manager = event.getGuild().getAudioManager();
            if (manager.getConnectedChannel() == null) {
                manager.openAudioConnection(voiceState.getChannel());
                if (voiceState.getChannel() instanceof StageChannel) {
                    event.getGuild().requestToSpeak();
                }
            }
            GuildAudioPlayer guildAudioPlayer = com.general_hello.commands.commands.Music.AudioManager.getAudioPlayer(event.getGuild().getIdLong());
            if (manager.getSendingHandler() == null)
                manager.setSendingHandler(guildAudioPlayer.getSendHandler());
            String query = event.getArgs().toString().replace("[", "").replace("]", "").replace(",", " ");
            query = query.startsWith("http://") || query.startsWith("https://") ? query : "ytsearch:" + query;
            com.general_hello.commands.commands.Music.AudioManager.getPlayerManager().loadItemOrdered(guildAudioPlayer, query, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    track.setUserData(event.getAuthor().getIdLong());
                    event.getMessage().replyEmbeds(MusicUtil.getAddedToQueueMessage(guildAudioPlayer, track)).queue();
                    guildAudioPlayer.getScheduler().queue(track);
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    if (playlist.getTracks().size() == 1 || playlist.isSearchResult()) {
                        AudioTrack single = (playlist.getSelectedTrack() == null) ? playlist.getTracks().get(0) : playlist.getSelectedTrack();
                        single.setUserData(event.getAuthor().getIdLong());
                        event.getMessage().replyEmbeds(MusicUtil.getAddedToQueueMessage(guildAudioPlayer, single)).queue();
                        guildAudioPlayer.getScheduler().queue(single);
                        return;
                    }
                    String amount = "Added `" + playlist.getTracks().size() + "` tracks! (`" + FormatUtil.formatTime(playlist.getTracks().stream().map(AudioTrack::getDuration).reduce(0L, Long::sum)) + "`)";
                    if (guildAudioPlayer.getPlayer().getPlayingTrack() == null) {
                        amount += "\n**Now playing**: " + Util.titleMarkdown(playlist.getTracks().get(0));
                    }
                    event.getMessage().replyEmbeds(EmbedUtil.defaultEmbed(amount)).queue();
                    playlist.getTracks().forEach(track ->
                    {
                        track.setUserData(event.getAuthor().getIdLong());
                        guildAudioPlayer.getScheduler().queue(track);
                    });
                }

                @Override
                public void noMatches() {
                    event.getMessage().replyEmbeds(EmbedUtil.errorEmbed("Sorry, i couldn't find anything!")).queue();
                }

                @Override
                public void loadFailed(FriendlyException exception) {
                    event.getMessage().replyEmbeds(EmbedUtil.errorEmbed("An error occurred while loading a track!\n`" + exception.getMessage() + "`")).queue();
                }
            });
        } catch (Exception e) {
            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle("An error occurred while executing Play command!")
                    .addField("Guild", (event.getGuild() == null ? "None (Direct message)" : event.getGuild().getIdLong()+" ("+event.getGuild().getName()+")"),true)
                    .addField("User", event.getAuthor().getAsMention()+" ("+event.getAuthor().getAsTag()+")", true)
                    .addField("Command", "u?play", false)
                    .setDescription("```\n"+ e.getLocalizedMessage() +"\n```")
                    .setColor(Color.RED);
            event.getJDA().openPrivateChannelById(Config.get("owner_id"))
                    .flatMap(c -> c.sendMessageEmbeds(builder.build()))
                    .queue();

            event.getJDA().openPrivateChannelById(Config.get("owner_id_partner"))
                    .flatMap(c -> c.sendMessageEmbeds(builder.build()))
                    .queue();

            event.getMessage().reply("An unknown error occurred! `" + e.getLocalizedMessage() + "` The owner of the bot has been notified of this!").queue(s -> {}, ex -> {});
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp(String prefix) {
        return "Plays a song";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("playsong");
        arrayList.add("listensong");
        return arrayList;
    }
    @Override
    public CommandType getCategory() {
        return CommandType.MUSIC;
    }
}