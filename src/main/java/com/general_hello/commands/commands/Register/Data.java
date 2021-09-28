package com.general_hello.commands.commands.Register;

import com.general_hello.commands.commands.User.UserPhoneUser;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {
    public static HashMap<User, Integer> progress = new HashMap<>();
    public static HashMap<User, ArrayList<String>> answers = new HashMap<>();
    public static ArrayList<UserPhoneUser> userPhoneUsers = new ArrayList<>();
    public static HashMap<User, UserPhoneUser> userUserPhoneUserHashMap = new HashMap<>();
    public static HashMap<String, UserPhoneUser> realNameUserPhoneUserHashMap = new HashMap<>();
}
