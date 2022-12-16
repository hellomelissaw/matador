package GameComponents.Board;

import Controllers.GuiController;
import GameComponents.Player;
import Translator.Text;
import gui_fields.GUI_Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeedSquareTest {
    GuiController guiController = new GuiController();

    Player[] testPlayers = new Player[2];
    GUI_Player[] testGuiPlayers = new GUI_Player[2];
    DeedSquare testDeedSquare = new DeedSquare("Test Deed 1",5,guiController);
    Text msg = new Text("src/main/java/Translator/EnglishText", guiController);

    public DeedSquareTest() {
        testPlayers[0] = new Player("TestPlayer 1");
        testPlayers[1] = new Player("TestPlayer 2");

        testGuiPlayers[0] = new GUI_Player("TestPlayer 1");
        testGuiPlayers[1] = new GUI_Player("TestPlayer 2");

        for(int i = 0 ; i < testPlayers.length ; i++) {
            testPlayers[i].setGui(testGuiPlayers[i], guiController, msg);
        }
    }
    @Test
    public void deedOwnerIsTestPlayer1AfterLandingOnIt() {
        testDeedSquare.setLang(msg);
        testPlayers[0].updatePosition(1);
        testDeedSquare.landOn(testPlayers[0]);
        assertEquals(testPlayers[0], testDeedSquare.getDeedOwner());
    }
    @Test
    public void testPlayer2PaysRentToDeedOwner() {
        testDeedSquare.setLang(msg);
        testPlayers[0].depositMoney(20);
        testPlayers[1].depositMoney(20);
        testPlayers[0].updatePosition(1);
        testDeedSquare.landOn(testPlayers[0]);
        testPlayers[1].updatePosition(1);
        testDeedSquare.landOn(testPlayers[1]);
        assertEquals(20, testPlayers[0].getCurrentBalance());
        assertEquals(15, testPlayers[1].getCurrentBalance());
    }

    @Test
    public void playerGetsDeedForFree() {
        testDeedSquare.setLang(msg);
        testDeedSquare.setDeedToFree();
        testDeedSquare.landOn(testPlayers[0]);

        assertEquals(0, testPlayers[0].getCurrentBalance());
    }
}