package com.general_hello.commands.commands.Others;

import com.fasterxml.jackson.databind.JsonNode;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class JokeCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        WebUtils.ins.getJSONObject("https://apis.duncte123.me/joke").async((json) -> {
            if (!json.get("success").asBoolean()) {
                channel.sendMessage("Something went wrong, try again later").queue();
                System.out.println(json);
                return;
            }

            final JsonNode data = json.get("data");
            final String title = data.get("title").asText();
            final String url = data.get("url").asText();
            final String body = data.get("body").asText();
            final EmbedBuilder embed = EmbedUtils.getDefaultEmbed()
                    .setTitle(title, url)
                    .setDescription(body);

            channel.sendMessage(embed.build()).queue();
        });
    }

    @Override
    public String getName() {
        return "joke";
    }

    @Override
    public String getHelp(String prefix) {
        return "Usage: `" + prefix + "joke`\n" +
                "Shows a random joke";
    }


    @Override
    public CommandType getCategory() {
        return CommandType.OTHERS;
    }
}
