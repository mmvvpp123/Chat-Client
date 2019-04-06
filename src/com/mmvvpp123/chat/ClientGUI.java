package com.mmvvpp123.chat;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.Inet4Address;
import java.net.Socket;
import java.util.logging.SimpleFormatter;


public class ClientGUI extends Application{

    private static Client client;
    public static String userName = "Anonymoose";

    private static Stage window;
    private static Scene scene;

    private static Button send;
    private static Button disconnect;

    public static TextField messaging;
    public static TextArea area;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setResizable(false);
        window.setTitle("Chat Room");

        Label userNameLabel = new Label("Username:  ");
        TextField usernameField = new TextField();
        HBox userChildren = new HBox(20, userNameLabel, usernameField);

        Label ipLabel = new Label ("IP Address: ");
        TextField ipField = new TextField();
        HBox ipChildren = new HBox(20, ipLabel, ipField);

        Button connectToChat = new Button("Connect");

        VBox layout = new VBox(20, userChildren, ipChildren, connectToChat);
        layout.setPadding(new Insets(20));

        Scene openingScene = new Scene(layout, 300, 250);
        connectToChat.setOnAction(e -> connect(usernameField.getText(), ipField.getText()));

        window.setScene(openingScene);
        window.show();
    }

    public void connect(String user, String IPaddress) {
        try {
            userName = user;
            Socket sock = new Socket(IPaddress, 0000);
            System.out.println("You connected to: " + sock.getLocalAddress().getHostName());

            client = new Client(sock);

            Thread x = new Thread(client);
            x.start();

            buildWindow();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Connection");
            alert.setContentText("IP Error! \nPlease enter the correct IP address.");
            alert.show();
        }
    }

    public void buildWindow() {
        send = new Button("Send");
        disconnect = new Button("Disconnect");
        area = new TextArea();
        area.setEditable(false);
        messaging = new TextField();

        HBox buttons = new HBox(20, send, disconnect);

        VBox layout2 = new VBox(20, area, messaging, buttons);
        layout2.setPadding(new Insets(10));
        scene = new Scene(layout2, 550, 300);

        send.setOnAction(e -> client.send(messaging.getText()));
        disconnect.setOnAction(e -> client.disconnect());
        window.setScene(scene);
        window.setTitle(userName + "'s Chat Box");
        window.show();
    }

}
