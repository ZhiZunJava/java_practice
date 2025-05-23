package task04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Main {
    public static void main(String[] args) {
        List<Card> deck = createDeck();
        Collections.shuffle(deck);

        List<Card> player1 = new ArrayList<>();
        List<Card> player2 = new ArrayList<>();
        List<Card> player3 = new ArrayList<>();

        for (int i = 0; i < deck.size() - 3; i++) {
            switch (i % 3) {
                case 0:
                    player1.add(deck.get(i));
                    break;
                case 1:
                    player2.add(deck.get(i));
                    break;
                case 2:
                    player3.add(deck.get(i));
                    break;
            }
        }

        List<Card> bottomCards = deck.subList(deck.size() - 3, deck.size());

        System.out.println("--------棋牌游戏洗牌发牌--------");
        System.out.println("玩家1: " + player1);
        System.out.println("玩家2: " + player2);
        System.out.println("玩家3: " + player3);
        System.out.println("底牌: " + bottomCards);
    }

    private static List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        String[] suits = {"♠", "♥", "♦", "♣"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(suit, rank));
            }
        }

        deck.add(new Card("", "小♣"));
        deck.add(new Card("", "大♠"));

        return deck;
    }
}