package com.mmvvpp123.chat;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client implements Runnable {

    Socket sock;
    Scanner input;
    Scanner send = new Scanner(System.in);
    PrintWriter output;

    public Client (Socket s) {
        this.sock = s;
    }

    public void run() {
        try {
            try {
                input = new Scanner(sock.getInputStream());
                output = new PrintWriter(sock.getOutputStream());
                output.flush();
                checkStream();
            }
            finally {
                sock.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {

        try {
            output.println(ClientGUI.userName + ": " + " has disconnected");
            output.flush();
            sock.close();

            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkStream() {
        while (true) {
            receive();
        }
    }

    public void receive () {
        if (input.hasNext()) {
            String message = input.nextLine();

            ClientGUI.area.appendText(message + "\n");
        }
    }

    public void send(String x) {
        output.println(ClientGUI.userName + ": " + x);
        output.flush();
        ClientGUI.messaging.setText("");
    }
}
