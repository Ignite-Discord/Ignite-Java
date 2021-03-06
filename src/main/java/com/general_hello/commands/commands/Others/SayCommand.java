package com.general_hello.commands.commands.Others;


import com.general_hello.commands.Config;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.PrefixStoring;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.awt.*;

public class SayCommand implements ICommand {
    @Override
    public void handle(CommandContext e) {
        if (!e.getAuthor().getId().equals(Config.get("owner_id")) && !e.getAuthor().getId().equals(Config.get("owner_id_partner"))) {
            e.getChannel().sendMessage("Do I know you?").queue();
            return;
        }
            final long guildID = e.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        if(e.getArgs().isEmpty()) {
            return;
        }

        if("embed".equals(e.getArgs().get(0))) {
                String[] tokens = e.getMessage().getContentRaw().split(" ");
                StringBuilder content = new StringBuilder();

                for (int i = 0; i < tokens.length; i++) {
                    content.append(i == 0 || i == 1 || i == 2 || i == 3 ? "" : tokens[i] + " "); //Ignore first two tokens: ignt say and embed
                }

                e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE);

                EmbedBuilder embedmsg = new EmbedBuilder();
            float[] floats = Color.RGBtoHSB(225, 215, 0, null);
            embedmsg.setColor(Color.getHSBColor(floats[0], floats[1], floats[2]));

                embedmsg.setAuthor(e.getArgs().get(1), null, e.getAuthor().getAvatarUrl());
                embedmsg.setDescription(content.toString());
                embedmsg.setFooter("Announced by " + e.getAuthor().getName(), null);
                e.getChannel().sendMessageEmbeds(embedmsg.build()).queue(
                        (message) -> {
                            if (e.getAuthor().getId().equals(Config.get("owner_id"))) {
                                message.addReaction("????????").queue();
                                message.addReaction("????????").queue();
                            }
                        }
                );

            e.getMessage().delete().queue();
        }

        else
        {
            String[] tokens = e.getMessage().getContentRaw().split(" ");
            StringBuilder content = new StringBuilder();

            for(int i = 1; i < tokens.length; i++) {
                content.append(i == 1 ? "" : tokens[i] + " "); //Ignore first token: =say
            }
            e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE);

            e.getChannel().sendMessage(content.toString()).queue();

            e.getMessage().delete().queue();
        }
    }

    @Override
    public String getName() {
        return "say";
    }

    @Override
    public String getHelp(String prefix) {
        return "This command is for letting a bot say something for you.\n"
                + "Command Usage: `" + prefix + "say`\n"
                + "Parameter: `[Content] | embed [Content]| null`\n"
                + "[Content]: The sentence you want the bot to say in normal message form.\n"
                + "embed [Content]: The sentence you want the bot to say in embed message form.\n"
                + "Support @mention(s): @everyone, @here, and @user.";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.SPECIAL;
    }
}
