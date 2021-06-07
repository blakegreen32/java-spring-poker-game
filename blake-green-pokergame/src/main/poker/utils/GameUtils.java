package src.main.poker.utils;

import org.springframework.web.servlet.ModelAndView;
import src.main.poker.controllers.PokerGameAPIController;
import src.main.poker.models.Card;
import src.main.poker.models.GameData;
import src.main.poker.models.Poker;

public class GameUtils {
    public static GameData gameData = new GameData();

    public void startGame() {
        gameData.setPot(10);
        gameData.setPlayerBalance(995);
        gameData.setComputerBalance(995);
        gameData.setPlayerCards(PokerGameAPIController.restStartEndpoint());
        gameData.setRaiseCounter(0);
        setupCards();
    }

    public void continueGame() {
        gameData.setPot(10);
        gameData.setPlayerBalance(gameData.getPlayerBalance() - 5);
        gameData.setComputerBalance(gameData.getComputerBalance() - 5);
        gameData.setPlayersLastBet(0);
        gameData.setComputersLastBet(0);
        gameData.setPlayersLastMove("");
        gameData.setComputersLastMove("");
        gameData.setPlayerCards(PokerGameAPIController.restStartEndpoint());
        gameData.setRaiseCounter(0);
        setupCards();
    }

    public void setupCards() {
        String computerCard1 = CardUtils.generateRandomCard();
        String computerCard2 = CardUtils.generateRandomCard();
        String flopCard1 = CardUtils.generateRandomCard();
        String flopCard2 = CardUtils.generateRandomCard();
        String flopCard3 = CardUtils.generateRandomCard();
        String playerCardImage1 = CardUtils.cardImageMap.get(gameData.getPlayerCards()[0]);
        String playerCardImage2 = CardUtils.cardImageMap.get(gameData.getPlayerCards()[1]);
        String flopCardImage1 = CardUtils.cardImageMap.get(flopCard1);
        String flopCardImage2 = CardUtils.cardImageMap.get(flopCard2);
        String flopCardImage3 = CardUtils.cardImageMap.get(flopCard3);
        String faceDownCard = "face-down-card.jpg";

        String[] tableCards = new String[5];
        tableCards[0] = flopCard1;
        tableCards[1] = flopCard2;
        tableCards[2] = flopCard3;

        String[] tableCardImages = new String[5];
        tableCardImages[0] = flopCardImage1;
        tableCardImages[1] = flopCardImage2;
        tableCardImages[2] = flopCardImage3;
        tableCardImages[3] = faceDownCard;
        tableCardImages[4] = faceDownCard;

        gameData.setComputerCards(new String[] { computerCard1, computerCard2 });
        gameData.setPlayerCardImages(new String[] { playerCardImage1, playerCardImage2 });
        gameData.setTableCards(tableCards);
        gameData.setTableCardImages(tableCardImages);
    }

    public ModelAndView updateTable(ModelAndView modelAndView, String message, boolean isStart, boolean isError) {
        boolean raiseCounterHit = gameData.getRaiseCounter() == 2;
        boolean sameBet = (gameData.getPlayersLastBet() == gameData.getComputersLastBet());
        boolean playersTurn = gameData.getPlayersTurn();
        boolean fourthStreetEmpty = gameData.getTableCards()[3] == null;
        boolean riverEmpty = gameData.getTableCards()[4] == null;

        if ( playersTurn && fourthStreetEmpty && !isStart && !isError && (sameBet || raiseCounterHit) ) {
            showNewCard(3);
        } else if ( playersTurn && riverEmpty && !isStart && !isError && (sameBet || raiseCounterHit) ) {
            showNewCard(4);
        } else if ( playersTurn && !riverEmpty && !isStart && !isError && (sameBet || raiseCounterHit) ) {
            int playerHandRank = checkHandRank(gameData.getPlayerCards());
            int computerHandRank = checkHandRank(gameData.getComputerCards());
            if ( playerHandRank > computerHandRank) {
                message = "Your hand wins! Opponents hand: " + gameData.getComputerCards()[0] + ", " + gameData.getComputerCards()[1];
                gameData.setPlayerBalance(gameData.getPlayerBalance() + gameData.getPot());
            } else {
                message = "Opponent's hand wins! Opponents hand: " + gameData.getComputerCards()[0] + ", " + gameData.getComputerCards()[1];
                gameData.setComputerBalance(gameData.getComputerBalance() + gameData.getPot());
            }
        }

        modelAndView.addObject("playerCard1", gameData.getPlayerCardImages()[0]);
        modelAndView.addObject("playerCard2", gameData.getPlayerCardImages()[1]);
        modelAndView.addObject("flopCard1", gameData.getTableCardImages()[0]);
        modelAndView.addObject("flopCard2", gameData.getTableCardImages()[1]);
        modelAndView.addObject("flopCard3", gameData.getTableCardImages()[2]);
        modelAndView.addObject("fourthStreet", gameData.getTableCardImages()[3]);
        modelAndView.addObject("river", gameData.getTableCardImages()[4]);
        modelAndView.addObject("pot", gameData.getPot());
        modelAndView.addObject("playerBalance", gameData.getPlayerBalance());
        modelAndView.addObject("computerBalance", gameData.getComputerBalance());
        modelAndView.addObject("message", message);
        modelAndView.setViewName("table");
        return modelAndView;
    }

    public void showNewCard(int index) {
        String fourthStreet = CardUtils.generateRandomCard();
        String fourthStreetImage = CardUtils.cardImageMap.get(fourthStreet);
        String[] tableCards = gameData.getTableCards();
        String[] tableCardImages = gameData.getTableCardImages();
        tableCards[index] = fourthStreet;
        tableCardImages[index] = fourthStreetImage;
        gameData.setTableCards(tableCards);
        gameData.setTableCardImages(tableCardImages);
        gameData.setPlayersLastMove("");
        gameData.setComputersLastMove("");
        gameData.setRaiseCounter(0);
    }

    public static int checkHandRank(String[] hand) {
        String[] fullHand = new String[7];
        String[] tableCards = gameData.getTableCards();
        for ( int i = 0; i < tableCards.length; i++ ) {
            fullHand[i] = tableCards[i];
        }
        fullHand[5] = hand[0];
        fullHand[6] = hand[1];
        Card[] cardArray = setupCardArray(fullHand);
        System.out.println(cardArray.toString());
        return Poker.valueHand(cardArray);
    }

    public static Card[] setupCardArray(String[] cards) {
        Card[] fullHand = new Card[7];
        for ( int i = 0; i < cards.length; i++ ) {
            String card = cards[i];
            String[] suitsAndRanks = card.split("-");
            int suit = 0;
            if ( suitsAndRanks[0].equals("Clubs") ) {
                suit = 1;
            } else if ( suitsAndRanks[0].equals("Diamonds") ) {
                suit = 2;
            } else if ( suitsAndRanks[0].equals("Hearts") ) {
                suit = 3;
            } else if ( suitsAndRanks[0].equals("Spades") ) {
                suit = 4;
            }

            int rank;
            if ( suitsAndRanks[1].equals("J") ) {
                rank = 11;
            } else if ( suitsAndRanks[1].equals("Q") ) {
                rank = 12;
            } else if ( suitsAndRanks[1].equals("K") ) {
                rank = 13;
            } else if ( suitsAndRanks[1].equals("A") ) {
                rank = 14;
            } else {
                rank = Integer.parseInt(suitsAndRanks[1]);
            }

            Card cardObj = new Card(suit, rank);
            System.out.println("RANK: "+ cardObj.getRank());
            System.out.println("SUIT: "+cardObj.getSuit());
            fullHand[i] = cardObj;
        }
        return fullHand;
    }
}
