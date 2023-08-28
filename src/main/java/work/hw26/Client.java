package work.hw26;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    static boolean continueRead = true;
    private final String SERVER_IP = "localhost";
    private final int SERVER_PORT = 8189;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    public static boolean tern = true;

    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;

    @FXML
    public void initialize() throws IOException {
        textField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                send();
            }
        });
        try {
            openConnection();
            addCloseListener();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка подключения");
            alert.setHeaderText("Сервер не работает");
            alert.setContentText("Не забудь включить сервер!");
            alert.showAndWait();
            e.printStackTrace();
            throw e;
        }
    }

    private void openConnection() throws IOException {
        new Thread(() -> {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                chat();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }).start();
    }

    @FXML
    private void chat() throws IOException {

        try {
            while (continueRead) {
                String strFromServer = in.readUTF();
                textArea.setText(textArea.getText() + "Сервер: " + strFromServer + "\n");
                if (strFromServer.equals("Очередь клиента")) {
                    tern = true;
                    send();
                }
            }
        } catch (Exception e) {
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

    }

    private void addCloseListener() {
        EventHandler<WindowEvent> onCloseRequest = HomeWorkAp26.mainStage.getOnCloseRequest();
        HomeWorkAp26.mainStage.setOnCloseRequest(event -> {
            closeConnection();
            if (onCloseRequest != null) {
                onCloseRequest.handle(event);
            }
        });
    }

    private void closeConnection() {
        try {
            continueRead = false;
            out.writeUTF("/end");
            socket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send() {
        new Thread(() -> {
            if (!textField.getText().trim().isEmpty()) {
                try {
                    if (textField.getText().equalsIgnoreCase("!end")) {
                        textArea.setText(textArea.getText() + "Сервер: " + "Очередь сервера" + "\n");
                        tern = false;
                        out.writeUTF(textField.getText().trim());
                    }
                    if (tern) {
                        out.writeUTF(textField.getText().trim());
                        textArea.setText(textArea.getText() + "Клиент: " + textField.getText().trim() + "\n");
                    }
                    out.flush();
                    textField.clear();
                    textField.requestFocus();
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Ошибка отправки сообщения");
                    alert.setHeaderText("Ошибка отправки сообщения");
                    alert.setContentText("При отправке сообщения возникла ошибка: " + e.getMessage());
                    alert.show();
                }
            }
        }).start();
    }
}

