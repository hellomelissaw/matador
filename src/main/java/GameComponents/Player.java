package GameComponents;

import Controllers.GuiController;
import gui_fields.GUI_Player;
import Translator.Text;

/*
===================================================================================
This class is reused from our CDIO2 project and built upon.
===================================================================================
 */
public class Player {
    private boolean testing = true;
    GuiController guiController;
    GUI_Player guiPlayer;

    Text msg;
    private int squareIndex = 0;
    private String playerName;
    Account playerAccount = new Account();
    private String winnerName;

   // private boolean hasPassedStart = false;

    public Player(String playerName) {
        this.playerName = playerName;

    }

    public void setGui(GUI_Player guiPlayer, GuiController guiController, Text msg) {
        this.guiPlayer = guiPlayer;
        playerAccount.setGuiAccount(guiPlayer);
        this.guiController = guiController;
        this.msg = msg;
        testing = false;
    }

    public void setStartBalance(int startBalance) {
        playerAccount.deposit(startBalance);
        guiPlayer.setBalance(startBalance);
    }

    /**
     * Deposits money in Player's Account
     * @param newPoints amount of Monopoly money to deposit
     */
    public void withdrawMoney(int newPoints) {
        playerAccount.withDraw(newPoints);
    }

    /**
     * Withdraws money from Player's Account
     * @param newPoints amount of Monopoly Money to withdraw
     */
    public void depositMoney(int newPoints){
        playerAccount.deposit(newPoints);
    }

    public int getCurrentBalance(){
        return (playerAccount.getBalance());
    }

    public String getPlayerName (){
        return playerName;
    }

    /**
     * Updates the position of the Player according to the sum of the dice in rings from square 0 to 23
     * @param distance amount of squares to move player's car
     * @return index of the Square that the Player is moved to after throwing dice
     */
    public void updatePosition(int distance) {
        boolean getStartMoney = true;
        int currentPos = squareIndex;
        if(currentPos == 30) {getStartMoney = false;}

        for(int i = 0; i < Math.abs(distance); i++) {
            if (distance < 0) {  }
            if (squareIndex < 39) {
                squareIndex++;

            } else {
                squareIndex = 0;
                if (getStartMoney) {
                    msg.printText("passStart", "na");
                    playerAccount.deposit(2);
                }
            }
        }

        if(!testing){ guiController.move(guiPlayer, currentPos, squareIndex);}
    }

    public int getPosition(){
        return squareIndex;
    }

    public int getDistanceToMove(int newSquareIndex, int boardLength) {
        int distance;
        if (squareIndex > newSquareIndex) {
            distance = boardLength - squareIndex + newSquareIndex;
            System.out.println("CurrentPos > i, dist to move:" + distance);

        } else {
            distance = boardLength - squareIndex - (boardLength - newSquareIndex);
            System.out.println("CurrentPos < i, dist to move: " + distance);
        }
        return distance;
    }

    public boolean isBankrupt() {
        return playerAccount.getAccountStatus();

    }
    @Override
    public String toString() {
        return playerName;
    }

    // Methode is inspired from internet https://www.geeksforgeeks.org/java-program-for-program-to-find-largest-element-in-an-array/
    public String winner(Player[] player) {
        int winner = player[0].getCurrentBalance();
        for (int i = 0; i < player.length; i++) {
            if (player[i].getCurrentBalance() > winner) {
                winner = player[i].getCurrentBalance();
                winnerName = player[i].getPlayerName();
            }
        }
        return winnerName;
    }

}

