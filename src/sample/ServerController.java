package sample;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {

    public static int n;
    public static Socket[] socket = new Socket[3];
    public static ServerSocket serverSocket;
    public static String[] players = new String[]{"BServer", "CPU 1", "CPU 2", "CPU 3"} ;

    @FXML
    Button startServer,startGame;
    @FXML
    ListView<String> playersList;
    @FXML
    TextField numberOfPlayers;
    @FXML
    ProgressBar progressBar;

    public void initialize(){
        startGame.setVisible(false);
        startServer.setOnAction(e->{
            startServer.setVisible(false);
            startGame.setVisible(true);
            start();
        });
        progressBar.setVisible(false);
    }

    private void start(){
        try {
            try{
                if (serverSocket != null)
                    serverSocket.close();
                Platform.runLater(()->playersList.getItems().setAll());
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
            n = Integer.parseInt(numberOfPlayers.getText());

            if(n<1 || n>3) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid number of Players. Enter between 1 and 3");
                alert.showAndWait();
                return;
            }

            Service service = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected String call() {
                                try {
                                    serverSocket = new ServerSocket(8086);
                                    int i=0;
                                    while (i<n){
                                        socket[i] = serverSocket.accept();
                                        BufferedReader bf = new BufferedReader(new InputStreamReader(socket[i].getInputStream()));
                                        String name =  bf.readLine();
                                        players[i+1] = name;
                                        Platform.runLater(()->playersList.getItems().add(name +" connected"));
                                        updateProgress(i+1, n);
                                        i++;
                                    }
                                }catch (IOException e){
                                    System.out.println(e.getMessage());
                                }
                                startGame.setOnAction(e->multiplayer());

                            return null;
                        }
                    };
                }
            };
            progressBar.setVisible(true);
            progressBar.progressProperty().bind(service.progressProperty());
            service.start();


        }catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void multiplayer(){
        FXMLLoader gameLoader = new FXMLLoader();
        gameLoader.setLocation(getClass().getResource("resources/FXMLs/GameTable.fxml"));
        try {
            startServer.getScene().getWindow().setHeight(600);
            startServer.getScene().getWindow().setWidth(700);
            startServer.getScene().setRoot(gameLoader.load());
            GameTableController gameTableController = gameLoader.getController();
            gameTableController.initialize(players);
        }catch (IOException e){
            e.printStackTrace();
        }

    }


}
