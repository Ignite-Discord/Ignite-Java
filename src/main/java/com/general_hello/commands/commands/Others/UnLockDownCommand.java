package com.general_hello.commands.commands.Others;

import com.general_hello.commands.Config;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.Emoji.Emojis;
import com.general_hello.commands.commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UnLockDownCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        if (!ctx.getMember().hasPermission(Permission.MANAGE_SERVER) && !ctx.getMember().getId().equals(Config.get("owner_id"))) {
            ctx.getChannel().sendMessage("You don't have the `Manage Server permission` with you!").queue();
            return;
        }

        List<Role> lockdown = ctx.getGuild().getRolesByName("lockdown", true);
        if (lockdown.isEmpty()) {
            ctx.getChannel().sendMessage("There is no role called **Lockdown**").queue();
            return;
        }

        Role role = lockdown.get(0);
        List<Member> members = ctx.getGuild().getMembers();
        int x = 0;
        int y = 0;

        while (x < members.size()) {
            if (ctx.getGuild().getSelfMember().canInteract(members.get(x))) {
                System.out.println(members.get(x).getEffectiveName());
                ctx.getGuild().removeRoleFromMember(members.get(x), role).queue();
                y++;
            }
            x++;
        }

        ctx.getChannel().sendMessage("Lockdown is now ceased! For " + y + " users " + Emojis.USER).queue();
    }

    @Override
    public String getName() {
        return "unlockdown";
    }

    @Override
    public String getHelp(String prefix) {
        return "Removes the lockdown of the entire server!";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.SPECIAL;
    }
}
