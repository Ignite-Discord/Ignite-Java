package com.general_hello.commands.commands.DefaultCommands;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.Emoji.Emoji;
import com.general_hello.commands.commands.GetData;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Others.UpdateIgniteCoinsCommand;
import com.general_hello.commands.commands.Register.Data;
import com.general_hello.commands.commands.User.UserPhoneUser;
import com.general_hello.commands.commands.Utils.EmbedUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BalanceCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        User user = ctx.getAuthor();

        if (!ctx.getMessage().getMentionedMembers().isEmpty()) user = ctx.getMessage().getMentionedUsers().get(0);

        GetData getData = new GetData();
        getData.checkIfContainsData(user, ctx);

        if (!Data.userUserPhoneUserHashMap.containsKey(user)) {
            ctx.getChannel().sendMessage(Emoji.ERROR + " Kindly register `ignt register` first before using this command!").queue();
            return;
        }

        List<List<Object>> lists = UpdateIgniteCoinsCommand.loadData(true);
        UserPhoneUser userPhoneUser = Data.userUserPhoneUserHashMap.get(user);

        UpdateIgniteCoinsCommand.getSpecificData(lists, userPhoneUser.getUserPhoneUserName());
        Integer balance = userPhoneUser.getBalance();

        if (balance == null) {
            MessageEmbed messageEmbed = EmbedUtil.errorEmbed("The balance hasn't been updated. Kindly ping one of the developers about this issue.");
            ctx.getChannel().sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(user.getName() + "'s Balance").setFooter("Nice balance you have 😀");
        embedBuilder.setDescription("Balance: **" + balance + "** ignite coins\n" +
                "Credits: **Soon to Come**");
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
