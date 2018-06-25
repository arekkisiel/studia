import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server{
    private static Socket connection;


    public static void main(String[] args) throws IOException {
        String path = "G:\\repos\\studia\\NettyDropbox\\Server\\";
        ServerSocket serverSocket = new ServerSocket(8000);
        while(true){
            connection = serverSocket.accept();
            saveFile();
        }
    }

    private static void saveFile() throws IOException {
        String path = "G:\\repos\\studia\\NettyDropbox\\Server\\";
        String pathPort = path + connection.getPort() + "\\";
        try {
            Files.createDirectory(Paths.get(pathPort));
        } catch (FileAlreadyExistsException e) {
            Paths.get(pathPort);
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