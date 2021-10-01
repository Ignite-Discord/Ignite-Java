package com.general_hello.commands.commands.Register;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.GetData;
import com.general_hello.commands.commands.ICommand;

import java.io.IOException;
import java.sql.SQLException;

public class SetNameCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        if (ctx.getArgs().isEmpty()) {
            ctx.getChannel().sendMessage("Kindly place your name after `ignt setname [name]`").queue();
            return;
        }

        String[] split = ctx.getArgs().toString().replace("[", "").replace("]", "").split(",");
        String name = split[0] + split[1];
        GetData getData = new GetData();
        getData.checkIfContainsData(ctx.getAuthor(), ctx);

        if (Data.userUserPhoneUserHashMap.containsKey(ctx.getAuthor())) {
            Data.userUserPhoneUserHashMap.get(ctx.getAuthor()).setUserPhoneUserName(name);
            GetData.setName(ctx.getAuthor(), name);
            ctx.getChannel().sendMessage("Successfully set the name to `" + name + "`").queue();
            return;
        }

        ctx.getChannel().sendMessage("You have not registered yet!").queue();
    }

    @Override
    public String getName() {
        return "setname";
    }

    @Override
    public String getHelp(String prefix) {
        return "Sets you name if you **CANNOT FOLLOW INSTRUCTIONS**";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.WALLET;
    }
}
