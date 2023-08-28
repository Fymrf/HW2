package work.hw26;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static DataInputStream in;
    private static DataOutputStream out;
    private static Socket socket;
    public static boolean tern = false;
    static boolean continueRead = true;

    public static void main(String[] args) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(8189)) {
                socket = serverSocket.accept();

                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                while (continueRead) {
                    String str = in.readUTF();
                    System.out.println("Клиент: " + str);
                    if (str.equals("!end")) {
                        Client.tern = false;
                        tern = true;
                        sendMessage();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void sendMessage() throws IOException {
        new Thread(() -> {
            try {
                while (true) {
                    Scanner scanner = new Scanner(System.in);
                    String message = scanner.nextLine();
                    if (message.equals("!end")) {
                        tern = false;
                        out.writeUTF("Очередь клиента");
                        System.out.println("Очередь клиента");
                    }
                    if (tern) {
                        out.writeUTF(message);
                        out.flush();
                    }
                }

            } catch (
                    IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
