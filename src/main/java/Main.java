import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getClassLoader().getResource("window.fxml"));
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("maven javafx");
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);

    }
}
