package com.general_hello.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;

public class Status {
    public void status() {
        while (true) {
            System.out.println("Yeet");
            Bot.jda.getPresence().setActivity(Activity.watching("Made by General Hello#0101"));

            try {
                Thread.sleep(1000000);
            } catch (Exception ignored) {}

            JDA jda = Bot.jda;
            jda.getPresence().setActivity(Activity.watching("This bot uses over 3,000 lines of code"));

            try {
                Thread.sleep(1000000);
            } catch (Exception ignored) {}

            jda.getPresence().setActivity(Activity.watching("ignt help"));

            try {
                Thread.sleep(1000000);
            } catch (Exception ignored) {}

            jda.getPresence().setActivity(Activity.watching("Made by HELLO66#0066"));

            try {
                Thread.sleep(1000000);
            } catch (Exception ignored) {}

            jda.getPresence().setActivity(Activity.watching("Made by unjown#4644"));

            try {
                Thread.sleep(1000000);
            } catch (Exception ignored) {}

            jda.getPresence().setActivity(Activity.watching("Design made by SkyacinthClues#0822"));

            try {
                Thread.sleep(1000000);
            } catch (Exception ignored) {}

            jda.getPresence().setActivity(Activity.watching("The Passion of Christ"));

            try {
                Thread.sleep(1000000);
            } catch (Exception ignored) {}

            jda.getPresence().setActivity(Activity.watching("Merry Christmas! 🎄🎄🎄"));

            try {
                Thread.sleep(1000000);
            } catch (Exception ignored) {}
        }
    }
}
