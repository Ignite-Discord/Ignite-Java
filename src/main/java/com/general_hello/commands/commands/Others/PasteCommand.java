package com.general_hello.commands.commands.Others;

import com.general_hello.commands.Config;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.PrefixStoring;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.menudocs.paste.PasteClient;
import org.menudocs.paste.PasteClientBuilder;

import java.util.List;

public class PasteCommand implements ICommand {

    private final PasteClient client = new PasteClientBuilder()
            .setUserAgent("MenuDocs Tutorial bot")
            .setDefaultExpiry("10m")
            .build();

    @Override
    public void handle(CommandContext ctx) {
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        if (args.size() < 2) {
            channel.sendMessage("Incorrect input\n" +
                    "Usage: `" + prefix + "sharecode [language] [text]`").queue();
            return;
        }

        final String language = args.get(0);
        final String contentRaw = ctx.getMessage().getContentRaw();
        final int index = contentRaw.indexOf(language) + language.length();
        final String body = contentRaw.substring(index).trim();

        client.createPaste(language, body).async(
                (id) -> client.getPaste(id).async((paste) -> {
                    EmbedBuilder builder = new EmbedBuilder()
                            .setTitle("Paste " + id, paste.getPasteUrl())
                            .setDescription("```")
                            .appendDescription(paste.getLanguage().getId())
                            .appendDescription("\n")
                            .appendDescription(paste.getBody())
                            .appendDescription("```");

                    channel.sendMessage(builder.build()).queue();
                })
        );
    }

    @Override
    public String getName() {
        return "sharecode";
    }

    @Override
    public String getHelp(String prefix) {
        return "Creates a formatted site that has the code\n" +
                "Usage: `" + prefix + "sharecode [language] [text]`";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.SPECIAL;
    }
}
