package com.mmvvpp123.chat;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {

    public static ArrayList<Socket> connections = new ArrayList<>();
    public static ArrayList<String> users = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        try {
            ServerSocket serverSocket = new ServerSocket(0000);
            System.out.println("Waiting for connections..");

            while(true) {
                Socket sock = serverSocket.accept();
                connections.add(sock);

                System.out.println("Connection from: " + sock.getLocalAddress().getHostName());
                addUserName(sock);

                ServerReturn chat = new ServerReturn(sock);
                Thread x = new Thread(chat);
                x.start();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUserName(Socket s) throws IOException {
        //Scanner input = new Scanner(s.getInputStream());
        //String userName = input.nextLine();
        //users.add(userName);

        for(int i = 1; i < Server.connections.size(); i++) {
            Socket tempSock = Server.connections.get(i);
            PrintWriter output = new PrintWriter(tempSock.getOutputStream());
            output.println("//" + users);
            output.flush();
        }
    }
}
