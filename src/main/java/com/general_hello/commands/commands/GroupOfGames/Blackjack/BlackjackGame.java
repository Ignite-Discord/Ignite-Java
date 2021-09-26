package com.general_hello.commands.commands.GroupOfGames.Blackjack;

import com.general_hello.commands.Database.DatabaseManager;
import com.general_hello.commands.commands.PrefixStoring;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class BlackjackGame {
    private Long messageId;
    private ArrayList<Card> deck;
    private BlackJackHand playerHand;
    private BlackJackHand secondPlayerHand;
    private BlackJackHand dealerHand;
    private boolean hasEnded;
    private EndState endstate;
    private EndState secondEndstate;
    private int bet;
    private int secondbet;
    private boolean firsthand;
    private boolean hasSplit;


    public BlackjackGame(int bet){
        hasEnded = false;
        firsthand = true;
        hasSplit = false;
        deck = new ArrayList<>();
        this.bet = bet;
        secondbet = 0;
        playerHand = new BlackJackHand();
        dealerHand = new BlackJackHand();
        secondPlayerHand = new BlackJackHand();
        for (Card.Face f : Card.Face.values()){
            for (Card.Value v : Card.Value.values()){
                deck.add(new Card(f, v));
            }
        }
        Collections.shuffle(deck);
        dealerHand.addCard(deck.remove(0));
        playerHand.addCard(deck.remove(0));
        dealerHand.addCard(deck.remove(0));
        playerHand.addCard(deck.remove(0));
        if (playerHand.getValue() == 21 && dealerHand.getValue() == 21){
            hasEnded = true;
            endstate = EndState.PUSH;
        } else if (dealerHand.getValue() == 21){
            hasEnded = true;
            endstate = EndState.LOST;
        } else if (playerHand.getValue() == 21){
            hasEnded = true;
            endstate = EndState.BLACKJACK;
        }
    }

    public enum EndState{
        WON("You Won", 1),
        LOST("You Lost", -1),
        BUST("You Bust", -1),
        DEALER_BUST("The Dealer Bust", 1),
        PUSH("It's a push", 0),
        BLACKJACK("You have blackjack", 1.5);

        private String display;
        private double reward;

        EndState(String display, double reward){
            this.display = display;
            this.reward = reward;
        }

        public String getDisplay() {
            return display;
        }

        public double getReward() {
            return reward;
        }
    }

    public EndState getEndState(int dealerv, int playerv){
        EndState state;
        if (playerv > 21){
            state = EndState.BUST;
        } else if (dealerv > 21){
            state = EndState.DEALER_BUST;
        } else if (dealerv > playerv){
            state = EndState.LOST;
        } else if (dealerv < playerv ){
            state = EndState.WON;
        } else {
            state = EndState.PUSH;
        }
        return state;
    }

    public void doDealerMoves(){
        while (dealerHand.getValue() < 17){
            dealerHand.addCard(deck.remove(0));
        }
        int dealv = dealerHand.getValue();
        int playerv = playerHand.getValue();
        endstate = getEndState(dealv, playerv);
        if (hasSplit){
            playerv = secondPlayerHand.getValue();
            secondEndstate = getEndState(dealv, playerv);
        }
    }

    public void hit(){
        BlackJackHand hand = firsthand ? playerHand : secondPlayerHand;
        hand.addCard(deck.remove(0));
        int value = hand.getValue();
        if (value >= 21){
            if (!firsthand || !hasSplit){
                hasEnded = true;
                doDealerMoves();
            }
            if (hasSplit && firsthand){
                firsthand = false;
            }
        }
    }

    public void stand(){
        if (!firsthand || !hasSplit){
            hasEnded = true;
            doDealerMoves();
        }
        if (hasSplit && firsthand){
            firsthand = false;
        }
    }

    public void split(){
        hasSplit = true;
        secondbet = bet;
        secondPlayerHand.addCard(playerHand.removeCard(1));
        playerHand.addCard(deck.remove(0));
        secondPlayerHand.addCard(deck.remove(0));
    }
    public boolean canDouble(){
        return playerHand.getCards().size() == 2;
    }

    public boolean canSplit(){
        return canDouble() && playerHand.getCards().get(0).getValue().getValue() == playerHand.getCards().get(1).getValue().getValue();
    }

    public void doubleDown(){
        if (firsthand){
            bet *= 2;
            playerHand.addCard(deck.remove(0));
        } else {
            secondbet *= 2;
            secondPlayerHand.addCard(deck.remove(0));
        }
        if (!hasSplit || !firsthand){
            hasEnded = true;
            doDealerMoves();
        } else {
            firsthand = false;
        }


    }


    public BlackJackHand getDealerHand() {
        return dealerHand;
    }

    public BlackJackHand getPlayerHand() {
        return playerHand;
    }
    public int getBet() {
        return bet;
    }

    public EndState getEndstate(){
        if (!hasEnded) return null;
        return endstate;
    }

    public boolean hasEnded(){
        return hasEnded;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public int getWonCreds(){
        return ((Double) (bet * endstate.reward + (hasSplit ? secondbet * secondEndstate.reward : 0))).intValue();
    }

    public EmbedBuilder buildEmbed(String user, Guild guild){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(String.format("\u2063Blackjack | %s | Bet : %d         \u2063", user, bet + secondbet));
        eb.addField(String.format("%sPlayer Cards", hasSplit && firsthand ? ":arrow_right: " : ""), String.format("%s\nValue: **%s**" ,playerHand.toString(), playerHand.getValue()), true);
        eb.addField("Dealer Cards", String.format("%s\nValue: **%s**", hasEnded ? dealerHand.toString() : dealerHand.toString().split(" ")[0] + " :question:", hasEnded ? dealerHand.getValue() : ":question:"), true);
        if (hasSplit){
            eb.addField(String.format("%sSecond Hand Cards" , !firsthand ? ":arrow_right: " : ""), String.format("%s\nValue: **%s**", secondPlayerHand.toString(), secondPlayerHand.getValue()), false);
        }
        eb.setColor(Color.BLUE);
        if (hasEnded){
            int credits = getWonCreds();
            eb.addField(String.format("%s%s", endstate.display, hasSplit ? " and " + secondEndstate.display: ""), String.format("You %s %d credits", credits > 0 ? "won" : credits == 0 ? "won/lost" : "lost", credits), hasSplit);
            eb.setColor(credits > 0 ? Color.GREEN : credits == 0 ? Color.BLUE : Color.RED);
        } else {
            final long guildID = guild.getIdLong();
            String prefix = PrefixStoring.PREFIXES.computeIfAbsent(guildID, DatabaseManager.INSTANCE::getPrefix);

            eb.addField("Commands", String.format(prefix + " stand : see dealer cards\n" + prefix + " hit : take another card%s%s", canDouble() ? "\n" + prefix + " double : double bet and take last card" : "", canSplit() && !hasSplit ? "\n" + prefix + " split : split your cards" : ""), false);
        }
        return eb;
    }
}
