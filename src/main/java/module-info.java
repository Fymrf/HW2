module work.hw26 {
    requires javafx.controls;
    requires javafx.fxml;


    opens work.hw26 to javafx.fxml;
    exports work.hw26;
}