package src.main.poker.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import src.main.poker.RunPokerGame;
import src.main.poker.models.GameData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import src.main.poker.utils.GameUtils;

@Controller
public class PokerGameController {

    GameUtils gameUtils = new GameUtils();

    @GetMapping("/jspStart")
    public ModelAndView jspStartEndpoint(ModelAndView modelAndView) {
        // start the game
        gameUtils.startGame();
        String message = "Your turn";
        return gameUtils.updateTable(modelAndView, message, true, false);
    }

    @GetMapping("/jspPlayAgain")
    public ModelAndView jspPlayAgainEndpoint(ModelAndView modelAndView) throws JSONException, InterruptedException {
        // play another round
        gameUtils.continueGame();
        String message = "Your turn";
        return gameUtils.updateTable(modelAndView, message, true, false);
    }

    @GetMapping("/jspFold")
    public ModelAndView jspFoldEndpoint(ModelAndView modelAndView) throws JSONException {
        // fold current game
        GameData gameData = GameUtils.gameData;
        PokerGameAPIController.restFoldEndpoint();
        modelAndView = new ModelAndView(new MappingJackson2JsonView());
        String message = "You folded, opponent wins: $ " + gameData.getPot();
        return gameUtils.updateTable(modelAndView, message, true, false);
    }

    @GetMapping("/jspBet")
    public ModelAndView jspBetEndpoint(@RequestParam("amount") long betAmount, ModelAndView modelAndView) throws JSONException {
        // place bet
        JSONObject responseJSON = PokerGameAPIController.restBetEndpoint(betAmount);
        modelAndView = new ModelAndView(new MappingJackson2JsonView());
        boolean isError = false;
        String message = "You bet $ " + betAmount;
        String response = responseJSON.getString("result");
        if ( ! response.equals("Success") ) {
            message = response;
            isError = true;
        }

        return gameUtils.updateTable(modelAndView, message, false, isError);
    }

    @GetMapping("/jspCheck")
    public ModelAndView jspCheckEndpoint(ModelAndView modelAndView) throws JSONException {
        // check
        JSONObject responseJSON = PokerGameAPIController.restCheckEndpoint();
        modelAndView = new ModelAndView(new MappingJackson2JsonView());
        boolean isError = false;
        String message = "You checked";
        String response = responseJSON.getString("result");
        if ( ! response.equals("Success") ) {
            message = response;
            isError = true;
        }
        return gameUtils.updateTable(modelAndView, message, false, isError);
    }

    @GetMapping("/jspCall")
    public ModelAndView jspCallEndpoint(ModelAndView modelAndView) throws JSONException {
        // match the opponents bet
        GameData gameData = GameUtils.gameData;
        JSONObject responseJSON = PokerGameAPIController.restCallEndpoint();
        modelAndView = new ModelAndView(new MappingJackson2JsonView());
        boolean isError = false;
        String message = "You called and bet $ " + gameData.getComputersLastBet();
        String response = responseJSON.getString("result");
        if ( ! response.equals("Success") ) {
            message = response;
            isError = true;
        }
        return gameUtils.updateTable(modelAndView, message, false, isError);
    }

    @GetMapping("/jspRaise")
    public ModelAndView jspRaiseEndpoint(@RequestParam("amount") long raiseAmount, ModelAndView modelAndView) throws JSONException {
        // raise the current bet value to an amount
        GameData gameData = GameUtils.gameData;
        JSONObject responseJSON = PokerGameAPIController.restRaiseEndpoint(raiseAmount);
        modelAndView = new ModelAndView(new MappingJackson2JsonView());
        long totalBet = raiseAmount + gameData.getComputersLastBet();
        boolean isError = false;
        String message = "You raised to $ " + totalBet;
        String response = responseJSON.getString("result");
        if ( ! response.equals("Success") ) {
            message = response;
            isError = true;
        }

        return gameUtils.updateTable(modelAndView, message, false, isError);
    }

    @GetMapping("/jspPause")
    public ModelAndView jspPauseEndpoint(ModelAndView modelAndView) throws JSONException, InterruptedException {
        // pause the game
        JSONObject responseObj = PokerGameAPIController.restPauseEndpoint();
        GameData gameData = GameUtils.gameData;
        if ( gameData.getIsPaused() ) {
            RunPokerGame.pause();
        }
        modelAndView = new ModelAndView(new MappingJackson2JsonView());
        String message = "Paused";
        return gameUtils.updateTable(modelAndView, message, true, false);
    }

    @GetMapping("/jspOpponentTurn")
    public ModelAndView jspEndTurnEndpoint(ModelAndView modelAndView) throws JSONException, InterruptedException {
        // start opponents turn
        JSONObject responseObj = PokerGameAPIController.restOpponentTurnEndpoint();
        modelAndView = new ModelAndView(new MappingJackson2JsonView());
        String message = responseObj.getString("result");
        return gameUtils.updateTable(modelAndView, message, false, false);
    }
}
