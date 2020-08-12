package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SinglePlayerDialogController {

    @FXML
    private TextField playerName;

    public String getPlayerName(){
        return playerName.getText();
    }



}
