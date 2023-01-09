package GameComponents.Board;

import GameComponents.Player;

public class ParkingSquare extends Square{
    Player[] players;


    public ParkingSquare(String freeParkingSquare) {
        super(freeParkingSquare);


    }
    public void landOn(Player currentPlayer) {
        int currentPosition = currentPlayer.getPosition();
        if (currentPosition == 20) {
            System.out.println(msg.getText("freeParking"));
        }
    }
}

