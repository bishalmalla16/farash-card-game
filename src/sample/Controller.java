package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    public static String Player1;

    @FXML
    private GridPane gridPane;

    @FXML
    public void exitGame(){
        Platform.exit();
    }

    @FXML
    public void help(){
        Alert help = new Alert(Alert.AlertType.INFORMATION);
        help.setTitle("Farash Card Game Help");
        help.setHeaderText("Farash (also known as teen Patti) is a gambling card game. ");
        help.setContentText("It is usually played with a 52 - card pack without jokers among 2-4 players.\n" +
                "Each player is dealt 3 face down cards. Before dealer giving away the cards, the boot amount will be collected from each player and put in the pot.\n" +
                "The betting then starts by the player next to the dealer.\n" +
                "The player who completes the hand and has the highest ranking is the winner.\n" +
                "The winner can get all the chips in the pot ranking the cards: \n\n" +
                "1. trial (three of same rank)\n" +
                "2.Pure sequence(double run)\n" +
                "3.Sequence (run)\n" +
                "4.Colour\n" +
                "5.Pair(Jud)\n" +
                "6.High card\n");
        help.showAndWait();
    }

    @FXML
    public void multiPlayerMenu(){
        Dialog<ButtonType> menu = new Dialog<>();
        FXMLLoader fxmlLoader = new FXMLLoader();
        menu.initOwner(gridPane.getScene().getWindow());
        menu.setTitle("Multiplayer");
        fxmlLoader.setLocation(getClass().getResource("resources/FXMLs/multiplayerDialog.fxml"));
        try{
            menu.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            e.printStackTrace();
        }
        menu.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        menu.showAndWait();
    }

    @FXML
    public void singleplayer(){

        Dialog<ButtonType> single = new Dialog<>();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("resources/FXMLs/SinglePlayerDialog.fxml"));

        try {
            single.setDialogPane(fxmlLoader.load());
            single.setTitle("Single Player");
            single.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
        }catch (IOException e){
            e.printStackTrace();
        }

        Optional<ButtonType> buttonType = single.showAndWait();

        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)){
            SinglePlayerDialogController singlePlayerDialogController = fxmlLoader.getController();
            FXMLLoader gameLoader = new FXMLLoader();
            gameLoader.setLocation(getClass().getResource("resources/FXMLs/GameTable.fxml"));
            try {
                gridPane.getScene().getWindow().setHeight(600);
                gridPane.getScene().getWindow().setWidth(700);
                gridPane.getScene().setRoot(gameLoader.load());
                GameTableController gameTableController = gameLoader.getController();
                Player1 = singlePlayerDialogController.getPlayerName();
                gameTableController.initialize(true, Player1);
            }catch (IOException e){
                e.printStackTrace();
            }

        }

    }
}
