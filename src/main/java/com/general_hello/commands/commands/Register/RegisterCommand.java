package com.general_hello.commands.commands.Register;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.Emoji.Emojis;
import com.general_hello.commands.commands.GetData;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Info.InfoUserCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

import java.io.IOException;

public class RegisterCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        try {
            try {
                GetData getData = new GetData();
                getData.checkIfContainsData(ctx.getAuthor(), ctx);
            } catch (Exception ignored) {}

            if (Data.userUserPhoneUserHashMap.containsKey(ctx.getAuthor())) {
                ctx.getMessage().reply(Emojis.ERROR + " You are already registered!").queue();
                return;
            }
            SelectionMenu menu = SelectionMenu.create("menu:class")
                    .setPlaceholder("Choose your age") // shows the placeholder indicating what this menu is for
                    .setRequiredRange(1, 1) // only one can be selected
                    .addOption("< 13", "reject")
                    .addOption("13 - 21", "noice")
                    .addOption("22 - 40", "oh")
                    .addOption("41 +", "old")
                    .build();

            EmbedBuilder embedBuilder = new EmbedBuilder().setColor(InfoUserCommand.randomColor()).setTitle("Age");
            embedBuilder.setDescription("How old are you? Select your answer below.");
            String id = ctx.getAuthor().openPrivateChannel().complete().sendMessageEmbeds(embedBuilder.build())
                    .setActionRow(menu)
                    .complete().getPrivateChannel().getId();

            String privateChannelLink = "https://discord.com/channels/@me/" + id;
            privateChannelLink = "[DM](" + privateChannelLink + ")";
            embedBuilder = new EmbedBuilder().setTitle("Rules").setColor(InfoUserCommand.randomColor());
            embedBuilder.setDescription("Let's go to your " + privateChannelLink + " to continue shall we?");
            ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public String getHelp(String prefix) {
        return "Registers the user to the Ignite bot!!!\n" +
                "Usage: `" + prefix + getName() + "`";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.SPECIAL;
    }
}
