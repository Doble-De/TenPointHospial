package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.opencsv.CSVReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ControllerLlista implements Initializable {

    private String csvFile = null;
    private List<Pacient> p = new ArrayList<>();
    private ObservableList<Pacient> data;

    @FXML TableView<Pacient> tablePacients;
    @FXML JFXButton btnLoadFile;
    @FXML JFXTextField txtDNI, txtNom, txtCognoms;
    @FXML PieChart idPieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        if (csvFile == null) {
            btnLoadFile.setText("Click per carregar CSV");
        } else {
            setTableView();
        }
    }

    private void setTableView(){
        TableColumn DNI = new TableColumn("DNI");
        TableColumn Nom = new TableColumn("Nom");
        TableColumn Cognoms = new TableColumn("Cognoms");
        TableColumn DataNaix = new TableColumn("Data de Naixament");
        TableColumn Genre = new TableColumn("Gènere");
        TableColumn Telefon = new TableColumn("Telèfon");
        TableColumn pes = new TableColumn("Pes");
        TableColumn Alçada = new TableColumn("Alçada");

        tablePacients.getColumns().addAll(DNI, Nom, Cognoms, DataNaix, Genre, Telefon, pes, Alçada);
    }
}
