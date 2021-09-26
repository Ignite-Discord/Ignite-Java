package com.general_hello.commands.commands.DefaultCommands;

import com.general_hello.commands.Config;
import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.PrefixStoring;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class SetPrefixCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final List<String> args = ctx.getArgs();
        final Member member = ctx.getMember();

        if (!member.hasPermission(Permission.MANAGE_SERVER) && !member.getId().equals(Config.get("owner_id"))) {
            channel.sendMessage("You must have the Manage Server permission to use his command").queue();
            return;
        }

        long guildId = ctx.getGuild().getIdLong();

        if (args.isEmpty()) {
            channel.sendMessage("Missing args").queue();
            return;
        }

        final String newPrefix = String.join("", args);
        updatePrefix(guildId, newPrefix);

        channel.sendMessageFormat("New prefix has been set to `%s` for `%s`", newPrefix, ctx.getJDA().getGuildById(guildId).getName()).queue();
    }

    @Override
    public String getName() {
        return "setprefix";
    }

    @Override
    public String getHelp(String prefix) {
        return "Sets the prefix for this server\n" +
                "Usage: '" + prefix + "setprefix <prefix>'";
    }

    private void updatePrefix(long guildId, String newPrefix) {
        PrefixStoring.PREFIXES.put(guildId, newPrefix);
        DatabaseManager.INSTANCE.setPrefix(guildId, newPrefix);
    }


    @Override
    public CommandType getCategory() {
        return CommandType.SPECIAL;
    }
}
