package com.general_hello.commands.commands.DefaultCommands;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;

import java.io.IOException;
import java.sql.SQLException;

public class BalanceCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {

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
}
