package sample;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientController {
    @FXML
    TextField address,name;
    @FXML
    Text result;
    @FXML
    Button joinGame;
    private static String player, addr;
    public static Socket socket;
    private static Window window;

    public void initialize(){
        joinGame.setOnAction(e-> initJoin());
    }

    public void initJoin(){
        player = name.getText();
        addr = address.getText();
        Service<String> service = new Service<String>() {
            @Override
            protected Task<String> createTask() {
                return new Task<String>() {
                    @Override
                    protected String call() throws Exception {
                        try{
                            socket = new Socket(addr, 8086);
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                            printWriter.println(player);
                            while (true) {
                                setGame(bufferedReader.readLine(), bufferedReader.readLine(), bufferedReader.readLine());
                            }
                        }catch (IOException e){
                            System.out.println(e.getMessage());
                        }
                        return null;
                    }
                };
            }
        };
        service.start();
        joinGame.setDisable(true);
        result.setText("Waiting for other players!");
    }

    private void setGame( String cardList, String playersList, String winner){

        try {
            Window pre = window;
            window = result.getScene().getWindow();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        String players[] = playersList.split(",");
        String cards[] = cardList.split(",");
        String playerCardsForTable[][] = new String[4][3];

        for(int i=0; i<players.length; i++){
            for(int j=0; j<3;j++){
                playerCardsForTable[i][j] = cards[3*i+j];
            }
        }
        FXMLLoader gameLoader = new FXMLLoader();
        gameLoader.setLocation(getClass().getResource("resources/FXMLs/GameTable.fxml"));
        try {
            window.setHeight(600);
            window.setWidth(700);
            window.getScene().setRoot(gameLoader.load());
            GameTableController gameTableController = gameLoader.getController();
            gameTableController.initialize(playerCardsForTable, players, winner);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}

