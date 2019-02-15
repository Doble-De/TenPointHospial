import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainFX extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("hospital.fxml"));
        primaryStage.setTitle("Hospital");
        primaryStage.setScene(new Scene(root, 700, 700));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
