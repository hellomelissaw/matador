package Controllers;
import GameComponents.Board.*;
import GameComponents.Board.Square;
import GameComponents.Cup;
import GameComponents.Cup_stub;
import GameComponents.Player;
import Translator.*;
import GameComponents.Bank;

import java.util.ArrayList;

public class GameController {
    boolean useCupStub = true;
    boolean testingInit = true;
    boolean testingActionButtons = true;
    boolean testingHouseCount = false;

    boolean testStartBalance = false;
    GuiController guiController = new GuiController();
    //private int playerCount = 0;
    String userInput;
    int balance = 0;
    Player[] players;
    Square[] squares;
    Text msg = new Text("src/main/java/Translator/DanskTekst", guiController);

    int playerCount = 0;

    int jailCounter = 0;

    int counter = 0;

    Bank bank = new Bank();

    public void init() {
        guiController.setLang(msg);
        testingInit = true;
        if (testingInit){
            msg = new Text("src/main/java/Translator/DanskTekst", guiController);
            //msg = new Text("src/main/java/Translator/EnglishText", guiController);
            //guiController.initFieldTitles(msg);
            playerCount = 3;
            if(testStartBalance){
                balance = 4000;

            } else { balance = 30000; }


            players = new Player[playerCount];

            players[0] = new Player("Marc"); // INITIALISE EACH PLAYER WITH NAME
            players[0].setGui(guiController.createGuiPlayer(players[0]),guiController,msg);
            players[0].setBank(bank); //INITIALISE BANK WITHIN PLAYER
            players[0].setStartBalance(balance,true); // DEPOSIT INITIAL BALANCE


            players[1] = new Player("Germaine"); // INITIALISE EACH PLAYER WITH NAME
            players[1].setGui(guiController.createGuiPlayer(players[1]),guiController,msg);
            players[1].setBank(bank); //INITIALISE BANK WITHIN PLAYER
            players[1].setStartBalance(balance, true); // DEPOSIT INITIAL BALANCE

            players[2] = new Player("Harry"); // INITIALISE EACH PLAYER WITH NAME
            players[2].setGui(guiController.createGuiPlayer(players[2]),guiController,msg);
            players[2].setBank(bank); //INITIALISE BANK WITHIN PLAYER
            players[2].setStartBalance(balance, true); // DEPOSIT INITIAL BALANCE

            if (playerCount > 3) {
                players[3] = new Player("Sara"); // INITIALISE EACH PLAYER WITH NAME
                players[3].setGui(guiController.createGuiPlayer(players[3]),guiController,msg);
                players[3].setBank(bank); //INITIALISE BANK WITHIN PLAYER
                players[3].setStartBalance(balance, true); // DEPOSIT INITIAL BALANCE

                players[4] = new Player("Megan"); // INITIALISE EACH PLAYER WITH NAME
                players[4].setGui(guiController.createGuiPlayer(players[4]),guiController,msg);
                players[4].setBank(bank); //INITIALISE BANK WITHIN PLAYER
                players[4].setStartBalance(balance, true); // DEPOSIT INITIAL BALANCE


                if (playerCount == 6) {
                    players[5] = new Player("Adam"); // INITIALISE EACH PLAYER WITH NAME
                    players[5].setGui(guiController.createGuiPlayer(players[5]),guiController,msg);
                    players[5].setBank(bank); //INITIALISE BANK WITHIN PLAYER
                    players[5].setStartBalance(balance, true); // DEPOSIT INITIAL BALANCE
                }
            }

        } else {
            //String[] lang = {"DanskTekst"};
            //int langIndex = guiController.getUserInteger("You are in English mode. Enter 1 to keep English or enter 2 to switch to Danish."); //GETS USER TO CHOOSE LANGUAGE
           // String langFile = "src/main/java/Translator/" + lang[0];
            //msg = new Text(langFile, guiController);

            //guiController.initFieldTitles(msg);

            //String userInput;

            //INITIALIZING PLAYERS
            //System.out.println(msg.getText("enterPlayerCount"));
            msg.printText("welcomeMessage", "na");
            boolean playerCountInvalid = true;
            while (playerCountInvalid) {
                // playerCount = userInput.nextInt();
                //System.out.println(playerCount);
                playerCount = guiController.getUserInteger(msg.getText("enterPlayerCount"));
                if (playerCount >= 3 && playerCount <= 6) {
                    playerCountInvalid = false;

                } else {
                    msg.printText("invalidCount", "na");

                }
            }
            balance = 30000;//SETS START BALANCE ACCORDING TO AMOUNT OF PLAYERS INPUT

            players = new Player[playerCount];

            for (int i = 0; i < playerCount; i++) {
                int playerNumber = i + 1;
                boolean duplicateName = true;
                while(duplicateName) {
                    userInput = guiController.getUserString(playerNumber);

                    if (i == 0) {
                        duplicateName = false;
                        System.out.println("First Player");

                    } else {
                        for (int j = 0; j < i; j++) {
                            String name = players[j].getPlayerName();
                            if (name.equals(userInput)) {
                                duplicateName = true;
                                msg.printText("duplicateName", "na");
                                break;

                            } else {duplicateName = false;}
                        }
                    }
                }

                players[i] = new Player(userInput); // INITIALISE EACH PLAYER WITH NAME
                players[i].setGui(guiController.createGuiPlayer(players[i]),guiController,msg);
                players[i].setBank(bank); //INITIALISE BANK WITHIN PLAYER
                players[i].setStartBalance(balance, true); // DEPOSIT INITIAL BALANCE


            }
        }

        BoardInit board = new BoardInit(guiController, msg, players);
        squares = board.getSquareArr();
        board.initChanceSquare(squares);
        msg.printText("startGame", "na");

    }

    public void run() {
         // SET TO TRUE WHEN TESTING LANDING ON SPECIFIC SQUARE (SET SUM IN Cup_stub)
        Cup cup;
        if (useCupStub) {
            cup = new Cup_stub(guiController);
        } else {
            cup = new Cup(guiController);
        }

        int newPosition;
        int fine = 1000;

        boolean gameOver = false;

        while (gameOver == false) {



            for (int i = 0; i < playerCount; i++) { //THROWS DICE AND UPDATES PLAYER'S POSITION
                int sum;
                boolean isInJail = players[i].checkInJail();


                if (isInJail) {

                    msg.printText("fængsel", "na");
                    String[] jailOptions = {"Betal bøde?", "Kast terninger?"};
                    String name;
                    name = guiController.getUserSelection("Betal bøde på 1000 kr med det samme? eller Prøv heldet med terningekast!", jailOptions);

                    if (players[i].jailCounter() < 3) {

                        if (name == "Betal bøde?") {
                            players[i].withdrawMoney(fine, true);
                            int currentBalance = players[i].getCurrentBalance();
                            System.out.println(msg.getText("newBalance") + currentBalance);
                            msg.printText("forladFængsel", "Du har nu betalt bøden, du kan nu forlade fængsel!");
                            players[i].moveOutJail();

                        } else if (name == "Kast terninger?") {

                            msg.printText("rollDice", players[i].getPlayerName());

                            boolean sameValue = cup.rollAndCheckEqualValueOfDice();
                            players[i].jailIncrement();

                            if (sameValue) {
                                players[i].moveOutJail();
                                msg.printText("kastOgForladFængsel", "na");
                                //Extra turn when leaving jail missing! maybe have done to final product!

                                msg.printText("rollDice", players[i].getPlayerName());
                                sum = cup.getSum();
                                players[i].updatePosition(sum);
                                newPosition = players[i].getPosition();
                                squares[newPosition].landOn(players[i]);

                            } else {
                                System.out.println("Vent til næste runde");
                            }

                        }
                    } else if (players[i].jailCounter() == 3) {
                        msg.printText("spildt3Runder", "na");
                        players[i].withdrawMoney(fine, true);
                        int currentBalance = players[i].getCurrentBalance();
                        System.out.println(msg.getText("newBalance") + currentBalance);
                        msg.printText("forladFængsel", "na");
                        players[i].moveOutJail();
                    }
                }

                if (!isInJail){
                    if(testingActionButtons){setOwnerForTesting(i);}

                    if(players[i].getBuildableDeeds().length > 0) {
                        boolean rollDice = false;
                        while (!rollDice) {
                            String userChoice = guiController.getUserAction(players[i].getPlayerName());

                            if (userChoice.equals("Byg")) {
                                ArrayList<Deed_Buildable> updatedDeedList = new ArrayList<Deed_Buildable>();
                                Deed_Buildable[] playerDeeds = players[i].getBuildableDeeds();
                                for(int j = 0 ; j < players[i].getBuildableDeeds().length ; j++) {
                                    updatedDeedList.add(playerDeeds[j]);
                                }

                                ArrayList<Deed_Buildable> selectedLots = new ArrayList<Deed_Buildable>();

                                boolean selectingMoreLots = true;
                                while (selectingMoreLots) {

                                    String userLot = guiController.getUserLot(players[i], updatedDeedList);
                                    selectedLots.add(getDeedFromName(userLot, i));
                                    updatedDeedList.remove(getDeedFromName(userLot, i));
                                    if(updatedDeedList.size()==0){
                                        selectingMoreLots = false;
                                    } else {
                                        selectingMoreLots = guiController.getUserBoolean(msg.getText("selectMoreLots"));
                                    }
                                }

                                Deed_Buildable[] selectedLotsArr = new Deed_Buildable[selectedLots.size()];
                                selectedLotsArr = selectedLots.toArray(selectedLotsArr);

                                String[] buildOptions = {"Hus", "Hotel"};
                                String buildingType = guiController.getUserSelection(msg.getText("houseOrHotel"), buildOptions);

                                if(buildingType.equals("Hus")){
                                    String[] countOptions = {"1", "2", "3", "4"};
                                    String userHouseCount = guiController.getUserSelection(msg.getText("howManyBuildings"), countOptions);
                                    int houseCount = Integer.parseInt(userHouseCount);
                                    System.out.println("House count: " + houseCount);

                                    players[i].buyHouse(selectedLotsArr, houseCount);

                                } else {
                                    if(testingHouseCount){setHouseCountForTesting(4,i);}

                                    players[i].buyHotel(selectedLotsArr);


                                }

                            } else if (userChoice.equals("Sælg")) {
                                System.out.println("player chose saelg");

                            } else {
                                rollDice = true;
                            }
                        }
                    }
                    msg.printText("rollDice", players[i].getPlayerName());
                    sum = cup.getSum();
                    players[i].updatePosition(sum);
                    newPosition = players[i].getPosition();
                    squares[newPosition].landOn(players[i]);
                }

                   if(players[i].isBankrupt()) {

                        gameOver = true;
                        String winnerName = players[i].winner(players) + " ";
                        msg.printText("gameOver", winnerName);
                        break;
                    }

                    System.out.println("");
                    System.out.println("");


                }

            }

        }


        private void setOwnerForTesting(int i) {

            ((DeedSquare_Buildable)squares[6]).setOwnerForTesting(players[i]);
            ((DeedSquare_Buildable)squares[8]).setOwnerForTesting(players[i]);
            ((DeedSquare_Buildable)squares[9]).setOwnerForTesting(players[i]);
            ((DeedSquare_Buildable)squares[11]).setOwnerForTesting(players[i]);
            ((DeedSquare_Buildable)squares[13]).setOwnerForTesting(players[i]);
            ((DeedSquare_Buildable)squares[14]).setOwnerForTesting(players[i]);

            Deed_Buildable[] testDeeds = players[i].getBuildableDeeds();
            if(players[i].getBuildableDeeds().length > 0){
                for(int j = 0 ; j < testDeeds.length ; j++) {
                    System.out.println(testDeeds[j].getDeedName());
                }
            } else {
                System.out.println("No deeds");
            }

        }

        private void setHouseCountForTesting(int houseCountForTesting, int i) {

            Deed_Buildable[] deeds = players[i].getBuildableDeeds();
            players[i].buyHouse(deeds,houseCountForTesting);
        }

        private Deed_Buildable getDeedFromName(String deedName, int currentPlayerIndex) {
            int deedToBuildOnIndex = 0;
            Deed_Buildable[] deeds = players[currentPlayerIndex].getBuildableDeeds();
            for(int k = 0 ; k < deeds.length ; k++) {
                if(deedName.equals(deeds[k].getDeedName())){
                    deedToBuildOnIndex = k;
                }
            }
            Deed_Buildable deedToBuildOn = deeds[deedToBuildOnIndex];
            return deedToBuildOn;
        }

    }




