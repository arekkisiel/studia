package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        startServer(args);
        connectClient(args);
    }

    private static void connectClient(String[] args) {
        new Thread(() -> {
            try {
                Client.main("G:\\repos\\studia\\MyDropbox\\Clients\\");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void startServer(String[] args) {
        new Thread(() -> {
            try {
                Server.main("G:\\repos\\studia\\MyDropbox\\Server\\");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
