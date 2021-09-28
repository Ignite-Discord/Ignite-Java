package com.general_hello.commands;


import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.DefaultCommands.*;
import com.general_hello.commands.commands.DefaultCommands.PingCommand;
import com.general_hello.commands.commands.GroupOfGames.Blackjack.*;
import com.general_hello.commands.commands.GroupOfGames.Games.GuessNumberCommand;
import com.general_hello.commands.commands.GroupOfGames.Games.HangManCommand;
import com.general_hello.commands.commands.GroupOfGames.Games.TriviaCommand;
import com.general_hello.commands.commands.GroupOfGames.MiniGames.ChessRequest;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Info.AboutCommand;
import com.general_hello.commands.commands.Info.InfoServerCommand;
import com.general_hello.commands.commands.Info.InfoUserCommand;
import com.general_hello.commands.commands.Math.MathCommand;
import com.general_hello.commands.commands.MusicPlainCommand.*;
import com.general_hello.commands.commands.Others.*;
import com.general_hello.commands.commands.RankingSystem.ViewRank;
import com.general_hello.commands.commands.Register.RegisterCommand;
import com.general_hello.commands.commands.Uno.ChallengeCommand;
import com.general_hello.commands.commands.Uno.DrawCommand;
import com.general_hello.commands.commands.Uno.PlayCardCommand;
import com.general_hello.commands.commands.Uno.UnoCommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();
    public static ArrayList<String> commandNames = new ArrayList<>();
    public static ArrayList<String> cmdNames = new ArrayList<>();
    private final long GAMES = 891569196427804672L;
    private final long MUSIC = 891568732810408007L;
    private final long WALLET = 891570475220754462L;
    private final boolean testing = false;
    private final long OTHERS = 892205334721101844L;

    public CommandManager(EventWaiter waiter) {

        //Default Commands
        addCommand(new HelpCommand(this));
        addCommand(new PingCommand());
        addCommand(new AboutCommand(Color.cyan, "a multipurpose bot created by Igniters for Igniters!"));
        addCommand(new UptimeCOmmand());
        addCommand(new InfoUserCommand());
        addCommand(new InfoServerCommand());
        addCommand(new RegisterCommand());
        addCommand(new SetPrefixCommand());
        addCommand(new MathCommand());

        //mini Games
        addCommand(new ChessRequest());

        //games
        GameHandler gameHandler = new GameHandler();

        //uno
        addCommand(new DrawCommand(gameHandler));
        addCommand(new ChallengeCommand());
        addCommand(new UnoCommand(gameHandler));
        addCommand(new PlayCardCommand(gameHandler));

        //minigames
        addCommand(new HangManCommand());
        addCommand(new TriviaCommand());
        addCommand(new GuessNumberCommand());

        //blackjack
        //TODO: MAKE BLACK JACK TO BUTTONS INSTEAD OF COMMANDS
        addCommand(new BlackjackCommand(gameHandler));
        addCommand(new DoubleDownCommand());
        addCommand(new HitCommand());
        addCommand(new SplitCommand());
        addCommand(new StandCommand());

        //rank
        addCommand(new ViewRank());

        //music
        addCommand(new PauseCommand());
        addCommand(new PlayCommand());
        addCommand(new QueueCommand());
        addCommand(new RepeatCommand());
        addCommand(new ResumeCommand());
        addCommand(new SkipCommand());
        addCommand(new VolumeCommand());

        //others
        addCommand(new JokeCommand());
        addCommand(new LockDownCommand());
        addCommand(new UnLockDownCommand());
        addCommand(new BalanceCommand());
        addCommand(new PasteCommand());
        addCommand(new SayCommand());
        addCommand(new UpdateIgniteCoinsCommand());
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present, " + cmd.getName() + " in " + cmd.getClass());
        }

        cmdNames.add(cmd.getName());
        System.out.println(Bot.ANSI_BLUE + "Loaded the ign " + cmd.getName() + " -> " + cmd.getClass() + Bot.ANSI_RESET);
        commands.add(cmd);
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }


    void handle(GuildMessageReceivedEvent event, String prefix) throws InterruptedException, IOException, SQLException {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(prefix), "")
                .replaceFirst("\\s+", "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            if (!Listener.count.containsKey(invoke)) {
                Listener.count.put(invoke, 1);
                commandNames.add(invoke);
            } else {
                Integer lastCount = Listener.count.get(invoke);
                Listener.count.put(invoke, lastCount + 1);

                if (!commandNames.contains(invoke)) commandNames.add(invoke);
            }

            if (event.getGuild().getIdLong() != 860295266765635584L) {
                if (!testing) {
                    if (!cmd.getCategory().equals(CommandType.SPECIAL)) {
                        switch (cmd.getCategory()) {
                            case GAMES:
                                if (event.getChannel().getIdLong() != (GAMES)) return;
                            case MUSIC:
                                if (event.getChannel().getIdLong() != (MUSIC)) return;
                            case OTHERS:
                                if (event.getChannel().getIdLong() != (OTHERS)) return;
                            case WALLET:
                                if (event.getChannel().getIdLong() != (WALLET)) return;
                        }
                    }
                }
            }

            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);

        }
    }
}
