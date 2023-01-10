package GameComponents;

import Controllers.GuiController;
import GameComponents.Board.Deed;
import GameComponents.Board.Deed_Buildable;
import GameComponents.Board.DeedSquare_Buildable;
import GameComponents.Board.Deed_NonBuildable;
import gui_fields.GUI_Player;
import Translator.Text;

/*
===================================================================================
This class is reused from our CDIO2 project and built upon.
===================================================================================
 */
public class Player {

    boolean guiOn = true;
    private boolean testing = false;
    GuiController guiController;
    GUI_Player guiPlayer;
    Text msg;
    private int squareIndex = 0;
    private String playerName;
    private Account playerAccount = new Account();

    private Cardholder cardholder = new Cardholder();
    private String winnerName;

    Bank bank;

    public Player(String playerName) {
        this.playerName = playerName;

    }

    public void setLang(Text msg) {
        this.msg = msg;
    }

    public void setGui(GUI_Player guiPlayer, GuiController guiController, Text msg) {
        if (guiOn) {
            this.guiPlayer = guiPlayer;
            playerAccount.setGuiAccount(guiPlayer);
            this.guiController = guiController;
        }
        this.msg = msg;
        testing = false;
    }

    public void setBank(Bank bank){
        this.bank = bank;
    }

    public void setStartBalance(int startBalance, boolean transactionToBankParameter) {
        playerAccount.deposit(startBalance);
        if (guiOn) {guiPlayer.setBalance(startBalance); }

        if (transactionToBankParameter){
            bank.takeMoneyFromBank(startBalance);
        }

    }

    /**
     * Deposits money in Player's Account
     * @param newPoints amount of Monopoly money to deposit
     * @param transactionToBankParameter is true if the transaction is with the bank and not other players
     */
    public void withdrawMoney(int newPoints, boolean transactionToBankParameter) {

        if (transactionToBankParameter){
            playerAccount.withDraw(newPoints);
            bank.giveMoneyToBank(newPoints);
        }else if (!transactionToBankParameter){
            playerAccount.withDraw(newPoints);
        }
    }

    /**
     * Withdraws money from Player's Account
     * @param newPoints amount of Monopoly Money to withdraw
     * @param transactionToBankParameter true if the transaction is with the bank and not other players
     */
    public void depositMoney(int newPoints, boolean transactionToBankParameter){
        if (transactionToBankParameter){
            int cashedOutMoney = bank.takeMoneyFromBank(newPoints);
            playerAccount.deposit(cashedOutMoney);
        }else if (!transactionToBankParameter){
            playerAccount.deposit(newPoints);
        }
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
        boolean getStartMoney = false;
        int currentPos = squareIndex;
        if(currentPos == 30 || currentPos == 0) {getStartMoney = false;}

        for(int i = 0; i < Math.abs(distance); i++) {
            if (distance < 0) {
                if(squareIndex > 0) {
                    squareIndex--;
                } else if (squareIndex == 0) {
                    squareIndex = 39;
                }

            } else {
                if (squareIndex < 39) {
                    squareIndex++;

                } else {
                    squareIndex = 0;
                    getStartMoney = true;
                }
            }
        }

        if (getStartMoney) {
            msg.printText("passStart", "na");
            playerAccount.deposit(4000);
        }
            if(!testing){ if(guiOn) { guiController.move(guiPlayer, currentPos, squareIndex); }}
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

    public boolean IsGroupOwner(String color) {
        boolean ownerStatus = cardholder.getOwnerStatus(color);
        return ownerStatus;
    }

    public void takeBuildableDeed(Deed_Buildable deed) {
        cardholder.addDeedBuildable(deed);
    }

    public void takeNonBuildableDeed(Deed_NonBuildable deed) {
        cardholder.addDeedNonBuildable(deed);
    }

    public Deed_Buildable[] getBuildableDeeds() {
        Deed_Buildable[] deedList = cardholder.getBuildable();
        return deedList;
    }

    public void buyHouse(DeedSquare_Buildable[] lotsToBuildOn, int housesToBuy) {
        Deed_Buildable[] deedsToBuildOn = new Deed_Buildable[lotsToBuildOn.length];
        for(int i = 0; i < lotsToBuildOn.length; i++) {
            deedsToBuildOn[i] = lotsToBuildOn[i].getDeed();
        }

        boolean enoughHouses = bank.areThereEnoughHouses(housesToBuy);
        if (enoughHouses) {
            for (int j = 0; j < housesToBuy; j++) {
                for (int i = 0; i < deedsToBuildOn.length; i++) {
                    String color = deedsToBuildOn[i].getColor();
                    boolean ownsGroup = cardholder.getOwnerStatus(color);
                    if (ownsGroup) {
                        boolean clearedForPurchase = cardholder.houseCountIsLevel(color, deedsToBuildOn[i]);
                        if (clearedForPurchase) {
                            int buildingPrice = deedsToBuildOn[i].getBuildingPrice();
                            int currentBalance = playerAccount.getBalance();
                            if (currentBalance > 0 && currentBalance - buildingPrice >= 0) {
                                playerAccount.withDraw(buildingPrice);
                                bank.buyHouseFromBank(1,buildingPrice);
                                int count = deedsToBuildOn[i].getHouseCount();
                                count++;
                                deedsToBuildOn[i].setHouseCount(count);
                                lotsToBuildOn[i].setHouseCount(count);
                                System.out.println("There is now " + count + " houses on Square #" + i);
                                System.out.println("Player's new balance is " + playerAccount.getBalance());

                            } else {
                                System.out.println("Du har ikke nok penge til at købe dette hus.");
                            }
                        } else {
                            System.out.println("Du skal bygge en jævn mængde hus på alle grunde i gruppen før du kan bygge videre.");
                        }

                    } else {
                        System.out.println("Du ejer ikke alle grunde i gruppen, derfor kan du ikke bygge endnu.");
                    }
                }
            }
        }
    }

    public void buyHotel(DeedSquare_Buildable[] lotsToBuildOn) {
        for (int i = 0; i < lotsToBuildOn.length; i++) {
            Deed_Buildable deed = lotsToBuildOn[i].getDeed();
            int houseCount = lotsToBuildOn[i].getHouseCount();
            boolean availableHotels = bank.areThereStillHotels();
            if (houseCount == 4 && availableHotels) {
                int currentBalance = playerAccount.getBalance();
                int buildingPrice = deed.getBuildingPrice();
                if (currentBalance > 0 && currentBalance - buildingPrice >= 0) {
                    playerAccount.withDraw(buildingPrice);
                    bank.buyHotelFromBank(1,buildingPrice);
                    lotsToBuildOn[i].setHouseCount(0);
                    lotsToBuildOn[i].setHasHotel(true);

                } else {
                    System.out.println("Du har ikke nok penge til at købe dette hotel.");
                }

            } else if (!availableHotels) {
                System.out.println("Der er ikke flere hoteller i banken");
            } else {
                System.out.println("Du har ikke nok huse til at bygge et hotel.");
            }
        }
    }


    public int getHouses() {
        return playerAccount.getHouses();
    }
    public int getHotels(){
        return playerAccount.getHotels();
    }
    public void acquireHouse(int acquiredHouses, int price) {
        playerAccount.acquireHouse(acquiredHouses,price);
    }

    public void sellHouses(int soldHouses, int price){
        playerAccount.sellHouses(soldHouses,price);
    }

    public void acquireHotel(int acquiredHotels, int price){
        playerAccount.acquireHotel(acquiredHotels,price);
    }

    public void sellHotel(int soldHotels, int price){
        playerAccount.sellHotel(soldHotels,price);
    }
}

