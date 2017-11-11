import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/MainWindow.fxml"));
        Pane pane = loader.load();

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("NFA - Series Analyzer");
        primaryStage.show();
    }

}
