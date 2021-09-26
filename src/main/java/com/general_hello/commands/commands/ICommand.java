package com.general_hello.commands.commands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ICommand {
    void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException;

    String getName();

    String getHelp(String prefix);

    CommandType getCategory();

    default List<String> getAliases() {
        return new ArrayList<>();
    }
}