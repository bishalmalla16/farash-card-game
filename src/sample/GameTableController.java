package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sample.TeenPatti.PokerTable;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameTableController {

    @FXML
    private ImageView p1c1,p1c2,p1c3,p2c1,p2c2,p2c3,p3c1,p3c2,p3c3,p4c1,p4c2,p4c3;
    @FXML
    private Text playerName;
    @FXML
    private GridPane gameTable;
    @FXML
    Text cpu1,cpu2,cpu3, result;
    @FXML
    Button show, playAgain, leavegame, exitGame;

    private static boolean again = false;

    private int turn=0,temp=0;
    private List<ImageView> imageViews = new ArrayList<>();
    private String cardString = "";
    private String playersString = "";
    private static String [] staticPlayers;

//    public void initialize(String player1, String player2, String player3, String player4){

    public void initialize(boolean singlePlayer, String player1){
        this.playerName.setText(player1.isEmpty() ? "Player" : player1);
        imageViews.add(p1c1);
        imageViews.add(p1c2);
        imageViews.add(p1c3);
        imageViews.add(p2c1);
        imageViews.add(p2c2);
        imageViews.add(p2c3);
        imageViews.add(p3c1);
        imageViews.add(p3c2);
        imageViews.add(p3c3);
        imageViews.add(p4c1);
        imageViews.add(p4c2);
        imageViews.add(p4c3);
        PokerTable pt = new PokerTable();
        pt.addPlayers(player1.isEmpty() ? "Player" : player1);
        pt.addPlayers("CPU1");
        pt.addPlayers("CPU2");
        pt.addPlayers("CPU3");
        pt.setPlayerCard();
        pt.winner();

        for(int i=0; i<4;i++){
            for(int j=0; j<3; j++){
                String card = "resources/blue_back.png";
                imageViews.get(turn).setImage(new Image(getClass().getResourceAsStream(card)));
                turn++;
            }

        }

        show.setOnAction(e-> {
            Platform.runLater(()->{
                turn = 0;
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        String card = "resources/deckOfCards/" + PokerTable.playerCardsForTable[i][j] + ".png";
                        imageViews.get(turn).setImage(new Image(getClass().getResourceAsStream(card)));
                        turn++;
                    }
                }

                Alert alert = new Alert(Alert.AlertType.NONE);
                ButtonType playAgain = new ButtonType("Play Again", ButtonBar.ButtonData.OK_DONE);
                ButtonType home = new ButtonType("Home", ButtonBar.ButtonData.FINISH);
                alert.getButtonTypes().addAll(playAgain, home);
                alert.setHeaderText(PokerTable.winner);
                Optional<ButtonType> option = alert.showAndWait();
                FXMLLoader parent = new FXMLLoader();
                parent.setLocation(getClass().getResource("resources/FXMLs/sample.fxml"));
                if (option.isPresent() && option.get().equals(playAgain)) {
                    FXMLLoader gameLoader = new FXMLLoader();
                    gameLoader.setLocation(getClass().getResource("resources/FXMLs/GameTable.fxml"));
                    try {
                        gameTable.getScene().getWindow().setHeight(600);
                        gameTable.getScene().getWindow().setWidth(700);
                        gameTable.getScene().setRoot(gameLoader.load());
                        GameTableController gameTableController = gameLoader.getController();
                        gameTableController.initialize(true, Controller.Player1);
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                } else {
                    try {
                        gameTable.getScene().getWindow().setWidth(720);
                        gameTable.getScene().getWindow().setHeight(480);
                        gameTable.getScene().setRoot(parent.load());
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                }
            });

        });

    }

    public void initialize(String [] players){
        GameTableController.staticPlayers = players;
        playAgain.setOnAction(event->{
            playAgain.setVisible(false);
            show.setVisible(true);
            exitGame.setVisible(false);
            this.initialize(GameTableController.staticPlayers);
        });
        cpu1.setText("");
        cpu2.setText("");
        cpu3.setText("");
        PokerTable pt = new PokerTable();
        this.playerName.setText("BServer");

        for (int i = 0; i<= ServerController.n; i++){
            if(i==0){
                pt.addPlayers("BServer");
                imageViews.add(p1c1);
                imageViews.add(p1c2);
                imageViews.add(p1c3);
            }
            if (i==1) {
                cpu2.setText(players[1]);
                imageViews.add(p3c1);
                imageViews.add(p3c2);
                imageViews.add(p3c3);
                pt.addPlayers(players[1]);
            }
            if (i==2) {
                cpu1.setText(players[2]);
                imageViews.add(p2c1);
                imageViews.add(p2c2);
                imageViews.add(p2c3);
                pt.addPlayers(players[2]);
            }
            if (i==3) {
                cpu3.setText(players[3]);
                imageViews.add(p4c1);
                imageViews.add(p4c2);
                imageViews.add(p4c3);
                pt.addPlayers(players[3]);
            }
        }

        pt.setPlayerCard();
        pt.winner();

        playersString = "";
        cardString = "";
        result.setText("");

        for(int i=0; i<=ServerController.n;i++){
            for(int j=0; j<3; j++) {
                String card = "resources/blue_back.png";
                imageViews.get(turn).setImage(new Image(getClass().getResourceAsStream(card)));
                cardString += PokerTable.playerCardsForTable[i][j] + ",";
                turn++;
            }
            playersString += players[i] + ",";
        }

        show.setOnAction(e-> {

            show.setVisible(false);
            Platform.runLater(() -> {
                turn = 0;
                for (int i = 0; i <= ServerController.n; i++) {
                    for (int j = 0; j < 3; j++) {
                        String card = "resources/deckOfCards/" + PokerTable.playerCardsForTable[i][j] + ".png";
                        imageViews.get(turn).setImage(new Image(getClass().getResourceAsStream(card)));
                        turn++;
                    }
                }
                result.setText(PokerTable.winner + "!!");

                playAgain.setVisible(true);
                exitGame.setVisible(true);


            });
        });

        exitGame.setOnAction(event -> {
            exitGame.getScene().getWindow().hide();
        });

        cardString = cardString.substring(0, cardString.length()-1);
        playersString = playersString.substring(0, playersString.length()-1);


        for (int i=0; i<ServerController.n; i++){
            try{
                System.out.println(ServerController.socket[i].toString());
                PrintWriter printWriter = new PrintWriter(ServerController.socket[i].getOutputStream(), true);
                printWriter.println(cardString);
                printWriter.println(playersString);
                printWriter.println(PokerTable.winner);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }


    }

    public void initialize(String[][] playerCardsForTable, String[] players, String winner){
        cpu1.setText("");
        cpu2.setText("");
        cpu3.setText("");


        for (int  i=0; i<players.length; i++){
            if(i==0){

                imageViews.add(p1c1);
                imageViews.add(p1c2);
                imageViews.add(p1c3);
            }
            if (i==1) {
                cpu2.setText(players[1]);
                imageViews.add(p3c1);
                imageViews.add(p3c2);
                imageViews.add(p3c3);

            }
            if (i==2) {
                cpu1.setText(players[2]);
                imageViews.add(p2c1);
                imageViews.add(p2c2);
                imageViews.add(p2c3);

            }
            if (i==3) {
                cpu3.setText(players[3]);
                imageViews.add(p4c1);
                imageViews.add(p4c2);
                imageViews.add(p4c3);

            }
        }


        for(int i=0; i<players.length;i++){
            for(int j=0; j<3; j++) {
                String card = "resources/blue_back.png ";
                imageViews.get(turn).setImage(new Image(getClass().getResourceAsStream(card)));
                turn++;
            }
        }

        show.setOnAction(e-> {



            show.setVisible(false);
            Platform.runLater(() -> {
                turn = 0;
                for (int i = 0; i < players.length; i++) {
                    for (int j = 0; j < 3; j++) {
                        String card = "resources/deckOfCards/" + playerCardsForTable[i][j] + ".png";
                        imageViews.get(turn).setImage(new Image(getClass().getResourceAsStream(card)));
                        turn++;
                    }
                }

                result.setText(winner + "!!");


            });
        });

    }


}
