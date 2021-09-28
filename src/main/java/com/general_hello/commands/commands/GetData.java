package com.general_hello.commands.commands;

import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.commands.Others.UpdateIgniteCoinsCommand;
import com.general_hello.commands.commands.Register.Data;
import com.general_hello.commands.commands.User.UserPhoneUser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetData {
    public static ArrayList<Guild> blackListedGuild = new ArrayList<>();
    public static ArrayList<User> blackListedUser = new ArrayList<>();
    public static HashMap<User, Long> xpMember = new HashMap<>();
    //get data from the db

    public int checkIfContainsData(User user, GuildMessageReceivedEvent ctx) {
        if (!Data.userUserPhoneUserHashMap.containsKey(user)) {
            return retrieveData(user.getIdLong(), ctx);
        }

        return 100;
    }

    public void checkIfContainsData(User user, SlashCommandEvent ctx) {
        if (!Data.userUserPhoneUserHashMap.containsKey(user)) {
            retrieveData(user.getIdLong(), ctx);
        }
    }

    public void checkIfContainsData(User user, CommandContext ctx) {
        if (!Data.userUserPhoneUserHashMap.containsKey(user)) {
            retrieveData(user.getIdLong(), ctx);
        }
    }

    public static long getLevelPoints(User member) throws SQLException {
        if (xpMember.containsKey(member)) {
            return xpMember.get(member);
        }

        return DatabaseManager.INSTANCE.getXpPoints(member.getIdLong());
    }

    public static void setLevelPoints(User user, long points) {
        DatabaseManager.INSTANCE.setXpPoints(user.getIdLong(), points);
        xpMember.put(user, points);
    }


    private int retrieveData(Long userId, GuildMessageReceivedEvent ctx) {
        if (blackListedUser.contains(ctx.getAuthor())) return -1;

        String name = DatabaseManager.INSTANCE.getName(userId);

        return addData(userId, name, ctx.getJDA());
    }

    private int retrieveData(Long userId, CommandContext ctx) {
        if (blackListedUser.contains(ctx.getAuthor())) return -1;

        String name = DatabaseManager.INSTANCE.getName(userId);

        return addData(userId, name, ctx.getJDA());
    }

    private int addData(Long userId, String name, JDA jda) {
        if (name != null) {
            User userById = jda.getUserById(userId);
            UserPhoneUser user = new UserPhoneUser(name, userById);
            Data.userUserPhoneUserHashMap.put(userById, user);
            Data.realNameUserPhoneUserHashMap.put(name, user);
            Data.userPhoneUsers.add(user);
            try {
                List<List<Object>> lists = UpdateIgniteCoinsCommand.loadData(false);
                if (lists == null) return -1;

                UpdateIgniteCoinsCommand.getSpecificData(lists, name);
            } catch (IOException e) {
                return -1;
            }
            return 0;
        }

        return -1;
    }

    private int retrieveData(Long userId, SlashCommandEvent ctx) {
        if (blackListedUser.contains(ctx.getUser())) return -1;

        String name = DatabaseManager.INSTANCE.getName(userId);


        return addData(userId, name, ctx.getJDA());
    }
}
