package com.general_hello.commands.commands.User;

import net.dv8tion.jda.api.entities.User;

public class UserPhoneUser {
    private String userPhoneUserName;
    private Integer balance = -1;
    private User discordUser;
    private String webhookUrl = null;

    public UserPhoneUser(String userPhoneUserName, User discordUser) {
        this.userPhoneUserName = userPhoneUserName;
        this.discordUser = discordUser;
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

    public void setBalance(Integer balance) {
        System.out.println("Balance updated to: " + balance);
        this.balance = balance;
    }

    public User getDiscordUser() {
        return discordUser;
    }
}
