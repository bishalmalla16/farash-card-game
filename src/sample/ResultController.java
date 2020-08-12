package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultController {
    @FXML
    Text winText;
//    @FXML
//    Button playAgain, home;


    public void initialize(String winText){
        this.winText.setText(winText);
//        home.setOnAction(e->goHome());

    }

//    public void goHome(){
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(getClass().getResource("resources/FXMLs/sample.fxml"));
//            home.getParent().getScene().setRoot(fxmlLoader.load());
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
}
