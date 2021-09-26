package com.general_hello.commands.commands.GroupOfGames.Blackjack;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.RankingSystem.LevelPointManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.sql.SQLException;

public class BlackjackCommand implements ICommand {
    private GameHandler gameHandler;

    public BlackjackCommand(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }


    @Override
    public void handle(CommandContext e) throws InterruptedException, SQLException {
        User author = e.getAuthor();
        long playerId = author.getIdLong();

         BlackjackGame objg = GameHandler.getBlackJackGame(playerId);
                if (objg == null) {
                    BlackjackGame bjg = new BlackjackGame(50);
                    EmbedBuilder eb = bjg.buildEmbed(author.getName(), e.getGuild());
                    if (!bjg.hasEnded()) {
                        GameHandler.putBlackJackGame(playerId, bjg);
                    } else {
                        double d = bjg.getWonCreds();
                        LevelPointManager.feed(author, (long) d);
                        eb.addField("XP points", "You now have more XP points", false);
                    }
                    e.getChannel().sendMessageEmbeds(eb.build()).queue(m -> {
                        if (!bjg.hasEnded()) bjg.setMessageId(m.getIdLong());
                    });
                } else {
                    e.getChannel().sendMessage("You're already playing a game").queue();
                }
            }

    @Override
    public String getName() {
        return "bj";
    }

    @Override
    public String getHelp(String p) {
        return "Start a blackjack game!\n" +
                "Usage: `" + p + "bj [amount of money]`";
    }

    public static int getInt(String s) {
        if (s.matches("(?i)[0-9]*k?m?")) {
            s = s.replaceAll("m", "000000");
            s = s.replaceAll("k", "000");
        }
        int i;
        try {
            i = Integer.parseInt(s);
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }


    @Override
    public CommandType getCategory() {
        return CommandType.GAMES;
    }
}
