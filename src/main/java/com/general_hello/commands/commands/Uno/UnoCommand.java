package com.general_hello.commands.commands.Uno;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.GroupOfGames.Blackjack.BlackjackCommand;
import com.general_hello.commands.commands.GroupOfGames.Blackjack.GameHandler;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Utils.MoneyData;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class UnoCommand implements ICommand {

    private GameHandler gameHandler;

    public UnoCommand (GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    @Override
    public void handle(CommandContext e) throws InterruptedException {
        if (GameHandler.getUnoGame(e.getGuild().getIdLong()) != null) {
            e.getChannel().sendMessage("A game has already started").queue();
            return;
        }
        int bet = 0;
        if (e.getArgs().size() == 1) {
            bet = BlackjackCommand.getInt(e.getArgs().get(0));
            if (bet >= 100) {
                if (!(MoneyData.money.get(e.getAuthor()) - bet >= 0)) {
                    e.getChannel().sendMessage(String.format("You don't have enough credits to make a %d credits bet", bet)).queue();
                    return;
                }
            } else {
                e.getChannel().sendMessage("You need to place a bet for at least 10 credits").queue();
                return;
            }
        } else if (e.getArgs().size() > 1) {
            e.getChannel().sendMessage("You need to place a valid bet").queue();
            return;
        }
        UnoGame unogame = new UnoGame(bet, e.getAuthor().getIdLong(), e.getChannel().getIdLong());
        gameHandler.setUnoGame(e.getGuild().getIdLong(), unogame);
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.cyan);
        eb.setTitle("A game of uno is going to start!");
        if (bet != 0)
            eb.setDescription(String.format("This game requires a %d credits bet.\nThe winner receives the sum of all bets", bet));
        eb.addField("Players", "No Players yet", false);
        eb.setFooter("React with \uD83D\uDD90️ to join, ▶️ to start and ❌ to cancel the game");
        e.getChannel().sendMessageEmbeds(eb.build()).queue(m -> {
            unogame.setMessageID(m.getIdLong());
            m.addReaction("\uD83D\uDD90️").queue();
            m.addReaction("▶️").queue();
            m.addReaction("❌").queue();
        });
    }

    @Override
    public String getName() {
        return "startuno";
    }

    @Override
    public String getHelp(String prefix) {
        return "Starts an uno game!!!\n" +
                "Usage: `" + prefix + "startuno [money]`";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.GAMES;
    }
}
