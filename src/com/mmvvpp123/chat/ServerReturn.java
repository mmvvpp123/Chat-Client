package com.mmvvpp123.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class ServerReturn implements Runnable {

    Socket sock;
    private Scanner input;
    private PrintWriter output;
    String message = "";

    public ServerReturn(Socket s) {
        this.sock = s;
    }

    public void checkConnection() throws IOException {

        if (!sock.isConnected()) {
            for (int i = 1; i < Server.connections.size(); i++) {
                if (Server.connections.get(i) == sock) {
                    Server.connections.remove(i);
                }
            }
            for(int i = 0; i < Server.connections.size(); i++) {
                Socket tempSock = Server.connections.get(i);
                PrintWriter tempOutput = new PrintWriter(tempSock.getOutputStream());
                tempOutput.println(tempSock.getLocalAddress().getHostName() + "disconnected!");
                tempOutput.flush();

                System.out.println(tempSock.getLocalAddress().getHostName() + "disconnected!");
            }
        }
    }

    public void run() {
        try {
            input = new Scanner(sock.getInputStream());
            output = new PrintWriter(sock.getOutputStream());

            while (true) {
                checkConnection();

                if(!input.hasNext()) {
                    return;
                }

                message = input.nextLine();

                System.out.println("Client said: " + message);

                for(int i = 0; i < Server.connections.size(); i++) {
                    Socket tempSock = Server.connections.get(i);
                    PrintWriter tempOutput = new PrintWriter(tempSock.getOutputStream());
                    tempOutput.println(message);
                    tempOutput.flush();
                    System.out.println("Sent to: " + tempSock.getLocalAddress().getHostName());
                }
            }
        }
        catch (Exception e) {

        }
        finally {
            try {
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
