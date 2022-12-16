package GameComponents.Board;

import Controllers.GuiController;
import GameComponents.Player;

import Translator.Text;
import gui_fields.GUI_Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class LandOnSquareTest {

    Player testPlayer1 = new Player("TestPlayer 1");
    GUI_Player testGuiPlayer1 = new GUI_Player("TestGuiPlayer 1");
    GuiController guiController = new GuiController();
    JailSquare testJail = new JailSquare("Jail", guiController);
    Text msg = new Text("src/main/java/Translator/EnglishText", guiController);

    public LandOnSquareTest() {
        testPlayer1.setGui(testGuiPlayer1, guiController, msg);
    }
    @Test
    public void landOnJailSquareAndPay1M() {
        testJail.setLang(msg);
        testPlayer1.depositMoney(20);
        testPlayer1.updatePosition(18);
        testJail.landOn(testPlayer1);
        assertEquals(19, testPlayer1.getCurrentBalance());

    }

    @Test
    public void updatePlayerPositionToIndex6AfterJail(){
        testJail.setLang(msg);
        testPlayer1.updatePosition(18);
        testJail.landOn(testPlayer1);
        assertEquals(6,testPlayer1.getPosition());

    }

    @Test
    public void landOnVisitJailAndDontPay() {
        testJail.setLang(msg);
        testPlayer1.depositMoney(20);
        testPlayer1.updatePosition(6);
        testJail.landOn(testPlayer1);
        assertEquals(20,testPlayer1.getCurrentBalance());
    }

    @Test
    public void landOnStartSquare() {
    }
}