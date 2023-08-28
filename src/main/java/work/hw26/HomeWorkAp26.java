package work.hw26;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class HomeWorkAp26 extends Application {
    static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;

        primaryStage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(Objects.requireNonNull(HomeWorkAp26.class.getResource("appView.fxml")));
        primaryStage.setScene(new Scene(root, 420, 620));
        primaryStage.show();
    }
}