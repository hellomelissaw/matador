package Controllers;

import GameComponents.Board.Deed;
import GameComponents.Board.Deed_NonBuildable;
import GameComponents.Player;
import Translator.Text;
import org.junit.Test;

import static org.junit.Assert.*;

public class SellControllerTest {

    @Test
    public void sellLot() {
        GuiController guiController = new GuiController();
        Text msg = new Text("src/main/java/Translator/DanskTekst", guiController);
        SellController sellController = new SellController(guiController,msg);
        Player player = new Player("TestPlayer");
        int[] array = {50,20,10,5,50,20,};
        Deed_NonBuildable deed = new Deed_NonBuildable(100,array,"Shipping");
        player.takeNonBuildableDeed(deed);
        assertEquals("Shipping" ,player.getNonBuildableDeeds()[0].getDeedName());
    }
}