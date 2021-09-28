package com.general_hello.commands.Database;

import com.general_hello.commands.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class SQLiteDataSource implements DatabaseManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLiteDataSource.class);
    public static Connection connection = null;

    public SQLiteDataSource() {
        try {
            final File dbFile = new File("database.db");

            if (!dbFile.exists()) {
                if (dbFile.createNewFile()) {
                    LOGGER.info("Created database file");
                } else {
                    LOGGER.info("Could not create database file");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Opened database successfully");

        try (final Statement statement = getConnection().createStatement()) {
            final String defaultPrefix = Config.get("prefix");

            // language=SQLite
            String[] commands = new String[]{
                    "CREATE TABLE IF NOT EXISTS guild_settings (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "guild_id VARCHAR(20) NOT NULL," +
                            "prefix VARCHAR(255) NOT NULL DEFAULT '" + defaultPrefix + "'" +
                            ");",
                    "CREATE TABLE IF NOT EXISTS UserData ( UserId INTEGER NOT NULL, " +
                            "UserName TEXT NOT NULL, " +
                            "PRIMARY KEY(UserId) ) WITHOUT ROWID",
                    "CREATE TABLE IF NOT EXISTS XPSystemUser (" +
                            "userId INTEGER UNIQUE," +
                            "xpPoints INTEGER DEFAULT 0" +
                            ");",
                    "CREATE TABLE IF NOT EXISTS GuildSettings (" +
                            "XPSystem INTEGER DEFAULT 0," +
                            "GuildId INTEGER" +
                            ");"
            };

            Connection connection = getConnection();

            if (connection == null) return;

            try {
                for (String command : commands) {
                    PreparedStatement ps = connection.prepareStatement(command);
                    ps.execute();
                    ps.close();
                }
            } catch (Exception e) {
                LOGGER.error("Could not run command", e);
            }
            LOGGER.info("Table initialised");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPrefix(long guildId) {
        System.out.println("Get prefix");

        try (final PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT prefix FROM guild_settings WHERE guild_id = ?")) {

            preparedStatement.setString(1, String.valueOf(guildId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("prefix");
                }
            }

            try (final PreparedStatement insertStatement = getConnection()
                    .prepareStatement("INSERT INTO guild_settings(guild_id) VALUES(?)")) {

                insertStatement.setString(1, String.valueOf(guildId));

                insertStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Config.get("prefix");
    }

    @Override
    public void setPrefix(long guildId, String newPrefix) {
        System.out.println("Set prefix");

        try (final PreparedStatement preparedStatement = getConnection()
                .prepareStatement("UPDATE guild_settings SET prefix = ? WHERE guild_id = ?")) {

            preparedStatement.setString(1, newPrefix);
            preparedStatement.setString(2, String.valueOf(guildId));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName(long userId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT UserName FROM UserData WHERE UserId = ?")) {

            preparedStatement.setString(1, String.valueOf(userId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("UserName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getXpPoints(long userId) throws SQLException {
        System.out.println("Get XP points");
        try {
            Statement statement = getConnection().createStatement();
            String sql = "SELECT xpPoints FROM XPSystemUser WHERE userId=" + userId;

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                return result.getInt("xpPoints");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Statement statement = getConnection().createStatement();
            String sql = "INSERT INTO XPSystemUser(userId) VALUES(" + userId + ")";

            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public void setXpPoints(long userId, long xpPoints) {
        System.out.println("Set XP points");
        try {
            System.out.println("Connection closed? " + getConnection().isClosed());
        } catch (Exception ignored) {}

        try {
            if (getConnection() == null) return;

            Statement statement = getConnection().createStatement();

            String sql = "UPDATE XPSystemUser SET xpPoints=" + (xpPoints) + " WHERE UserId=" + userId;

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newInfo(long userId, String userName) {
        System.out.println("New info");

        try (final PreparedStatement preparedStatement = getConnection()
             .prepareStatement("INSERT INTO UserData" +
                     "(UserId, UserName)" +
                     "VALUES (?, ?);")) {

            preparedStatement.setString(1, String.valueOf(userId));
            preparedStatement.setString(2, userName);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("Added the user to the database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setName(long userId, String name) {
        System.out.println("Set name");

        try (final PreparedStatement preparedStatement = getConnection()
                .prepareStatement("UPDATE UserData SET UserName=? WHERE UserId=?"
                )) {

            preparedStatement.setString(2, String.valueOf(userId));
            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        if (connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        }
        return connection;
    }
}
