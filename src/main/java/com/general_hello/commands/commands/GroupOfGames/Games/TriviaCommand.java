package com.general_hello.commands.commands.GroupOfGames.Games;


import com.general_hello.commands.Database.FilePath;
import com.general_hello.commands.Listener;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Utils.UtilNum;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TriviaCommand implements ICommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    public static ArrayList<User> storeUser = new ArrayList<>();
    public static HashMap<User, String> storeAnswer = new HashMap<>();

    @Override
    public void handle(CommandContext ctx) {
        String respond = "";
        int totalline = 0;

        try {
            totalline = UtilNum.getLineCount(FilePath.EightBall);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        int magic = UtilNum.randomNum(0, totalline), line = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FilePath.EightBall));

            while((respond = reader.readLine()) != null) {
                line++;
                if(line >= magic)
                    break;
            }
            reader.close();

        } catch (IOException io) {
            LOGGER.info(this.getClass().getName(), io, "BufferedReader at getting response.");
        }

        String[] splitString = respond.split("-");
        String msg = ctx.getAuthor().getAsMention() + splitString[0];
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Trivia!!!");
        embedBuilder.addField("Question:", msg, false);
        embedBuilder.setColor(Color.cyan);
        embedBuilder.setFooter("A correct answer will give you \uD83E\uDE99 1000");
        ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        storeAnswer.put(ctx.getAuthor(), splitString[1]);
        storeUser.add(ctx.getAuthor());
    }


    @Override
    public String getName() {
        return "trivia";
    }

    @Override
    public String getHelp(String prefix) {
        return "Makes the bot send a trivia message!!!\n" +
                "Usage: `" + prefix + "trivia`";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.GAMES;
    }
}
