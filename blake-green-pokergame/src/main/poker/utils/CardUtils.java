package src.main.poker.utils;

import java.util.*;

public class CardUtils {

    public static List<String> cards;
    public static Map<String, String> cardImageMap = generateCardImgMap();

    public static List<String> generateCards() {
        String[] cards = new String[] { "Clubs-2", "Diamonds-2", "Hearts-2", "Spades-2",
                                        "Clubs-3", "Diamonds-3", "Hearts-3", "Spades-3",
                                        "Clubs-4", "Diamonds-4", "Hearts-4", "Spades-4",
                                        "Clubs-5", "Diamonds-5", "Hearts-5", "Spades-5",
                                        "Clubs-6", "Diamonds-6", "Hearts-6", "Spades-6",
                                        "Clubs-7", "Diamonds-7", "Hearts-7", "Spades-7",
                                        "Clubs-8", "Diamonds-8", "Hearts-8", "Spades-8",
                                        "Clubs-9", "Diamonds-9", "Hearts-9", "Spades-9",
                                        "Clubs-10", "Diamonds-10", "Hearts-10", "Spades-10",
                                        "Clubs-J", "Diamonds-J", "Hearts-J", "Spades-J",
                                        "Clubs-Q", "Diamonds-Q", "Hearts-Q", "Spades-Q",
                                        "Clubs-K", "Diamonds-K", "Hearts-K", "Spades-K",
                                        "Clubs-A", "Diamonds-A", "Hearts-A", "Spades-A" };

        return new ArrayList<>(Arrays.asList(cards));
    }

    public static Map<String, String> generateCardImgMap() {
        Map<String, String> cards = new HashMap<>();
        cards.put("Clubs-2", "2C.png");
        cards.put("Diamonds-2", "2D.png");
        cards.put("Hearts-2", "2H.png");
        cards.put("Spades-2", "2S.png");
        cards.put("Clubs-3", "3C.png");
        cards.put("Diamonds-3", "3D.png");
        cards.put("Hearts-3", "3H.png");
        cards.put("Spades-3", "3S.png");
        cards.put("Clubs-4", "4C.png");
        cards.put("Diamonds-4", "4D.png");
        cards.put("Hearts-4", "4H.png");
        cards.put("Spades-4", "4S.png");
        cards.put("Clubs-5", "5C.png");
        cards.put("Diamonds-5", "5D.png");
        cards.put("Hearts-5", "5H.png");
        cards.put("Spades-5", "5S.png");
        cards.put("Clubs-6", "6C.png");
        cards.put("Diamonds-6", "6D.png");
        cards.put("Hearts-6", "6H.png");
        cards.put("Spades-6", "6S.png");
        cards.put("Clubs-7", "7C.png");
        cards.put("Diamonds-7", "7D.png");
        cards.put("Hearts-7", "7H.png");
        cards.put("Spades-7", "7S.png");
        cards.put("Clubs-8", "8C.png");
        cards.put("Diamonds-8", "8D.png");
        cards.put("Hearts-8", "8H.png");
        cards.put("Spades-8", "8S.png");
        cards.put("Clubs-9", "9C.png");
        cards.put("Diamonds-9", "9D.png");
        cards.put("Hearts-9", "9H.png");
        cards.put("Spades-9", "9S.png");
        cards.put("Clubs-10", "10C.png");
        cards.put("Diamonds-10", "10D.png");
        cards.put("Hearts-10", "10H.png");
        cards.put("Spades-10", "10S.png");
        cards.put("Clubs-J", "JC.png");
        cards.put("Diamonds-J", "JD.png");
        cards.put("Hearts-J", "JH.png");
        cards.put("Spades-J", "JS.png");
        cards.put("Clubs-Q", "QC.png");
        cards.put("Diamonds-Q", "QD.png");
        cards.put("Hearts-Q", "QH.png");
        cards.put("Spades-Q", "QS.png");
        cards.put("Clubs-K", "KC.png");
        cards.put("Diamonds-K", "KD.png");
        cards.put("Hearts-K", "KH.png");
        cards.put("Spades-K", "KS.png");
        cards.put("Clubs-A", "AC.png");
        cards.put("Diamonds-A", "AD.png");
        cards.put("Hearts-A", "AH.png");
        cards.put("Spades-A", "AS.png");

        return cards;
    }

    public static String generateRandomCard() {
        Random r = new Random();
        int randomIndex = r.nextInt(cards.size());
        String card = cards.get(randomIndex);
        cards.remove(card);
        return card;
    }
}
