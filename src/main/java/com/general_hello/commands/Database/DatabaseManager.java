package com.general_hello.commands.Database;

import java.sql.SQLException;

public interface DatabaseManager {
    DatabaseManager INSTANCE = new SQLiteDataSource();

    String getPrefix(long guildId);
    String getName(long userId);
    Integer getXpPoints(long userId) throws SQLException;
    void setXpPoints(long userId, long xpPoints);

    void setPrefix(long guildId, String newPrefix);
    void newInfo(long userId, String userName);
    void setName(long userId, String name);
}