package com.general_hello.commands.commands.Uno;


import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.GroupOfGames.Blackjack.GameHandler;
import com.general_hello.commands.commands.ICommand;
import com.general_hello.commands.commands.PrefixStoring;
import com.general_hello.commands.commands.Utils.MoneyData;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PlayCardCommand implements ICommand {
    private GameHandler gameHandler;

    public PlayCardCommand(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    @Override
    public void handle(CommandContext e) throws InterruptedException {
        final long guildID = e.getGuild().getIdLong();
        String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, DatabaseManager.INSTANCE::getPrefix);

        UnoGame unoGame = GameHandler.getUnoGame(e.getGuild().getIdLong());
        if (unoGame != null && unoGame.getHands().stream().map(UnoHand::getChannelId).collect(Collectors.toList()).contains(e.getChannel().getIdLong())) {
            int turn = unoGame.getTurn();
            ArrayList<UnoHand> hands = unoGame.getHands();
            if (unoGame.isFinished()) {
                e.getChannel().sendMessage("The game has already ended").queue();
                return;
            }
            if (turn != -1 && hands.get(turn).getPlayerId() == e.getMember().getIdLong()) {
                if (e.getArgs().isEmpty()) {
                    e.getChannel().sendMessage(getHelp(prefix)).queue();
                    return;
                }
                UnoCard card = UnoCard.fromString(e.getArgs().get(0));
                Guild guild = e.getGuild();
                if (card != null && unoGame.canPlay(card)) {
                    unoGame.playCard(card);
                    Color color = guild.getSelfMember().getColor();
                    int newturn = unoGame.getTurn();
                    for (int i = 0; i < hands.size(); i++) {
                        UnoHand hand = hands.get(i);
                        long player = hand.getPlayerId();
                        TextChannel channel = guild.getTextChannelById(hand.getChannelId());
                        if (player != e.getMember().getIdLong()) {
                            int finalI = i;
                            channel.retrieveMessageById(hand.getMessageId()).queue(message -> {
                                EmbedBuilder eb = unoGame.createEmbed(player);
                                eb.setColor(color);
                                if (unoGame.isFinished()) {
                                    message.editMessageEmbeds(eb.build()).queue();
                                    EmbedBuilder eb2 = new EmbedBuilder();
                                    int size = hands.size() - 1;
                                    int bet = unoGame.getBet();
                                    int credits = bet == 0 ? 200 * size : bet * size;
                                    eb2.setTitle(String.format("%s played a **%s** and won **%d** credits by winning the game", e.getMember().getEffectiveName(), card.toString(), credits));
                                    if (bet != 0) {
                                        eb2.setDescription(String.format("You lost **%d** credits", bet));
                                    }

                                    final Double monet = MoneyData.money.get(e.getAuthor());

                                    if (bet != 0) MoneyData.money.put(e.getAuthor(), (monet - 1)  * credits);
                                    eb2.setColor(color);
                                    channel.sendMessageEmbeds(eb2.build()).queue();
                                    channel.delete().queueAfter(1, TimeUnit.MINUTES);
                                } else if (newturn == finalI) {
                                    message.editMessageEmbeds(eb.build()).queue();
                                    EmbedBuilder eb2 = new EmbedBuilder();
                                    eb2.setTitle("It's your turn!");
                                    eb2.setColor(color);
                                    channel.sendMessageEmbeds(eb2.build()).queue();
                                } else if (isBetween(unoGame, turn, finalI) && (card.getValue() == UnoCard.Value.PLUSFOUR || card.getValue() == UnoCard.Value.PLUSTWO)) {
                                    EmbedBuilder eb2 = new EmbedBuilder();
                                    eb2.setColor(color);
                                    eb2.setTitle(String.format("You had to draw %d cards because %s played a %s", card.getValue() == UnoCard.Value.PLUSTWO ? 2 : 4, hands.get(turn).getPlayerName(), card.toString()));
                                    channel.sendMessageEmbeds(eb2.build()).queue();
                                    channel.sendFile(ImageHandler.getCardsImage(hand.getCards()), "hand.png").embed(eb.build()).queueAfter(1, TimeUnit.SECONDS, newmessage -> hand.setMessageId(newmessage.getIdLong()));
                                } else {
                                    message.editMessage(eb.build()).queue();
                                }
                            });
                        } else {
                            if (!unoGame.isFinished()) {
                                EmbedBuilder eb = unoGame.createEmbed(player);
                                eb.setColor(guild.getSelfMember().getColor());
                                channel.sendFile(ImageHandler.getCardsImage(hand.getCards()), "hand.png").setEmbeds(eb.build()).queue(newmessage -> hand.setMessageId(newmessage.getIdLong()));
                            } else {
                                EmbedBuilder eb2 = new EmbedBuilder();
                                int size = hands.size() - 1;
                                int credits = unoGame.getBet() == 0 ? 200 * size : unoGame.getBet() * size;
                                eb2.setTitle(String.format("You played a **%s** and won, you won **%d** credits", card.toString(), credits));
                                final Double money = MoneyData.money.get(e.getAuthor());
                                MoneyData.money.put(e.getAuthor(), money+credits);
                                channel.sendMessageEmbeds(eb2.build()).queue();
                                guild.getTextChannelById(unoGame.getChannelID()).retrieveMessageById(unoGame.getMessageID()).queue(m -> {
                                    EmbedBuilder eb = new EmbedBuilder(m.getEmbeds().get(0));
                                    eb.setTitle("The game of uno has concluded");
                                    eb.setDescription(String.format("%s won the game and won **%d** credits", hand.getPlayerName(), credits));
                                    m.editMessage(eb.build()).queue();
                                });
                                channel.delete().queueAfter(1, TimeUnit.MINUTES);

                            }
                        }
                    }
                    if (unoGame.isFinished()) {
                        guild.getCategoryById(unoGame.getCategory()).delete().queueAfter(65, TimeUnit.SECONDS);
                        gameHandler.removeUnoGame(guild.getIdLong());
                    }
                } else {
                    e.getChannel().sendMessage("You need to play a valid card that's in your hand").queue();
                }
            } else {
                e.getChannel().sendMessage("It's not your turn yet").queue();
            }

        }
    }

    @Override
    public String getName() {
        return "playcard";
    }

    public static boolean isBetween(UnoGame game, int turn, int between) {
        int one = game.isClockwise() ? 1 : -1;
        int newturn = game.getTurn();
        int x1 = (turn + one) % game.getHands().size();
        if (x1 < 0) x1 += game.getHands().size();
        int x2 = (newturn - one) % game.getHands().size();
        if (x2 < 0) x2 += game.getHands().size();
        return between == x1 && between == x2;
    }

    @Override
    public String getHelp(String prefix) {
        return "Play a card from your hand, pick the color for a wildcard immediately\n" +
                "Usage: `" + prefix + "playcard [color] [value]`";
    }


    @Override
    public CommandType getCategory() {
        return CommandType.SPECIAL;
    }
}
