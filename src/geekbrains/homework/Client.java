package geekbrains.homework;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        final int PORT = 9090;
        final String SERVER = "localhost";
        Socket socket = null;
        DataInputStream in;
        DataOutputStream out;

        try {
            socket = new Socket(SERVER, PORT);
            System.out.println("Connected");
            Scanner scanner = new Scanner(new InputStreamReader(System.in));
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    while (true) {
                        String clientMessage = scanner.nextLine();
                        out.writeUTF(clientMessage);
                        out.flush();
                        System.out.println("Client: " + clientMessage);

                        if (clientMessage.equals("/end")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            new Thread(() -> {
                try {
                    while (true) {
                        String serverMessage = in.readUTF();
                        System.out.println("Message from server: " + serverMessage);

                        if (serverMessage.equals("/end")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

