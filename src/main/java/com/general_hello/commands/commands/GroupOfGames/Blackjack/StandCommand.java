package com.general_hello.commands.commands.GroupOfGames.Blackjack;


import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.PrefixStoring;
import com.general_hello.commands.commands.RankingSystem.LevelPointManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.concurrent.TimeUnit;

public class StandCommand implements ICommand {
    @Override
    public void handle(CommandContext e) throws InterruptedException {
        final long guildID = e.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, DatabaseManager.INSTANCE::getPrefix);

        if (e.getArgs().isEmpty()) {
            BlackjackGame bjg = GameHandler.getBlackJackGame(e.getAuthor().getIdLong());
            if (bjg != null) {
                try {
                    e.getMessage().delete().queueAfter(4, TimeUnit.SECONDS);
                } catch (Exception ea) {
                }
                bjg.stand();
                e.getChannel().retrieveMessageById(bjg.getMessageId()).queue(m -> {
                    EmbedBuilder eb = bjg.buildEmbed(e.getAuthor().getName(), e.getGuild());
                    if (bjg.hasEnded()) {
                        int d = bjg.getWonCreds();
                        GameHandler.removeBlackJackGame(e.getAuthor().getIdLong());
                        LevelPointManager.feed(e.getAuthor(), 20);
                        DatabaseManager.INSTANCE.setCredits(e.getAuthor().getIdLong(), d);
                        eb.addField("Credits", "You now have " + d + " more credits", false);
                        GameHandler.removeBlackJackGame(e.getAuthor().getIdLong());
                    }
                    m.editMessageEmbeds(eb.build()).queue();
                });
            } else e.getChannel().sendMessage("No game has been started! Type `" +prefix + "bj` to start one!").queue();
        }
    }

    @Override
    public String getName() {
        return "stand";
    }

    @Override
    public String getHelp(String prefix) {
        return "Shows if you win or lose and not to get a card anymore.\n" +
                "Usage: `" + prefix + getName() + "`";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.GAMES;
    }
}
