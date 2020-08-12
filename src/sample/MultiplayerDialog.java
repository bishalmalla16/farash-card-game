package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;

import java.io.IOException;
import java.net.Socket;

public class MultiplayerDialog {

    @FXML
    Button createServer,joinServer;

    @FXML
    DialogPane multiplayerDialog;

    public void initialize(){
        createServer.setOnAction(e-> create());
        joinServer.setOnAction(e->join());

    }

    public void create(){

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("resources/FXMLs/createServer.fxml"));
        try {
            multiplayerDialog.setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    public void join(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("resources/FXMLs/joinDialog.fxml"));
        try {
            multiplayerDialog.setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }



}
