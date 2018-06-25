package server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServerConnectionHandler implements Runnable {
    private static Socket connection;

    public ServerConnectionHandler(Socket clientSocket) {
        this.connection = clientSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveFile() throws IOException {
        String path = "G:\\repos\\studia\\NettyDropbox\\Server\\";
        String pathPort = path + connection.getPort() + "\\";
        try {
            Files.createDirectory(Paths.get(pathPort));
        } catch (FileAlreadyExistsException e) {
            DataInputStream dis = new DataInputStream(connection.getInputStream());
            String filename = dis.readUTF();

            FileOutputStream outputStream = new FileOutputStream(new File(pathPort + filename));

            int count;
            byte[] buffer = new byte[8192];
            while ((count = dis.read(buffer)) > 0)
                outputStream.write(buffer, 0, count);
        }
    }
}
