package com.general_hello.commands.OtherEvents;

import com.general_hello.commands.Config;
import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.commands.DefaultCommands.BalanceCommand;
import com.general_hello.commands.commands.GroupOfGames.Blackjack.BlackjackGame;
import com.general_hello.commands.commands.GroupOfGames.Blackjack.GameHandler;
import com.general_hello.commands.commands.GroupOfGames.MiniGames.ChessStoring;
import com.general_hello.commands.commands.GroupOfGames.MiniGames.MainChessCode;
import com.general_hello.commands.commands.PrefixStoring;
import com.general_hello.commands.commands.RankingSystem.LevelPointManager;
import com.general_hello.commands.commands.Register.Data;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class OnButtonClick extends ListenerAdapter {
    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        // users can spoof this id so be careful what you do with this
        String[] id = event.getComponentId().split(":"); // this is the custom id we specified in our button
        String authorId = id[0];

        if (id.length == 1) {
            return;
        }

        String type = id[1];
        // When storing state like this is it is highly recommended to do some kind of verification that it was generated by you, for instance a signature or local cache

        if (!authorId.equals("0000") && !authorId.equals(event.getUser().getId())) {
            System.out.println("oof");
            return;
        }


        switch (type)
        {
            case "nope":
            case "end":
                event.getMessage().delete().queue();
                break;
            case "user":
                event.getMessage().delete().queue();
                event.getChannel().sendMessageEmbeds(helpCrap(4, event).build()).queue();
                return;
            case "bot":
                event.getMessage().delete().queue();
                event.getChannel().sendMessageEmbeds(helpCrap(2, event).build()).queue();
                return;
            case "info":
                event.getMessage().delete().queue();
                event.getChannel().sendMessageEmbeds(helpCrap(1, event).build()).queue();
                return;
            case "mod":
                event.getMessage().delete().queue();
                event.getChannel().sendMessageEmbeds(helpCrap(3, event).build()).queue();
                return;
            case "rpg":
                event.getMessage().delete().queue();
                event.getChannel().sendMessageEmbeds(helpCrap(6, event).build()).queue();
                return;
            case "game":
                SelectionMenu menu = SelectionMenu.create("menu:class")
                        .setPlaceholder("Choose the game you want to find help on") // shows the placeholder indicating what this menu is for
                        .setRequiredRange(1, 1) // only one can be selected
                        .addOption("Uno", "uno")
                        .addOption("Blackjack", "bj")
                        .addOption("Guess the number", "gn")
                        .addOption("Trivia", "trivia")
                        .addOption("Chess", "chess")
                        .build();

                event.getMessage().delete().queue();
                event.getChannel().sendMessageEmbeds(helpCrap(5, event).build()).setActionRows(ActionRow.of(menu), ActionRow.of(Button.of(ButtonStyle.DANGER, "0000:backgames", "Back"))).queue();
                return;
            case "backgames":
                final long guildID = event.getGuild().getIdLong();

                String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (ids) -> Config.get("prefix"));

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Groups");
                embedBuilder.setColor(Color.cyan);
                embedBuilder.addField(com.general_hello.commands.commands.Emoji.Emoji.USER + " | User (10)", "Shows basic to complex commands that the user can do with the bot", false);
                embedBuilder.addField(com.general_hello.commands.commands.Emoji.Emoji.DISCORD_BOT + " | Bot (3)", "Shows the commands you can do with the bot", false);
                embedBuilder.addField(com.general_hello.commands.commands.Emoji.Emoji.INFO + " | Info (3)", "Shows basic to complex information about users, servers, or mods", false);
                embedBuilder.addField(com.general_hello.commands.commands.Emoji.Emoji.MOD + " | Moderation (4)","Basic to advanced moderation tools used by staff to control or monitor the server.", false);
                embedBuilder.addField(com.general_hello.commands.commands.Emoji.Emoji.MUSIC + " | Music (7)","Basic to advanced music commands. (Do ignt musichelp to see its commands)", false);
                embedBuilder.addField(com.general_hello.commands.commands.Emoji.Emoji.GAME + " | Games (4)","Fun games.", false);
                embedBuilder.addField("\uD83D\uDCDA | RPG (?)","Fun RPG commands for igniters", false);


                embedBuilder.setFooter("Type " + prefix + " help [group name] to see their commands");

                boolean disableOrEnable = !event.getMember().hasPermission(Permission.MANAGE_SERVER) && !event.getMember().getRoles().contains(event.getGuild().getRoleById(888627140046749697L));

                event.getMessage().delete().queue();
                event.getChannel().sendMessageEmbeds(embedBuilder.build()).setActionRows(
                        ActionRow.of(
                                Button.secondary(event.getMember().getUser().getId() + ":user", "User").withEmoji(Emoji.fromEmote("user", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":bot", "Bot").withEmoji(Emoji.fromEmote("discord_bot", Long.parseLong("862895574960701440"), false)),
                                Button.secondary(event.getMember().getUser().getId() + ":info", "Info").withEmoji(Emoji.fromEmote("info", Long.parseLong("870871190217060393"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":game", "Games").withEmoji(Emoji.fromEmote("games", Long.parseLong("891146612016025630"), true))),
                        ActionRow.of(
                                Button.secondary(event.getMember().getUser().getId() + ":rpg", "RPG").withEmoji(Emoji.fromUnicode("U+1F4DA")).asDisabled(),
                                Button.secondary(event.getMember().getUser().getId() + ":mod", "Moderation").withDisabled(disableOrEnable).withEmoji(Emoji.fromEmote("mod", Long.parseLong("862898484041482270"), true)),
                                Button.danger(event.getMember().getUser().getId() + ":end", "Cancel").withEmoji(Emoji.fromEmote("cancel", Long.parseLong("863204248657461298"), true)))
                ).queue();
                return;
            case "accept":
                String arrow = "<a:arrow_1:862525611465113640>";

                event.getMessage().delete().queue();
                EmbedBuilder em = new EmbedBuilder().setTitle("Stored data").setFooter("Press the Accept button if you accept the data that will be stored!\n");
                em.setDescription("The bot stores the following data:\n" +
                        arrow + " Reads all sent messages in the server the bot is in.\n" +
                        arrow + " Reads all the messages you sent to the bot.\n" +
                        arrow + " Reads your ignite coins.\n" +
                        arrow + " Reads your user name, profile picture, nitro status, and user id.\n" +
                        arrow + " Reads all the permissions you have on that server.");
                event.getChannel().sendMessageEmbeds(em.build()).setActionRow(
                        Button.primary("0000:yes", "Accept").withEmoji(Emoji.fromEmote("verify", Long.parseLong("863204252188672000"), true))
                ).queue();
                break;
            case "balance":
                Integer balance = BalanceCommand.dataInTheSky.get(event.getUser().getIdLong());
                event.reply("Your balance is **" + balance + "**").setEphemeral(true).queue();
                BalanceCommand.dataInTheSky.remove(event.getUser().getIdLong());
                event.getMessage().editMessageEmbeds(event.getMessage().getEmbeds().get(0)).setActionRow(event.getButton().asDisabled()).queue();
                break;
            case "yes":
                event.getMessage().delete().queue();
                event.getChannel().sendMessage("<a:thanks:863989523461177394> Thank you for accepting the rules and data that will be stored.").queue();
                event.getChannel().sendMessage("<a:question:863989523368247346> For your Ignite Coins balance, may we ask for your first and last, real name? For example, **Nathan Tan** or **John Sy**").queue();
                Data.progress.put(event.getUser(), 1);
                break;
            case "acceptChess":
                event.getMessage().getActionRows().get(0).updateComponent(event.getUser().getId() + ":acceptChess", event.getButton().asDisabled());
                event.getChannel().sendMessage(event.getMember().getAsMention() + " accepted the Chess challenge!\nLoading the game " + com.general_hello.commands.commands.Emoji.Emoji.LOADING).queue();
                MainChessCode.loadBoard(event);
                Board board = new Board();
                ChessStoring.userToBoard.put(event.getUser(), board);
                ChessStoring.userToBoard.put(ChessStoring.userToUser.get(event.getUser()), board);
                event.getInteraction().editButton(event.getButton().asDisabled()).queue();
                break;
            case "hit":
                AtomicBoolean me = new AtomicBoolean(true);

                BlackjackGame bjg = GameHandler.getBlackJackGame(event.getUser().getIdLong());
                if (bjg != null) {
                    bjg.hit();
                    event.getChannel().retrieveMessageById(bjg.getMessageId()).queue(m -> {
                        EmbedBuilder eb = bjg.buildEmbed(event.getUser().getName(), event.getGuild());
                        if (bjg.hasEnded()) {
                            int d = bjg.getWonCreds();
                            GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                            LevelPointManager.feed(event.getUser(), 20);
                            DatabaseManager.INSTANCE.setCredits(event.getUser().getIdLong(), d);
                            eb.addField("Credits", "You now have " + d + " more credits", false);
                            GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                            me.set(false);
                        }
                        if (me.get()) {
                            m.editMessageEmbeds(eb.build()).queue();
                        } else {
                            m.editMessageEmbeds(eb.build()).setActionRows(ActionRow.of(Button.of(ButtonStyle.SUCCESS, "DONE", "Game ended!").asDisabled())).queue();
                        }
                    });
                }

                event.deferEdit().queue();
                break;
            case "stand":
                me = new AtomicBoolean(true);

                bjg = GameHandler.getBlackJackGame(event.getUser().getIdLong());
                if (bjg != null) {
                    bjg.stand();
                    event.getChannel().retrieveMessageById(bjg.getMessageId()).queue(m -> {
                        EmbedBuilder eb = bjg.buildEmbed(event.getUser().getName(), event.getGuild());
                        if (bjg.hasEnded()) {
                            int d = bjg.getWonCreds();
                            GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                            LevelPointManager.feed(event.getUser(), 20);
                            DatabaseManager.INSTANCE.setCredits(event.getUser().getIdLong(), d);
                            eb.addField("Credits", "You now have " + d + " more credits", false);
                            GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                            me.set(false);
                        }
                        if (me.get()) {
                            m.editMessageEmbeds(eb.build()).queue();
                        } else {
                            m.editMessageEmbeds(eb.build()).setActionRows(ActionRow.of(Button.of(ButtonStyle.SUCCESS, "DONE", "Game ended!").asDisabled())).queue();
                        }
                    });
                }

                event.deferEdit().queue();
                break;
            case "double":
                me = new AtomicBoolean(true);
                bjg = GameHandler.getBlackJackGame(event.getUser().getIdLong());
                if (bjg != null) {
                    if (bjg.canDouble()) {
                        if (DatabaseManager.INSTANCE.getCredits(event.getUser().getIdLong()) - 2 * bjg.getBet() >= 0) {
                            bjg.doubleDown();
                            event.getChannel().retrieveMessageById(bjg.getMessageId()).queue(m -> {
                                EmbedBuilder eb = bjg.buildEmbed(event.getUser().getName(), event.getGuild());
                                if (bjg.hasEnded()) {
                                    int d = bjg.getWonCreds();
                                    GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                                    LevelPointManager.feed(event.getUser(), 20);
                                    DatabaseManager.INSTANCE.setCredits(event.getUser().getIdLong(), d);
                                    eb.addField("Credits", "You now have " + d + " more credits", false);
                                    me.set(false);
                                }
                                if (me.get()) {
                                    m.editMessageEmbeds(eb.build()).queue();
                                } else {
                                    m.editMessageEmbeds(eb.build()).setActionRows(ActionRow.of(Button.of(ButtonStyle.SUCCESS, "DONE", "Game ended!").asDisabled())).queue();
                                }
                            });
                        } else {
                            event.getChannel().sendMessage("You have not enough credits").queue();
                        }
                    } else {
                        event.getChannel().sendMessage("You can't do that").queue();
                    }
                }
                event.deferEdit().queue();
                break;
            case "split":
                me = new AtomicBoolean(true);
                bjg = GameHandler.getBlackJackGame(event.getUser().getIdLong());
                if (bjg != null) {
                    if (bjg.canSplit()) {
                        bjg.split();
                        event.getChannel().retrieveMessageById(bjg.getMessageId()).queue(m -> {
                            EmbedBuilder eb = bjg.buildEmbed(event.getUser().getName(), event.getGuild());
                            if (bjg.hasEnded()) {
                                int d = bjg.getWonCreds();
                                GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                                LevelPointManager.feed(event.getUser(), 20);
                                DatabaseManager.INSTANCE.setCredits(event.getUser().getIdLong(), d);
                                eb.addField("Credits", "You now have " + d + " more credits", false);
                                GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                                me.set(false);
                            }

                            long playerId = event.getUser().getIdLong();

                            if (me.get()) {
                                m.editMessageEmbeds(eb.build()).setActionRows(ActionRow.of(
                                        Button.of(ButtonStyle.PRIMARY, playerId + ":hit", "Hit"),
                                        Button.of(ButtonStyle.PRIMARY, playerId + ":stand", "Stand"),
                                        Button.of(ButtonStyle.PRIMARY, playerId + ":double", "Double")
                                ), ActionRow.of(
                                        Button.of(ButtonStyle.SUCCESS, playerId + ":split", "Used Split").asDisabled(),
                                        Button.of(ButtonStyle.DANGER, playerId + ":endbj", "Surrender")
                                )).queue();
                            } else {
                                m.editMessageEmbeds(eb.build()).setActionRows(ActionRow.of(Button.of(ButtonStyle.SUCCESS, "DONE", "Game ended!").asDisabled())).queue();
                            }
                        });
                    }
                } else {
                    event.getChannel().sendMessage("You have not enough credits").queue();
                }

                event.deferEdit().queue();
                break;
            case "endbj":
                me = new AtomicBoolean(true);

                bjg = GameHandler.getBlackJackGame(event.getUser().getIdLong());
                if (bjg != null) {
                    bjg.stand();
                    if (!bjg.hasEnded()) {
                        bjg.stand();
                    }
                    event.getChannel().retrieveMessageById(bjg.getMessageId()).queue(m -> {
                        EmbedBuilder eb = bjg.buildEmbed(event.getUser().getName(), event.getGuild());
                        if (bjg.hasEnded()) {
                            int d = bjg.getWonCreds();
                            GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                            LevelPointManager.feed(event.getUser(), 10);
                            DatabaseManager.INSTANCE.setCredits(event.getUser().getIdLong(), d);
                            eb.addField("Credits", "You now have " + d + " more credits", false);
                            GameHandler.removeBlackJackGame(event.getUser().getIdLong());
                            me.set(false);
                        }
                        if (me.get()) {
                            m.editMessageEmbeds(eb.build()).queue();
                        } else {
                            m.editMessageEmbeds(eb.build()).setActionRows(ActionRow.of(Button.of(ButtonStyle.SUCCESS, "DONE", "Game ended!").asDisabled())).queue();
                        }
                    });
                }

                event.deferEdit().queue();
                break;
        }
        }

    public EmbedBuilder helpCrap (int number, ButtonClickEvent ctx) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, (id) -> Config.get("prefix"));

        switch (number) {
            case 1:
                embedBuilder.setTitle("Information Commands");
                embedBuilder.setColor(Color.YELLOW);
                embedBuilder.addField("1.) Profile Command","`" + prefix + " profile`", false);
                embedBuilder.addField("2.) Server Information Command","`" + prefix + " serverinfo`", false);

                embedBuilder.setFooter("\nType " + prefix + " help [command name] to see what they do");
                break;
            case 2:
                embedBuilder.setTitle("About the Bot Commands");
                embedBuilder.setColor(Color.blue);
                embedBuilder.addField("1.) Uptime Command", "`" + prefix + " uptime`", false);
                embedBuilder.addField("2.) Ping Command", "`" + prefix + " ping`", false);
                embedBuilder.addField("3.) About Command", "`" + prefix + " about`", false);


                embedBuilder.setFooter("\nType " + prefix + " help [command name] to see what they do");
                break;
            case 3:
                embedBuilder.setTitle("Moderation Commands");
                embedBuilder.setColor(Color.red);
                embedBuilder.addField("1.) Set Prefix Command", "`" + prefix + " setprefix`", false);
                embedBuilder.addField("2.) Lockdown Command", "`" + prefix + " lockdown`", false);
                embedBuilder.addField("3.) Un-lockdown Command", "`" + prefix + " unlockdown`", false);
                embedBuilder.addField("4.) Update Ignite Coins Balance Command", "`" + prefix + " updatecoins`", false);

                embedBuilder.setFooter("\nType " + prefix + " help [command name] to see what they do");
                break;
            case 4:
                embedBuilder.setTitle("User Commands");
                embedBuilder.setColor(Color.CYAN);
                embedBuilder.addField("1.) Calculator Command", "`" + prefix + " calculator`", false);
                embedBuilder.addField("2.) Register Command", "`" + prefix + " register`", false);
                embedBuilder.addField("3.) View rank Command", "`" + prefix + " rank`", false);
                embedBuilder.addField("4.) Show a Joke Command", "`" + prefix + " joke`", false);
                embedBuilder.addField("5.) Show a Meme Command", "`" + prefix + " meme`", false);
                embedBuilder.addField("6.) Animal Fact Command", "`" + prefix + " fact`", false);
                embedBuilder.addField("7.) Weather Lookup Command", "`" + prefix + " weather`", false);
                embedBuilder.addField("8.) Lyric Search Command", "`" + prefix + " lyric`", false);
                embedBuilder.addField("9.) Make Facts Command", "`" + prefix + " makefacts`", false);
                embedBuilder.addField("10.) Quote Command", "`" + prefix + " quote`", false);
                embedBuilder.addField("11.) Share code Command (Programming)", "`" + prefix + " sharecode`", false);
                embedBuilder.addField("12.) Akinator Command *NOT WORKING*", "`" + prefix + " aki`", false);
                embedBuilder.addField("13.) Rock Paper Scissors Command", "`" + prefix + " rps`", false);
                embedBuilder.addField("14.) Connect 4 Command *NOT WORKING*", "`" + prefix + " c4`", false);
                embedBuilder.addField("15.) Tic Tac Toe Command *NOT WORKING*", "`" + prefix + " ttt`", false);

                embedBuilder.setFooter("Type " + prefix + " help [command name] to see what they do");
                break;
            case 5:
                embedBuilder.setTitle("Games");
                embedBuilder.setColor(Color.ORANGE);
                embedBuilder.setDescription(com.general_hello.commands.commands.Emoji.Emoji.UNO + " **Uno** - Players take turns matching a card in their hand with the current card shown on top of the deck either by color or number. Special action cards deliver game-changing moments as they help you defeat your opponents.\n\n" +
                        com.general_hello.commands.commands.Emoji.Emoji.BLACKJACK + " **Blackjack** - Blackjack is a card game. The object of blackjack is to be dealt cards with a value of up to but not over 21 and to beat the dealer's hand. ... You place bets with the dealer on the likelihood that your hand will come equal or closer to 21 than will the dealer's.\n\n" +
                        com.general_hello.commands.commands.Emoji.Emoji.NUMBER + " **Guess the Number** - Your goal is to get the same number that the bot selected from 1-100 the bot will inform you if the number is *higher* or *lower*\n\n" +
                        com.general_hello.commands.commands.Emoji.Emoji.MARK_QUESTION + " **Trivia** - Users will be given a random question and they are to answer it.\n\n" +
                        com.general_hello.commands.commands.Emoji.Emoji.BPAWN + " **Chess** *Soon* - Chess is a game played between two opponents on opposite sides of a board containing 64 squares of alternating colors. Each player has 16 pieces. The goal of the game is to checkmate the other king.");

                embedBuilder.setFooter("Type " + prefix + " help [command name] to see what they do");
                break;
            case 6:
                embedBuilder.setTitle("RPG Commands *Temporary*");
                embedBuilder.setColor(Color.blue);
                embedBuilder.addField("1.) Play Command", "`" + prefix + " play`", false);
                embedBuilder.addField("2.) Pause Command", "`" + prefix + " pause`", false);
                embedBuilder.addField("3.) Queue Command", "`" + prefix + " queue`", false);
                embedBuilder.addField("4.) Repeat Command", "`" + prefix + " repeat`", false);
                embedBuilder.addField("5.) Resume Command", "`" + prefix + " resume`", false);
                embedBuilder.addField("6.) Skip Command", "`" + prefix + " skip`", false);
                embedBuilder.addField("7.) Volume Command", "`" + prefix + " volume`", false);
                embedBuilder.addField("8.) Lyrics Command", "`" + prefix + " lyrics`", false);
                embedBuilder.addField("9.) Stop Command", "`" + prefix + " stop`", false);
                embedBuilder.addField("10.) Leave Command", "`" + prefix + " leave`", false);

                embedBuilder.setFooter("Type " + prefix + " help [command name] to see what they do");
        }
        return embedBuilder;
    }
}
