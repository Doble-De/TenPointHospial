package control;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public String csvFile = null; //"src/hospital/data/Hospital.csv";
    @FXML AnchorPane paneDret;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onClickBtnMenu(ActionEvent actionEvent) {
        //System.out.println( ((Button)actionEvent.getSource()).getText());
        String btn = ((JFXButton)actionEvent.getSource()).getId();

        if(btn.equals("btnLlista")) {
           // ListView lsvLlista = new ListView();
            paneDret.getChildren().clear();
            AnchorPane anchorPaneLlista = null;
            try {

                anchorPaneLlista = FXMLLoader.load(getClass().getClassLoader().getResource("llista.fxml"));

            } catch (IOException e) {
                e.printStackTrace();
            }
            paneDret.getChildren().add(anchorPaneLlista);

        }else {
            paneDret.getChildren().clear();
        }


    }



}
