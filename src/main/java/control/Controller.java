package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    public String csvFile = null;
    @FXML AnchorPane paneDret;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    @FXML
    public void onClickBtnMenu(ActionEvent actionEvent){
        String btn = ((JFXButton)actionEvent.getSource()).getID();
    }
}
