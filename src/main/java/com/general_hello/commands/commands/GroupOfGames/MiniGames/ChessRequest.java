package com.general_hello.commands.commands.GroupOfGames.MiniGames;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.Emoji.Emoji;
import com.general_hello.commands.commands.Emoji.EmojiObject;
import com.general_hello.commands.commands.GetData;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.Register.Data;
import com.general_hello.commands.commands.User.UserPhoneUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.Button;

import java.awt.*;
import java.io.IOException;
import java.time.OffsetDateTime;

public class ChessRequest implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException {
        if (ctx.getMessage().getMentionedUsers().isEmpty()) {
            ctx.getChannel().sendMessage(Emoji.ERROR + " Kindly mention the user you want to play with.").queue();
            return;
        }

        if (ctx.getMessage().getMentionedUsers().get(0).isBot()) {
            ctx.getChannel().sendMessage(Emoji.ERROR + " Kindly mention a user not a " + Emoji.DISCORD_BOT + ".").queue();
            return;
        }
        User mentionedUser = ctx.getMessage().getMentionedUsers().get(0);

        GetData getData = new GetData();
        getData.checkIfContainsData(ctx.getAuthor(), ctx);
        getData.checkIfContainsData(ctx.getMessage().getMentionedUsers().get(0), ctx);
        UserPhoneUser userPhoneUser = Data.userUserPhoneUserHashMap.get(ctx.getAuthor());

        String userName = userPhoneUser.getUserPhoneUserName();

        if (userName == null) {
            userName = ctx.getAuthor().getName() + "* (Unregistered " + Emoji.USER + " )";
        } else {
            userName = userName + "*";
        }

        ChessStoring.userToUser.put(mentionedUser, ctx.getAuthor());

        //Main code
        //Build the embed
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Request to Play Chess").setFooter("Click on accept if you accept the challenge!");
        embedBuilder.setDescription(ctx.getMember().getEffectiveName() + " is challenging you to a game of Chess!\n" +
                "\n**Challenger's Profile:**" +
                "\n" +

                "Userphone Name: *" + userName)
                .setTimestamp(OffsetDateTime.now()).setColor(Color.YELLOW)
        .setThumbnail("https://tryengineering.org/wp-content/uploads/bigstock-208614778-1024x683.jpg");

        ctx.getChannel().sendMessage(mentionedUser.getAsMention()).queue();

        EmojiObject emojiObject = Emoji.customEmojiToEmote(Emoji.CHECK);

        String messageID = ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).setActionRow(Button.primary(mentionedUser.getId() + ":acceptChess", "Accept")
                .withEmoji(net.dv8tion.jda.api.entities.Emoji.fromEmote(emojiObject.getEmojiName(), emojiObject.getEmojiID(), emojiObject.isAnimated()))).complete().getId();
        String messageLink = "https://discord.com/channels/" + ctx.getGuild().getId() + "/" + ctx.getChannel().getId() + "/" + messageID;
        //DM the requested user for the channel link
        mentionedUser.openPrivateChannel().queue((privateChannel -> {
            embedBuilder.clear();
            embedBuilder.setTitle("Request to Play Chess");
            embedBuilder.setColor(Color.YELLOW);
            embedBuilder.setDescription(ctx.getAuthor().getName() + " is challenging you a game of Chess in " + ctx.getGuild().getName() + " at " + ctx.getChannel().getAsMention() + " in this [message](" + messageLink + ")!");
            privateChannel.sendMessageEmbeds(embedBuilder.build()).queue();
        }));
    }

    @Override
    public String getName() {
        return "chess";
    }

    @Override
    public String getHelp(String prefix) {
        return "Plays chess with another user!\n" +
                "Usage: `" + prefix + getName() + "[mentioned user]";
    }
}
