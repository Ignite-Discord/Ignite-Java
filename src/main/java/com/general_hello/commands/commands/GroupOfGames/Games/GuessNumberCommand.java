package com.general_hello.commands.commands.GroupOfGames.Games;

import com.general_hello.commands.Listener;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class GuessNumberCommand implements ICommand {

    private final HashMap<User, GuessNumber> guessNumberHashMap = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    @Override
    public void handle(CommandContext ctx) {
        if(ctx.getArgs().isEmpty())
        {
            ctx.getChannel().sendMessage("Invalid Input").queue();
            return;
        }

        else if(ctx.getArgs().size() == 0 || (ctx.getArgs().size() > 0 && "start".equals(ctx.getArgs().get(0))))
        {
            guessNumberHashMap.put(ctx.getAuthor(), new GuessNumber(ctx.getEvent()));
            return;
        }

        GuessNumber gn = this.guessNumberHashMap.get(ctx.getAuthor());

        if(ctx.getArgs().size() > 0 && "end".equals(ctx.getArgs().get(0)))
        {
            gn.endGame(ctx.getAuthor());
            ctx.getChannel().sendMessage("Game ended! The number was " + gn.getNumber(ctx.getAuthor()) + ".").queue();
        }

        else
        {
            try {
                if(!gn.isEnded)
                    gn.sendInput(ctx.getArgs(), ctx.getEvent());
                else
                    ctx.getChannel().sendMessage("\uD83D\uDED1 Game haven't started yet!").queue();
            } catch(NullPointerException en) {
                ctx.getChannel().sendMessage("\uD83D\uDED1 Game haven't started yet!").queue();
                LOGGER.info(en + this.getClass().getName(), "Game haven't started.");
            }
        }
    }

    @Override
    public String getName() {
        return "gn";
    }

    @Override
    public String getHelp(String prefix) {
        return "Plays Guess the Number\n" +
                "Usage: `" + prefix + "gn`" +
                "\n" +
                "Parameter: `start | number`";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.GAMES;
    }
}
