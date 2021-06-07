package src.main.poker.models;

public class GameData {
    private int raiseCounter;
    private long playersLastRaise;
    private long computersLastRaise;
    private long pot;
    private long playerBalance;
    private long computerBalance;
    private long playersLastBet;
    private long computersLastBet;
    private String playersLastMove;
    private String computersLastMove;
    private String[] playerCards;
    private String[] computerCards;
    private String[] tableCards;
    private String[] playerCardImages;
    private String[] tableCardImages;
    private boolean playersTurn;
    private boolean isPaused = false;

    public GameData() {}

    public int getRaiseCounter() { return raiseCounter; }
    public void setRaiseCounter(int raiseCounter) { this.raiseCounter = raiseCounter; }
    public long getPlayersLastRaise() { return playersLastRaise; }
    public void setPlayersLastRaise(long playersLastRaise) { this.playersLastRaise = playersLastRaise; }
    public long getComputersLastRaise() { return computersLastRaise; }
    public void setComputersLastRaise(long computersLastRaise) { this.computersLastRaise = computersLastRaise; }
    public long getPot() { return pot; }
    public void setPot(long pot) { this.pot = pot; }
    public long getPlayerBalance() { return playerBalance; }
    public void setPlayerBalance(long playerBalance) { this.playerBalance = playerBalance; }
    public long getComputerBalance() { return computerBalance; }
    public void setComputerBalance(long computerBalance) { this.computerBalance = computerBalance; }
    public long getPlayersLastBet() { return playersLastBet; }
    public void setPlayersLastBet(long playersLastBet) { this.playersLastBet = playersLastBet; }
    public long getComputersLastBet() { return computersLastBet; }
    public void setComputersLastBet(long computersLastBet) { this.computersLastBet = computersLastBet; }
    public String getPlayersLastMove() { return playersLastMove; }
    public void setPlayersLastMove(String playersLastMove) { this.playersLastMove = playersLastMove; }
    public String getComputersLastMove() { return computersLastMove; }
    public void setComputersLastMove(String computersLastMove) { this.computersLastMove = computersLastMove; }
    public String[] getPlayerCards() { return playerCards; }
    public void setPlayerCards(String[] playerCards) { this.playerCards = playerCards; }
    public String[] getComputerCards() { return computerCards; }
    public void setComputerCards(String[] computerCards) { this.computerCards = computerCards; }
    public String[] getTableCards() { return tableCards; }
    public void setTableCards(String[] tableCards) { this.tableCards = tableCards; }
    public String[] getPlayerCardImages() { return playerCardImages; }
    public void setPlayerCardImages(String[] playerCardImages) { this.playerCardImages = playerCardImages; }
    public String[] getTableCardImages() { return tableCardImages; }
    public void setTableCardImages(String[] tableCardImages) { this.tableCardImages = tableCardImages; }
    public boolean getPlayersTurn() { return playersTurn; };
    public void setPlayersTurn(boolean playersTurn) { this.playersTurn = playersTurn; }
    public boolean getIsPaused() { return isPaused; }
    public void setIsPaused(boolean isPaused) { this.isPaused = isPaused; }
}
