package com.general_hello.commands.commands.GroupOfGames.Games;


import Requests.OpenTDB;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Utils.UtilNum;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TriviaCommand implements ICommand {
    public static HashMap<User, String> storeAnswer = new HashMap<>();

    @Override
    public void handle(CommandContext ctx) {
        OpenTDB obj = new OpenTDB();

        obj.getTrivia();

        System.out.println(obj.getQuestion());
        System.out.println(obj.getCorrectAnswer());

        String[] incorrectAnswers = obj.incorrectAnswers;
        for (int i = 0; i < obj.incorrectAnswers.length ; i++) {
            System.out.println(obj.incorrectAnswers[i]);
        }


        SelectionMenu.Builder menu = SelectionMenu.create("menu:class")
                .setPlaceholder("Choose the correct answer") // shows the placeholder indicating what this menu is for
                .setRequiredRange(1, 1);

        int x = 0;
        ArrayList<String> arrayList = new ArrayList<>();

        while (x < incorrectAnswers.length) {
            arrayList.add(incorrectAnswers[x]);
            x++;
        }

        x = 0;

        arrayList.add(obj.getCorrectAnswer());
        int size = arrayList.size();
        while (x < size) {
            int random = UtilNum.randomNum(0, size-1 - (x));
            String choice = arrayList.get(random).replace("&quot;", "'").replace("&#039;", "'");
            System.out.println(choice);

            menu.addOption(choice, choice);
            arrayList.remove(choice);

            x++;
        }

        String msg = ctx.getAuthor().getAsMention() + " " + obj.getQuestion().replace("&quot;", "'").replace("&#039;", "'");
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Trivia!!!");
        embedBuilder.addField("Category: ", obj.getCategory(), true);
        embedBuilder.addField("Difficulty: ", obj.getDifficulty(), true);
        embedBuilder.addField("Question: ", msg, false);
        embedBuilder.setColor(Color.cyan);
        embedBuilder.setFooter("A correct answer will give you \uD83E\uDE99 1000");
        ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).setActionRow(menu.build()).queue();
        storeAnswer.put(ctx.getAuthor(), obj.getCorrectAnswer().replace("&quot;", "'").replace("&#039;", "'"));
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
