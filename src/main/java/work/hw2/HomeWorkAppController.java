package work.hw2;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HomeWorkAppController {
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;

    @FXML
    private void send() {
        sending();
    }

    @FXML
    public void initialize() {
        textField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                sending();
            }
        });
    }

    private void sending() {
        if (!textField.getText().trim().isBlank()) {
            textArea.appendText(textField.getText() + "\n");
            textField.clear();
        }
    }
}