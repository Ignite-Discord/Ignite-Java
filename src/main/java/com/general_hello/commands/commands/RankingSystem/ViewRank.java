package com.general_hello.commands.commands.RankingSystem;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Utils.ErrorUtils;
import net.dv8tion.jda.api.entities.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class ViewRank implements ICommand
{
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        User member = ctx.getAuthor();

        if (!ctx.getMessage().getMentionedMembers().isEmpty()) {
            member = ctx.getMessage().getMentionedUsers().get(0);
        }

        try{
            ByteArrayOutputStream baos = LevelPointManager.getLevelPointCard(member).getByteArrayOutputStream();
            ctx.getEvent().getChannel().sendFile(baos.toByteArray(), member.getName() + "-stats.png").queue();
        }
        catch(Exception e){
            ErrorUtils.error(ctx, e);
        }
    }

    @Override
    public String getName() {
        return "rank";
    }

    @Override
    public String getHelp(String prefix) {
        return "Shows your rank!";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.SPECIAL;
    }
}
