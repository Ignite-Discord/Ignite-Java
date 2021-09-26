package com.general_hello.commands.commands.Uno;

import com.general_hello.commands.commands.CommandContext;
import com.general_hello.commands.commands.CommandType;
import com.general_hello.commands.commands.GroupOfGames.Blackjack.GameHandler;
import com.general_hello.commands.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ChallengeCommand implements ICommand {

    private static final String PATH = "./Uno"; //"src/main/resources/uno_cards"; //
    private static final int height = 362;
    private static final int width = 242;

    @Override
    public void handle(CommandContext e) throws InterruptedException {
        Guild guild = e.getGuild();
        UnoGame unoGame = GameHandler.getUnoGame(guild.getIdLong());
        if (unoGame != null && unoGame.getHands().stream().map(UnoHand::getChannelId).collect(Collectors.toList()).contains(e.getChannel().getIdLong())) {
            ArrayList<UnoHand> hands = unoGame.getHands();
            if (unoGame.isFinished()) {
                e.getChannel().sendMessage("The game has already ended").queue();
                return;
            }
            System.out.printf("Current turn: %d\n", unoGame.getTurn());
            UnoHand skippedHand = hands.get(unoGame.calculateNextTurn(-1));
            System.out.printf("Skipped: %s, turn: %d\n", skippedHand.getPlayerName(), unoGame.calculateNextTurn(-1));
            if (unoGame.getTopCard().getValue() == UnoCard.Value.PLUSFOUR && skippedHand.getPlayerId() == e.getAuthor().getIdLong()) {
                int playedturn = unoGame.calculateNextTurn(-2);
                UnoHand playedHand = hands.get(playedturn);
                System.out.printf("Played: %s, turn: %d\n", playedHand.getPlayerName(), playedturn);
                EmbedBuilder eb1 = new EmbedBuilder();
                EmbedBuilder eb2 = new EmbedBuilder();
                Color color = guild.getSelfMember().getColor();
                eb1.setColor(color);
                eb2.setColor(color);
                if (unoGame.canPlayDrawFour(playedturn)) {
                    UnoCard card1 = unoGame.getNextCard();
                    UnoCard card2 = unoGame.getNextCard();
                    skippedHand.addCard(card1, false);
                    skippedHand.addCard(card2, false);
                    eb1.setTitle(String.format("You challenged %s, but you were wrong, you drew a %s and a %s", playedHand.getPlayerName(), card1.toString(), card2.toString()));
                    eb2.setTitle(String.format("%s challenged you but was wrong, he drew 2 cards", skippedHand.getPlayerName()));
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 4; i++) {
                        unoGame.getTrekstapel().add(skippedHand.getCards().remove(skippedHand.getCards().size() - 1));
                        UnoCard card = unoGame.getTrekstapel().remove(0);
                        playedHand.addCard(card, false);
                        sb.append(card.toString()).append(", ");
                    }
                    eb1.setTitle(String.format("You were right, %s drew 4 cards", playedHand.getPlayerName()));
                    eb2.setTitle(String.format("%s challenged you and was right, you drew 4 cards: %s", skippedHand.getPlayerName(), sb.substring(0, sb.length() - 3)));
                }
                TextChannel skippedchannel = guild.getTextChannelById(skippedHand.getChannelId());
                TextChannel playedChannel = guild.getTextChannelById(playedHand.getChannelId());
                skippedchannel.sendMessageEmbeds(eb1.build()).queue();
                playedChannel.sendMessageEmbeds(eb2.build()).queue();
                EmbedBuilder eb = unoGame.createEmbed(skippedHand.getPlayerId());
                eb.setColor(color);
                skippedchannel.sendFile(getCardsImage(skippedHand.getCards()), "hand.png").embed(eb.build()).queue(newmessage -> skippedHand.setMessageId(newmessage.getIdLong()));
                eb = unoGame.createEmbed(playedHand.getPlayerId());
                playedChannel.sendFile(getCardsImage(playedHand.getCards()), "hand.png").embed(eb.build()).queue(newmessage -> playedHand.setMessageId(newmessage.getIdLong()));
            } else {
                e.getChannel().sendMessage("You can only challenge draw four cards when you need to draw").queue();
            }
        }
    }

    public static InputStream getCardsImage(ArrayList<UnoCard> cards){
        try {
            BufferedImage img = new BufferedImage(cards.size() * width, height, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < cards.size(); i++){
                BufferedImage card = ImageIO.read(getCardImage(cards.get(i), true));
                img.createGraphics().drawImage(card, i * width, 0,null);
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, "png", os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (Exception err){
            err.printStackTrace();
            return null;
        }
    }

    public static File getCardImage(UnoCard card, boolean ignorecolor){
        String url = card.getColor().getToken() + card.getValue().getToken();
        if (ignorecolor && card.getValue() == UnoCard.Value.WILD || card.getValue() == UnoCard.Value.PLUSFOUR){
            url = card.getValue().getToken();
        }
        url += ".png";
        return new File(PATH, url);
    }

    @Override
    public String getName() {
        return "challenge";
    }

    @Override
    public String getHelp(String prefix) {
        return "Challenges the user who gave you a `+4 card`";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.SPECIAL;
    }
}
