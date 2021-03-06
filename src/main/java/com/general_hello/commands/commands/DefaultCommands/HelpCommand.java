package com.general_hello.commands.commands.DefaultCommands;

import com.general_hello.commands.CommandManager;
import com.general_hello.commands.Config;
import com.general_hello.commands.RPG.Objects.RPGEmojis;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.Emoji.Emojis;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Info.InfoUserCommand;
import com.general_hello.commands.commands.PrefixStoring;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class HelpCommand implements ICommand {
    public static EmbedBuilder embedBuilder = buildDefaultHelpEmbed();
    public static ArrayList<ActionRow> actionRows;
    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    private static void defaultActionRows(CommandContext ctx) {
        boolean disableOrEnable = !ctx.getMember().hasPermission(Permission.MANAGE_SERVER) && !ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById(888627140046749697L));
        ArrayList<ActionRow> actionRow = new ArrayList<>();
        actionRow.add(ActionRow.of(
            Button.secondary(ctx.getMember().getUser().getId() + ":currency", "Currency").withEmoji(Emoji.fromEmote("ignt_coins", 905999722374905857L, false)),
            Button.secondary(ctx.getMember().getUser().getId() + ":user", "User").withEmoji(Emoji.fromEmote("user", Long.parseLong("862895295239028756"), true)),
            Button.secondary(ctx.getMember().getUser().getId() + ":bot", "Bot").withEmoji(Emoji.fromEmote("discord_bot", Long.parseLong("862895574960701440"), false)),
            Button.secondary(ctx.getMember().getUser().getId() + ":info", "Info").withEmoji(Emoji.fromEmote("info", Long.parseLong("870871190217060393"), true)),
            Button.secondary(ctx.getMember().getUser().getId() + ":game", "Games").withEmoji(Emoji.fromEmote("games", Long.parseLong("891146612016025630"), true))));
        actionRow.add(ActionRow.of(
            Button.secondary(ctx.getMember().getUser().getId() + ":rpg", "RPG").withEmoji(Emoji.fromMarkdown("<:RPG:899933662185918464>")),
            Button.secondary(ctx.getMember().getUser().getId() + ":mod", "Moderation").withDisabled(disableOrEnable).withEmoji(Emoji.fromEmote("mod", Long.parseLong("862898484041482270"), true)),
            Button.danger(ctx.getMember().getUser().getId() + ":end", "Cancel").withEmoji(Emoji.fromEmote("cancel", Long.parseLong("863204248657461298"), true))));
        actionRows = actionRow;
    }

    public static void updateDefaultActionRows(ButtonClickEvent ctx) {
        boolean disableOrEnable = !ctx.getMember().hasPermission(Permission.MANAGE_SERVER) && !ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById(888627140046749697L));
        ArrayList<ActionRow> actionRow = new ArrayList<>();
        actionRow.add(ActionRow.of(
                Button.secondary(ctx.getMember().getUser().getId() + ":currency", "Currency").withEmoji(Emoji.fromEmote("ignt_coins", 905999722374905857L, false)),
                Button.secondary(ctx.getMember().getUser().getId() + ":user", "User").withEmoji(Emoji.fromEmote("user", Long.parseLong("862895295239028756"), true)),
                Button.secondary(ctx.getMember().getUser().getId() + ":bot", "Bot").withEmoji(Emoji.fromEmote("discord_bot", Long.parseLong("862895574960701440"), false)),
                Button.secondary(ctx.getMember().getUser().getId() + ":info", "Info").withEmoji(Emoji.fromEmote("info", Long.parseLong("870871190217060393"), true)),
                Button.secondary(ctx.getMember().getUser().getId() + ":game", "Games").withEmoji(Emoji.fromEmote("games", Long.parseLong("891146612016025630"), true))));
        actionRow.add(ActionRow.of(
                Button.secondary(ctx.getMember().getUser().getId() + ":rpg", "RPG").withEmoji(Emoji.fromMarkdown("<:RPG:899933662185918464>")),
                Button.secondary(ctx.getMember().getUser().getId() + ":mod", "Moderation").withDisabled(disableOrEnable).withEmoji(Emoji.fromEmote("mod", Long.parseLong("862898484041482270"), true)),
                Button.danger(ctx.getMember().getUser().getId() + ":end", "Cancel").withEmoji(Emoji.fromEmote("cancel", Long.parseLong("863204248657461298"), true))));
        actionRows = actionRow;
    }

    private static EmbedBuilder buildDefaultHelpEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Groups");
        embedBuilder.setColor(Color.cyan);
        embedBuilder.addField(Emojis.igntCoins + " | Currency (9)", "Shows basic to complex commands that the user can do with their currency", false);
        embedBuilder.addField(Emojis.USER + " | User (6)", "Shows basic to complex commands that the user can do with the bot", false);
        embedBuilder.addField(Emojis.DISCORD_BOT + " | Bot (3)", "Shows the commands you can do with the bot", false);
        embedBuilder.addField(Emojis.INFO + " | Info (2)", "Shows basic to complex information about users, servers, or mods", false);
        embedBuilder.addField(Emojis.MOD + " | Moderation (7)","Basic to advanced moderation tools used by staff to control or monitor the server.", false);
        embedBuilder.addField(Emojis.GAME + " | Games (6)","Fun games.", false);
        embedBuilder.addField(Emojis.RPG + " | RPG (9)","Fun RPG commands for igniters", false);
        embedBuilder.addField( RPGEmojis.mythical_chest + " | Others (?)","Commands made by <@756308319622397972>", false);
        embedBuilder.setFooter("Click the buttons below to see its categories commands.");
        return embedBuilder;
    }
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        defaultActionRows(ctx);
        if (args.isEmpty()) {
            channel.sendMessageEmbeds(embedBuilder.build()).setActionRows(
                    actionRows
            ).queue();

            return;
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if (command == null) {
            channel.sendMessage("No help info found for " + search).queue();
            return;
        }

        embedBuilder.setTitle("Help results for " + search);
        embedBuilder.setColor(InfoUserCommand.randomColor());
        embedBuilder.setDescription(command.getHelp(prefix + " "));
        embedBuilder.setTimestamp(OffsetDateTime.now());
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp(String prefix) {
        return "Shows the list with commands in the bot\n" +
                "Usage: `" + prefix + "help [command]`";
    }

    @Override
    public List<String> getAliases() {
        List<String> strings = new java.util.ArrayList<>();
        strings.add("cmds");
        strings.add("commands");
        strings.add("commandlist");
        return strings;
    }

    @Override
    public CommandType getCategory() {
        return CommandType.SPECIAL;
    }
}
