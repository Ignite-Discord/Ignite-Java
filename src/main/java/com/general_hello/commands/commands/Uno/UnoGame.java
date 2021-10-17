package com.general_hello.commands.commands.Uno;

import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.min;

public class UnoGame {

    private final String PATH = "http://zwervers.wettinck.be/uno/";

    private ArrayList<UnoHand> hands;
    private ArrayList<UnoCard> trekstapel;
    private ArrayList<UnoCard> aflegstapel;
    private int turn;
    private boolean clockwise;
    private boolean finished;
    private Random random = new Random();
    private int bet;
    private long messageID;
    private long channelID;
    private long starter;
    private long category;

    public UnoGame(int bet, long starter, long channelID) {
        trekstapel = new ArrayList<>();
        for (UnoCard.Value value : UnoCard.Value.values()) {
            for (UnoCard.Color color : UnoCard.Color.values()) {
                trekstapel.add(new UnoCard(color, value));
                if (value != UnoCard.Value.ZERO && value != UnoCard.Value.PLUSFOUR && value != UnoCard.Value.WILD) {
                    trekstapel.add(new UnoCard(color, value));
                }
            }
        }
        Collections.shuffle(trekstapel);
        aflegstapel = new ArrayList<>();
        turn = -1;
        this.bet = bet;
        clockwise = true;
        finished = false;
        this.starter = starter;
        this.channelID = channelID;
        hands = new ArrayList<>();
    }

    public void addPlayer(long id, String name) {
        UnoHand hand = new UnoHand(id, name);
        for (int i = 0; i < 7; i++) {
            hand.addCard(trekstapel.remove(0), false);
        }
        hands.add(hand);
    }

    public int start() {
        if (hands.size() >= 2) {
            UnoCard beginCard = trekstapel.remove(0);
            if (beginCard.getValue() == UnoCard.Value.PLUSFOUR || beginCard.getValue() == UnoCard.Value.WILD) {
                trekstapel.add(beginCard);
                Collections.shuffle(trekstapel);
                return start();
            }
            aflegstapel.add(beginCard);
            turn = random.nextInt(/*players*/hands.size());
        }
        return turn;
    }

    public boolean canPlay(UnoCard card) {
        return hands.get(turn).canPlay(card) && getTopCard().canBePlayed(card);
    }

    public boolean canPlayDrawFour(int turn){
        UnoCard topcard = aflegstapel.get(aflegstapel.size() - 2);
        System.out.printf("\nTop card: %s\n", topcard.toString());
        for (UnoCard card : hands.get(turn).getCards()){
            System.out.printf("Hand card: %s, ", card.toString());
            if (card.getColor() == topcard.getColor() && card.getValue() != UnoCard.Value.PLUSFOUR && card.getValue() != UnoCard.Value.WILD){
                return false;
            }
        }
        return true;
    }

    public boolean playCard(UnoCard card) {
        if (canPlay(card) && !finished) {
            UnoHand hand = hands.get(turn);
            hand.endTurn(card);
            aflegstapel.add(card);
            if (hand.getCards().size() == 0) {
                finished = true;
                return true;
            }
            switch (card.getValue()) {
                case REVERSE:
                    clockwise = !clockwise;
                    nextTurn(hands.size() == 2);
                    break;
                case SKIP:
                    nextTurn(true);
                    break;
                case PLUSTWO:
                    nextTurn(false);
                    hand = hands.get(turn);
                    if (trekstapel.size() == 0) reshuffle();
                    hand.addCard(trekstapel.remove(0), false);
                    if (trekstapel.size() == 0) reshuffle();
                    hand.addCard(trekstapel.remove(0), false);
                    nextTurn(false);
                    break;
                case PLUSFOUR:
                    nextTurn(false);
                    hand = hands.get(turn);
                    for (int i = 0; i < 4; i++) {
                        if (trekstapel.size() == 0) reshuffle();
                        hand.addCard(trekstapel.remove(0), false);
                    }
                    nextTurn(false);
                    break;
                default:
                    nextTurn(false);

            }
            return true;
        }
        return false;
    }
    public UnoCard getNextCard(){
        if (trekstapel.size() == 0) reshuffle();
        return trekstapel.remove(0);
    }

    public UnoCard drawCard() {
        UnoCard card = getNextCard();
        hands.get(turn).addCard(card, true);
        return card;
    }

    public void nextTurn(boolean extra) {
        int amount = extra ? 2 : 1;
        turn = calculateNextTurn(amount);
    }
    public int calculateNextTurn(int step){
        int temp =  (turn + (clockwise ? step : -step)) % hands.size();
        if (temp < 0) temp += hands.size();
        return temp;
    }

    public void reshuffle() {
        UnoCard topcard = aflegstapel.remove(aflegstapel.size() - 1);
        trekstapel.addAll(aflegstapel);
        aflegstapel.clear();
        aflegstapel.add(topcard);
        Collections.shuffle(trekstapel);
    }

    public long getStarter() {
        return starter;
    }

    public long getCategory() {
        return category;
    }

    public long getMessageID() {
        return messageID;
    }

    public boolean isFinished() {
        return finished;
    }

    public UnoCard getTopCard() {
        return aflegstapel.get(aflegstapel.size() - 1);
    }

    public int getTurn() {
        return turn;
    }

    public int getBet() {
        return bet;
    }

    public boolean isClockwise() {
        return clockwise;
    }

    public ArrayList<UnoHand> getHands() {
        return hands;
    }

    public ArrayList<UnoCard> getTrekstapel() {
        return trekstapel;
    }

    public void setCategory(long category) {
        this.category = category;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public void setChannel(long uuid, long channelId, long messageID) {
        //channels.put(uuid, Pair.of(channelId, messageID));
        for (UnoHand hand : hands) {
            if (hand.getPlayerId() == uuid) {
                hand.setChannelId(channelId);
                hand.setMessageId(messageID);
            }
        }
    }

    public UnoHand getPlayerHand(long id){
        for (UnoHand hand : hands) {
            if (hand.getPlayerId() == id) return hand;
        }
        return null;
    }


    public EmbedBuilder createEmbed(long player) {
        EmbedBuilder eb = new EmbedBuilder();
        UnoHand hand = hands.get(turn);
        if (player == hand.getPlayerId()) {
            eb.setTitle("It's your turn ");
        } else {
            eb.setTitle(String.format("It's %s's turn", hand.getPlayerName()));
        }
        StringBuilder sb = new StringBuilder();
        for (UnoCard c : getPlayerHand(player).getCards()) {
            if (c.getValue() == UnoCard.Value.WILD || c.getValue() == UnoCard.Value.PLUSFOUR){
                sb.append(c.getValue().getName()).append(", ");
            } else {
                sb.append(c.toString()).append(", ");
            }

        }
        sb.delete(sb.length() - 2, sb.length());
        eb.addField("Current Card", getTopCard().toString(), false);
        eb.addField("Your cards", sb.toString(), false);

        StringBuilder names = new StringBuilder();
        StringBuilder cards = new StringBuilder();
        names.append("Order: ").append(clockwise ? ":arrow_forward:\n" : ":arrow_backward:\n");
        for (int i = 0; i < hands.size(); i++) {
            hand = hands.get(i);
            String name = hand.getPlayerName();
            names.append(name.substring(0, min(name.length(), 5)));
            names.append(name.length() > 5 ? "." : "");
            cards.append("   ").append(hand.getCards().size()).append("   ");
            if (hand.getPlayerId() == player) {
                names.append("(You)");
                cards.append("    ");
            } else if (i == turn) {
                names.append("(Now)");
                cards.append("    ");
            }
            names.append(" ");
        }
        names.append("\n").append(cards);
        eb.addField("Other players' cards", names.toString(), false);
        eb.setImage("attachment://hand.png");
        eb.setThumbnail(String.format("%s%s%s.png", PATH, getTopCard().getColor().getToken(), getTopCard().getValue().getToken()));
        return eb;
    }

    public long getChannelID() {
        return channelID;
    }
}