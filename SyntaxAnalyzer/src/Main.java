import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("sample/sample.fxml"));
        Pane pane = loader.load();

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Syntax Analyzer");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
