package Controllers;

import GameComponents.Board.DeedSquare_Buildable;
import GameComponents.Board.DeedSquare_NonBuildable;
import GameComponents.Board.Deed_Buildable;
import GameComponents.Board.Deed_NonBuildable;
import GameComponents.Player;
import Translator.Text;
import gui_fields.GUI_Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class SellControllerTest {

    GuiController guiController = new GuiController();
    Text msg = new Text("src/main/java/Translator/DanskTekst", guiController);
    SellController sellController = new SellController(guiController,msg);
    Player testPlayer = new Player("TestPlayer");
    Player buyerPlayer = new Player("BuyerPlayer");

    Player[] players = new Player[3];
    int[] array = {1000,50,250,750,2250,4000,6000};
    Deed_Buildable deedBuildable = new Deed_Buildable(1200 ,array,"Rødorvrevej",500);
    //Deed_NonBuildable bunnyPalace = new Deed_NonBuildable(1000, array, "Bunny Palace");
    //Deed_Buildable ratKingdom = new Deed_Buildable(1000, array, "Rat Kingdom", 500);
    DeedSquare_Buildable bunnyPalace = new DeedSquare_Buildable("Bunny Palace", 1200, array, 500);
    DeedSquare_NonBuildable ratKingdom = new DeedSquare_NonBuildable("Rat Kingdom", 1000, array);
    GUI_Player testGuiPlayer = new GUI_Player("Test Player");

    public SellControllerTest() {
        testPlayer.guiIsOn(false);
        buyerPlayer.guiIsOn(false);

        for(int i = 0 ; i < players.length ; i++) {
            players[i] = new Player("TestPlayer"+i);
            players[i].guiIsOn(false);
            players[i].setStartBalance(7000, false);
        }

        bunnyPalace.setOwnerForTesting(players[1]);
        ratKingdom.setOwnerForTesting(players[2]);

    }

    @Test
    public void testPlayerTakeNonBuildableDeed() {

        Deed_NonBuildable deed = new Deed_NonBuildable(100,array,"Shipping");
        testPlayer.takeNonBuildableDeed(deed);
        assertEquals("Shipping" , testPlayer.getNonBuildableDeeds()[0].getDeedName());
    }
    @Test
    public void testGetOwnedFields(){
        testPlayer.addToOwnedFields(deedBuildable);
        (testPlayer.getOwnedFields()[0]).getDeedName();
        String expected = "Rødorvrevej";
        assertEquals(expected , (testPlayer.getOwnedFields()[0]).getDeedName());
    }
    @Test
    public void testChangeOwner(){

        testPlayer.depositMoney(2000, false);
        buyerPlayer.depositMoney(2000, false);
        assertEquals(2000, testPlayer.getCurrentBalance());
        assertEquals(2000,buyerPlayer.getCurrentBalance());

        testPlayer.addToOwnedFields(deedBuildable);
    }

    @Test
    public void player1WantsToBuyAndPlayer2And3LotsShownInArray() {
        sellController.buyLot(players[0], players);
        String[] expected = {"Bunny Palace", "Rat Kingdom"};
        assertEquals(expected, sellController.getLotOptions());

    }
}