package com.company;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        ServerSocket server_socket;
        Socket client_socket;
        try {
            server_socket = new ServerSocket(6000);
            client_socket = server_socket.accept();
            System.out.println("Zglosil sie klient");
        }
        catch (Exception e) {
            System.err.println( e.getMessage() );
            e.printStackTrace();
        }

    }
}
