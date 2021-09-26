package com.general_hello.commands.commands.GroupOfGames.Games;

import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.Listener;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.PrefixStoring;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HangManCommand implements ICommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    public static HashMap<User, HangMan> userHangManHashMap = new HashMap<>();

    @Override
    public void handle(CommandContext ctx) {
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, DatabaseManager.INSTANCE::getPrefix);

        if(ctx.getArgs().size() == 0)
        {
            ctx.getChannel().sendMessage(getHelp(prefix + " ")).queue();
            return;
        }

        else if(ctx.getArgs().size() > 0 && "start".equals(ctx.getArgs().get(0)))
        {
            LOGGER.info(this.getClass().getName(), "HangMan Started.");
            userHangManHashMap.put(ctx.getAuthor(), new HangMan(ctx.getEvent()));
            return;
        }

        HangMan game = userHangManHashMap.get(ctx.getAuthor());

        if(ctx.getArgs().size() > 0 && "end".equals(ctx.getArgs().get(0)))
        {
            if(HangMan.starter.contains(ctx.getAuthor()))
                game.endGame(ctx.getAuthor());
            else
                ctx.getChannel().sendMessage("🛑 Only the game starter can end the game.").queue();
        }

        else
        {
            try {
                game.sendInput(ctx.getArgs(), ctx.getEvent());
            } catch(NullPointerException en) {
                ctx.getChannel().sendMessage("🛑 Game haven't started yet!").queue();
                LOGGER.info(en + this.getClass().getName(), "Game haven't started.");
            }
        }
    }

    @Override
    public String getName() {
        return "hm";
    }

    @Override
    public String getHelp(String prefix) {
        return "Play Hangman with anyone\n" +
                "Usage: `" + prefix + "hm`\n" +
                "Options: `start | [letter] | end | null`";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();
        aliases.add("hangman");
        return aliases;
    }


    @Override
    public CommandType getCategory() {
        return CommandType.GAMES;
    }
}
