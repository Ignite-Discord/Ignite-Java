package com.general_hello.commands.commands.User;

import com.general_hello.commands.Database.DatabaseManager;
import net.dv8tion.jda.api.entities.User;

public class UserPhoneUser {
    private String userPhoneUserName;
    private Integer balance;
    private final User discordUser;
    private Integer credits;

    public UserPhoneUser(String userPhoneUserName, User discordUser, Integer credits) {
        this.userPhoneUserName = userPhoneUserName;
        this.discordUser = discordUser;
        this.credits = credits;
    }

    public String getUserPhoneUserName() {
        return userPhoneUserName;
    }

    public void setUserPhoneUserName(String userPhoneUserName) {
        this.userPhoneUserName = userPhoneUserName;
    }

    public Integer getBalance() {
        return balance;
    }

    public Integer getCredits() {
        return DatabaseManager.INSTANCE.getCredits(discordUser.getIdLong());
    }

    public void addCredits(Integer credits) {
        this.credits += credits;
        DatabaseManager.INSTANCE.setCredits(discordUser.getIdLong(), this.credits += credits);
    }

    public void setBalance(Integer balance) {
        System.out.println("Balance updated to: " + balance);
        this.balance = balance;
    }

    public User getDiscordUser() {
        return discordUser;
    }
}
