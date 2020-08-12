package sample.TeenPatti;

public class Main {

    public static void main(String[] args) {

        PokerTable pt = new PokerTable();

        pt.addPlayers("Bishal");
//        pt.addPlayers("Manish");
//        pt.addPlayers("Mimansh");


        pt.setPlayerCard();
        pt.winner();
    }
}

