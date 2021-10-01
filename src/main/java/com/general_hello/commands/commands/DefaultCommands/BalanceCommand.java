package com.general_hello.commands.commands.DefaultCommands;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.GetData;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Others.UpdateIgniteCoinsCommand;
import com.general_hello.commands.commands.Register.Data;
import com.general_hello.commands.commands.User.UserPhoneUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BalanceCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        User user = ctx.getAuthor();


        if (!ctx.getMessage().getMentionedMembers().isEmpty()) {
            user = ctx.getMessage().getMentionedUsers().get(0);
        }

        GetData getData = new GetData();
        getData.checkIfContainsData(user, ctx);

        UpdateIgniteCoinsCommand.loadData();

        UserPhoneUser userPhoneUser = Data.userUserPhoneUserHashMap.get(user);

        if (userPhoneUser == null) {
            ctx.getChannel().sendMessage("Kindly register with the bot by using `ignt register`").queue();
            return;
        }
        Integer balance = userPhoneUser.getBalance();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.CYAN);
        embedBuilder.setTitle(user.getName() + "'s Balance").setFooter("Nice balance you have 😀");
        embedBuilder.setDescription("Balance: **" + (balance == null ? "You are not in the spreadsheet of ignite coins!**\n" : balance + "** ignite coins\n") +
                "Credits: ** " + userPhoneUser.getCredits() + " credits**");
        ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }

    @Override
    public CommandType getCategory() {
        return CommandType.WALLET;
    }

    @Override
    public String getName() {
        return "bal";
    }

    @Override
    public String getHelp(String prefix) {
        return "Shows your ignite coins!\n" +
                "Example: `" + prefix + getName() + "`";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();
        aliases.add("balance");
        aliases.add("coins");
        aliases.add("coin");
        return aliases;
    }
}
