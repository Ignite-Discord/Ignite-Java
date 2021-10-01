package com.general_hello.commands.commands.GroupOfGames.Games;

import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.commands.GroupOfGames.Blackjack.GameHandler;
import com.general_hello.commands.commands.RankingSystem.LevelPointManager;
import com.general_hello.commands.commands.Utils.UtilNum;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;
import java.util.List;

public class GuessNumber implements Game {

    public boolean isEnded = false;
    private HashMap<User, Integer> numberHash = new HashMap<>();
    private int count = 8;
    private GuildMessageReceivedEvent e;

    public GuessNumber(GuildMessageReceivedEvent event)
    {
        e = event;

        startGame(event.getAuthor());
    }

    @Override
    public void startGame(User user) {
        numberHash.put(user, UtilNum.randomNum(0, 1000));

        e.getChannel().sendMessage("1️⃣ Guess a number between 0 and 1000! You have " + count  + " chances.").queue();
    }

    @Override
    public void endGame(User user) {
        numberHash.remove(user);
        count = 8;
        isEnded = true;
    }

    @Override
    public void sendInput(List<String> in, GuildMessageReceivedEvent event) {
        int innum = Integer.parseInt(in.get(0));

        if(isEnded)
        {
            e.getChannel().sendMessage("🛑 game haven't started yet!").queue();
            return;
        }

        Integer number = numberHash.get(event.getAuthor());

        count--;
        if(innum == number)
        {


            e.getChannel().sendMessage(e.getAuthor().getAsMention() + " won! The number was " + number + ".\n" +
                    "\uD83E\uDE99 " + (2000) + " was added to your account").queue();
            GameHandler.removeBlackJackGame(e.getAuthor().getIdLong());
            LevelPointManager.feed(e.getAuthor(), 20);
            DatabaseManager.INSTANCE.setCredits(e.getAuthor().getIdLong(), 2000);
            GameHandler.removeBlackJackGame(e.getAuthor().getIdLong());
            endGame(event.getAuthor());
            return;
        }

        else if(count == 0)
        {
            e.getChannel().sendMessage("⏰ Time's up! The number was " + number + ".").queue();
            endGame(e.getAuthor());
            return;
        }

        else if(innum < number)
        {
            e.getChannel().sendMessage("Higher! ⬆" +
                    "\nYou got " + count + " chances left.").queue();
        }

        else if(innum > number)
        {
            e.getChannel().sendMessage("Lower! ⬇ " +
                    "\nYou got " + count + " chances left.").queue();
        }
    }

    public int getNumber(User user) {
        return numberHash.get(user);
    }
}
