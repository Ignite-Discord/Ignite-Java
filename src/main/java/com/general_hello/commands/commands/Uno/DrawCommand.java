package com.general_hello.commands.commands.Uno;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.GroupOfGames.Blackjack.GameHandler;
import com.general_hello.commands.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DrawCommand implements ICommand {
    private GameHandler gameHandler;

    public DrawCommand(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    @Override
    public void handle(CommandContext e) throws InterruptedException {
        Guild guild = e.getGuild();
        UnoGame unoGame = gameHandler.getUnoGame(guild.getIdLong());
        if (unoGame != null && unoGame.getHands().stream().map(UnoHand::getChannelId).collect(Collectors.toList()).contains(e.getChannel().getIdLong())) {
            int turn = unoGame.getTurn();
            ArrayList<UnoHand> hands = unoGame.getHands();
            if (unoGame.isFinished()) {
                e.getChannel().sendMessage("The game has already ended").queue();
                return;
            }
            if (turn != -1 && hands.get(turn).getPlayerId() == e.getMember().getIdLong()) {
                UnoCard newCard = unoGame.drawCard();
                EmbedBuilder deb = new EmbedBuilder();
                Color color = guild.getSelfMember().getColor();
                deb.setColor(color);
                boolean played = false;
                if (unoGame.canPlay(newCard)) {
                    if (newCard.getValue() != UnoCard.Value.PLUSFOUR && newCard.getValue() != UnoCard.Value.WILD) {
                        unoGame.playCard(newCard);
                        played = true;
                        deb.setTitle(String.format("You drew and played a %s", newCard.toString()));
                    } else {
                        UnoHand hand = unoGame.getPlayerHand(e.getMember().getIdLong());
                        TextChannel channel = guild.getTextChannelById(hand.getChannelId());
                        deb.setTitle(String.format("You drew a %s", newCard.toString()));
                        channel.sendMessageEmbeds(deb.build()).queue();
                        return;
                    }
                } else {
                    hands.get(turn).endTurn(null);
                    unoGame.nextTurn(false);
                    deb.setTitle(String.format("You drew a %s", newCard.toString()));
                }
                int newturn = unoGame.getTurn();
                for (int i = 0; i < hands.size(); i++) {
                    UnoHand hand = hands.get(i);
                    TextChannel channel = guild.getTextChannelById(hand.getChannelId());
                    long player = hand.getPlayerId();
                    if (player != e.getMember().getIdLong()) {
                        if (played && isBetween(unoGame, turn, i) && (newCard.getValue() == UnoCard.Value.PLUSTWO)) {
                            EmbedBuilder eb = unoGame.createEmbed(player);
                            eb.setColor(guild.getSelfMember().getColor());
                            EmbedBuilder eb2 = new EmbedBuilder();
                            eb2.setColor(color);
                            eb2.setTitle(String.format("You had to draw 2 cards because %s played a %s", hands.get(turn).getPlayerName(), newCard.toString()));
                            channel.sendMessageEmbeds(eb2.build()).queue();
                            channel.sendFile(ImageHandler.getCardsImage(hand.getCards()), "hand.png").setEmbeds(eb.build()).queueAfter(1, TimeUnit.SECONDS, newmessage -> hand.setMessageId(newmessage.getIdLong()));
                        } else {
                            channel.retrieveMessageById(hand.getMessageId()).queue(message -> {
                                EmbedBuilder eb = unoGame.createEmbed(player);
                                eb.setColor(color);
                                message.editMessageEmbeds(eb.build()).queue();
                                if (hands.get(newturn).getPlayerId() == player) {
                                    EmbedBuilder eb2 = new EmbedBuilder();
                                    eb2.setTitle("It's your turn!");
                                    eb2.setColor(color);
                                    channel.sendMessageEmbeds(eb2.build()).queue();
                                }
                            });
                        }

                    } else {
                        channel.sendMessageEmbeds(deb.build()).queue();
                        EmbedBuilder eb = unoGame.createEmbed(player);
                        eb.setColor(guild.getSelfMember().getColor());
                        channel.sendFile(ImageHandler.getCardsImage(hand.getCards()), "hand.png").setEmbeds(eb.build()).queue(newmessage -> hand.setMessageId(newmessage.getIdLong()));

                    }
                }


            } else {
                e.getChannel().sendMessage("It's not your turn yet").queue();
            }
        }
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
    public String getName() {
        return "draw";
    }

    @Override
    public String getHelp(String prefix) {
        return "Draws a card\n" +
                "Usage: `" + prefix + getName() + " [card name]`";
    }
    @Override
    public CommandType getCategory() {
        return CommandType.SPECIAL;
    }
}
