package src.main.poker.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import src.main.poker.models.GameData;
import src.main.poker.utils.CardUtils;
import src.main.poker.utils.GameUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class PokerGameAPIController {

    private static boolean isPaused = false;

    @GetMapping("/start")
    public static @ResponseBody
    String[] restStartEndpoint() {
        GameData gameData = GameUtils.gameData;
        gameData.setPlayersTurn(true);
        CardUtils.cards = CardUtils.generateCards();
        String playerCard1 = CardUtils.generateRandomCard();
        String playerCard2 = CardUtils.generateRandomCard();
        return new String[] { playerCard1, playerCard2 };
    }

    @GetMapping("/availablemoves")
    public static @ResponseBody
    String[] restAvailableMovesEndpoint() {
        return new String[] { "fold", "bet", "check", "call", "raise", "tableview" };
    }

    @GetMapping("/pause")
    public static @ResponseBody
    JSONObject restPauseEndpoint() throws JSONException, InterruptedException {
        JSONObject response = new JSONObject();
        GameData gameData = GameUtils.gameData;
        gameData.setIsPaused( !gameData.getIsPaused() );
        response.put("result", "Success");
        return response;
    }

    @GetMapping("/fold")
    public static @ResponseBody
    JSONObject restFoldEndpoint() throws JSONException {
        JSONObject response = new JSONObject();
        GameData gameData = GameUtils.gameData;
        gameData.setComputerBalance(gameData.getComputerBalance() + gameData.getPot());
        response.put("result", "Success");
        return response;
    }

    @GetMapping("/bet")
    public static @ResponseBody
    JSONObject restBetEndpoint(@RequestParam("amount") long betAmount) throws JSONException {
        GameData gameData = GameUtils.gameData;
        JSONObject response = new JSONObject();
        Long longValue = betAmount;
        if ( longValue == null ) {
            response.put("result", "Please enter an amount to bet");
            return response;
        }
        if ( betAmount > gameData.getPlayerBalance() ) {
            response.put("result", "Bet amount exceeds your balance");
            return response;
        }
        if ( betAmount == 0 ) {
            response.put("result", "Must bet more than 0");
            return response;
        }
        gameData.setPot(gameData.getPot() + betAmount);
        gameData.setPlayerBalance(gameData.getPlayerBalance() - betAmount);
        gameData.setPlayersLastBet(betAmount);
        gameData.setPlayersLastMove("bet");
        gameData.setPlayersTurn(false);
        response.put("result", "Success");
        return response;
    }

    @GetMapping("/raise")
    public static @ResponseBody
    JSONObject restRaiseEndpoint(@RequestParam("amount") long raiseAmount) throws JSONException {
        GameData gameData = GameUtils.gameData;
        JSONObject response = new JSONObject();
        Long longValue = raiseAmount;
        if ( longValue == null ) {
            response.put("result", "Please enter an amount to raise");
            return response;
        }
        if ( raiseAmount == 0 ) {
            response.put("result", "Must raise more than 0, otherwise call");
            return response;
        }
        long computersLastBet = gameData.getComputersLastBet();
        long totalBet = raiseAmount + computersLastBet;
        if ( totalBet > gameData.getPlayerBalance() ) {
            response.put("result", "Raise amount exceeds your balance");
            return response;
        }
        gameData.setPot(gameData.getPot() + totalBet);
        gameData.setPlayerBalance(gameData.getPlayerBalance() - totalBet);
        gameData.setPlayersLastBet(totalBet);
        gameData.setPlayersLastRaise(raiseAmount);
        gameData.setPlayersLastMove("raise");
        gameData.setPlayersTurn(false);
        response.put("result", "Success");
        return response;
    }

    @GetMapping("/check")
    public static @ResponseBody
    JSONObject restCheckEndpoint() throws JSONException {
        JSONObject response = new JSONObject();
        GameData gameData = GameUtils.gameData;
        if ( gameData.getComputersLastMove() != null && !gameData.getComputersLastMove().equals("check")
             && !gameData.getComputersLastMove().equals("")) {
            response.put("result", "You cannot check when players have opened");
            return response;
        }
        gameData.setPlayersLastBet(0);
        gameData.setPlayersLastMove("check");
        gameData.setPlayersTurn(false);
        response.put("result", "Success");
        return response;
    }

    @GetMapping("/call")
    public static @ResponseBody
    JSONObject restCallEndpoint() throws JSONException {
        JSONObject response = new JSONObject();
        GameData gameData = GameUtils.gameData;
        long bet = gameData.getComputersLastBet();
        String computersLastMove = gameData.getComputersLastMove();
        if ( computersLastMove.equals("raise") ) {
            bet = gameData.getComputersLastRaise();
        }
        if ( computersLastMove.equals("call") ) {
            response.put("result", "Opponent already called, choose another move");
            return response;
        }
        if ( bet > gameData.getPlayerBalance() ) {
            response.put("result", "Call amount exceeds your balance");
            return response;
        }
        gameData.setPot(gameData.getPot() + bet);
        gameData.setPlayerBalance(gameData.getPlayerBalance() - bet);
        gameData.setPlayersLastBet(bet);
        gameData.setPlayersLastMove("call");
        gameData.setPlayersTurn(false);
        response.put("result", "Success");
        return response;
    }

    @GetMapping("/opponentcard")
    public static @ResponseBody
    String[] restOpponentCardEndpoint() {
        GameData gameData = GameUtils.gameData;
        return gameData.getComputerCards();
    }

    @GetMapping("/opponentTurn")
    public static @ResponseBody
    JSONObject restOpponentTurnEndpoint() throws JSONException, InterruptedException {
        JSONObject response = new JSONObject();
        GameData gameData = GameUtils.gameData;
        Thread.sleep(2500);

        long bet = 0;
        String message = "";
        String[] choices = new String [] {"bet", "check", "call", "raise"};
        boolean playerChecked = ( gameData.getPlayersLastMove().equals("check") );
        boolean playerBet = ( gameData.getPlayersLastMove().equals("bet") );
        boolean playerRaised = ( gameData.getPlayersLastMove().equals("raise") );
        boolean playerCalled = ( gameData.getPlayersLastMove().equals("call") );
        boolean newRound = ( gameData.getPlayersLastMove().equals("") );
        List<String> choiceList = new ArrayList<>(Arrays.asList(choices));

        if ( gameData.getComputerBalance() <= 0 ) {
            message = "You win! opponent has run out of money";
            response.put("result", message);
            return response;
        }

        if ( playerChecked ) {
            choiceList.remove("call");
            choiceList.remove("raise");
        } else if ( playerBet || playerRaised ) {
            choiceList.remove("check");
            choiceList.remove("bet");
        } else if ( playerCalled ) {
            choiceList.remove("bet");
            choiceList.remove("check");
        } else if ( newRound ) {
            choiceList.remove("raise");
            choiceList.remove("call");
        }

        Random r = new Random();
        int randomIndex = r.nextInt(choiceList.size());
        String decision = choiceList.get(randomIndex);

        if ( decision.equals("call") ) {
            bet = gameData.getPlayersLastBet();
            message = "Your turn, opponent called and bet $ " + bet;
        } else if ( decision.equals("check") ) {
            message = "Your turn, opponent checked";
        } else if ( decision.equals("bet") ) {
            bet = ThreadLocalRandom.current().nextLong(0, gameData.getComputerBalance()/5);
            message ="Your turn, opponent bet $ " + bet;
        } else if ( decision.equals("raise") ) {
            long raiseAmount = ThreadLocalRandom.current().nextLong(0, 50);
            gameData.setComputersLastRaise(raiseAmount);
            if ( playerBet ) {
                bet = raiseAmount + gameData.getPlayersLastBet();
            } else {
                bet = raiseAmount;
            }
            gameData.setRaiseCounter(gameData.getRaiseCounter()+1);
            message = "Your turn, opponent raised $ " + bet;
        }

        gameData.setPot(gameData.getPot() + bet);
        gameData.setComputerBalance(gameData.getComputerBalance() - bet);
        gameData.setComputersLastBet(bet);
        gameData.setComputersLastMove(decision);

        gameData.setPlayersTurn(true);
        response.put("result", message);
        return response;
    }
}
