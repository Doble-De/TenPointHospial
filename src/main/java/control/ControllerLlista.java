package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Button;
import model.Hospital;
import model.Pacient;
import model.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerLlista implements Initializable {

    private String csvFile = null;
    private List<Pacient> p = new ArrayList<>();
    private ObservableList<Pacient> data;

    @FXML TableView<Pacient> tablePacients;
    @FXML JFXButton btnLoadFile, delete;
    @FXML JFXTextField txtDNI, txtNom, txtCognoms,edad,maxEdad,altura,maxAltura,peso,maxPeso,edadg,maxEdadg,alturag,maxAlturag,pesog,maxPesog ;
    @FXML JFXCheckBox redad, raltura, rpeso,redadg, ralturag, rpesog;
    @FXML PieChart idPieChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = FXCollections.observableArrayList();
        if(csvFile == null) {
            btnLoadFile.setText("Click per carregar CSV");
        }else {
            setTableView();
        }
        maxEdad.setVisible(false);
        maxAltura.setVisible(false);
        maxPeso.setVisible(false);
        maxEdadg.setVisible(false);
        maxAlturag.setVisible(false);
        maxPesog.setVisible(false);

    }

    private void setTableView() {
        TableColumn DNI = new TableColumn("DNI");
        TableColumn Nom = new TableColumn("Nom");
        TableColumn Cognoms = new TableColumn("Cognoms");
        TableColumn DataNaix = new TableColumn("Data de Naixament");
        TableColumn Genre = new TableColumn("Gènere");
        TableColumn Telefon = new TableColumn("Telèfon");
        TableColumn pes = new TableColumn("Pes");
        TableColumn Alçada = new TableColumn("Alçada");

        // COMPTE!!!! les propietats han de tenir getters i setters
        DNI.setCellValueFactory(new PropertyValueFactory<Pacient, String>("DNI"));
        Nom.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Nom"));
        Cognoms.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Cognoms"));
        DataNaix.setCellValueFactory(new PropertyValueFactory<Pacient, String>("DataNaixament"));
        Genre.setCellValueFactory(new PropertyValueFactory<Pacient, String>("genere"));
        Telefon.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Telefon"));
        pes.setCellValueFactory(new PropertyValueFactory<Pacient, Float>("Pes"));
        Alçada.setCellValueFactory(new PropertyValueFactory<Pacient, Integer>("Alçada"));

        tablePacients.getColumns().addAll(DNI, Nom, Cognoms, DataNaix, Genre, Telefon, pes, Alçada);


        //data.add(new Pacient("111", "n", "co", LocalDate.of(2000, 12, 12), Persona.Genere.HOME, "55555", 5.4f, 100));
        loadData();
        data.addAll(p);
        tablePacients.setItems(data);

    }

    private void loadData() {
        Hospital hospital = new Hospital();
        p.addAll(hospital.loadPacients(csvFile));
    }


    public void clickLoadFile(ActionEvent event) {
        if(csvFile == null) {
            FileChooser fc = new FileChooser();
            fc.setTitle("Select csv file");
            File file = fc.showOpenDialog(null);
            csvFile = file.getAbsolutePath();
            setTableView();
            btnLoadFile.setText("Loaded");
        }else {
            btnLoadFile.setText("File is loaded");
        }
    }

    public void btnCerca(ActionEvent event) {
        List<Pacient> pacients = p.stream()
                .filter(pacient -> pacient.getDNI().contains(txtDNI.getText()))
                .collect(Collectors.toList());
        if(txtDNI.getText().equals("")) {
            updateTable(p);
        }else updateTable(pacients);
    }

    private void updateTable(List<Pacient> pacients) {
        data.clear();
        data.addAll(pacients);
        tablePacients.setItems(data);
    }

    public void changeText(KeyEvent keyEvent) {
        data.clear();
        List<Pacient> pacients = p.stream()
                .filter(pacient -> pacient.getNom().contains(txtNom.getText()))
                .filter((pacient -> pacient.getCognoms().contains(txtCognoms.getText())))
                .collect(Collectors.toList());
        data.addAll(pacients);
        tablePacients.setItems(data);
    }

    public void calcularEdad(ActionEvent event) {
        List<Pacient> pacients = p.stream()
                .filter(pacient ->  pacient.getEdat() == Integer.parseInt(edad.getText()))
                .collect(Collectors.toList());
        if(!redad.isSelected()){
            updateTable(pacients);
        }else {
            if(Integer.parseInt(edad.getText())<=Integer.parseInt(maxEdad.getText())){
                pacients = p.stream()
                        .filter(pacient ->  pacient.getEdat() >= Integer.parseInt(edad.getText()))
                        .filter(pacient ->  pacient.getEdat() <= Integer.parseInt(maxEdad.getText()))
                        .collect(Collectors.toList());
                updateTable(pacients);
            }
        }
    }

    public void calcularAltura(ActionEvent event) {
        List<Pacient> pacients = p.stream()
                .filter(pacient ->  pacient.getAlçada() == Integer.parseInt(altura.getText()))
                .collect(Collectors.toList());
        updateTable(pacients);
        if(!raltura.isSelected()){
            updateTable(pacients);
        }else {
            if(Integer.parseInt(altura.getText())<=Integer.parseInt(maxAltura.getText())){
                pacients = p.stream()
                        .filter(pacient ->  pacient.getAlçada() >= Integer.parseInt(altura.getText()))
                        .filter(pacient ->  pacient.getAlçada() <= Integer.parseInt(maxAltura.getText()))
                        .collect(Collectors.toList());
                updateTable(pacients);
            }
        }
    }

    public void calcularPeso(ActionEvent event) {
        List<Pacient> pacients = p.stream()
                .filter(pacient ->  pacient.getPes() == Float.parseFloat(peso.getText()))
                .collect(Collectors.toList());
        updateTable(pacients);
        if(!rpeso.isSelected()){
            updateTable(pacients);
        }else {
            if(Float.parseFloat(peso.getText())<=Float.parseFloat(maxPeso.getText())){
                pacients = p.stream()
                        .filter(pacient ->  pacient.getPes() >= Float.parseFloat(peso.getText()))
                        .filter(pacient ->  pacient.getPes() <= Float.parseFloat(maxPeso.getText()))
                        .collect(Collectors.toList());
                updateTable(pacients);
            }
        }
    }


    public void clickTable(MouseEvent event) {
        //Cal verificar si hi ha alguna selecció feta al fer doble click
        if (event.getClickCount() == 2 && !tablePacients.getSelectionModel().isEmpty()){
            System.out.println(tablePacients.getSelectionModel().getSelectedItem().getNom());
        }
    }

    /**
     * Exemple de PieChart
     * Quants pacients homes i quants pacients dones hi ha
     * @param event
     */
    public void btnChart(ActionEvent event) {

        idPieChart.getData().clear();
            long total = p.stream()
                    .filter(pacient -> pacient.getEdat() >= Integer.parseInt(edadg.getText()) && pacient.getEdat() <= Integer.parseInt(maxEdadg.getText()))
                    .count();
            long resto = p.stream()
                    .filter(pacient -> pacient.getEdat() < Integer.parseInt(edadg.getText()) && pacient.getEdat() > Integer.parseInt(maxEdadg.getText()))
                    .count();
            idPieChart.setTitle("Edad");
            idPieChart.getData().add(new PieChart.Data("Total: " + total, total));
            idPieChart.getData().add(new PieChart.Data("Resto: " + resto, resto));


//        idPieChart.getData().clear();
//
//            long dones = p.stream()
//                    .filter(pacient -> pacient.getGenere() == Persona.Genere.DONA)
//                    .count();
//            long homes = p.stream()
//                    .filter(pacient -> pacient.getGenere() == Persona.Genere.HOME)
//                    .count();
//            idPieChart.setTitle("Gènere");
//            idPieChart.getData().add(new PieChart.Data(Persona.Genere.DONA.toString(), dones));
//            idPieChart.getData().add(new PieChart.Data(Persona.Genere.HOME.toString(), homes));

    }

    public void mostrarRangog(ActionEvent evet) {

        if (redadg.isSelected()){
            maxEdadg.setVisible(true);
        }else {
            maxEdadg.setVisible(false);
            maxEdadg.setText("");
        }

        if (ralturag.isSelected()){
            maxAlturag.setVisible(true);
        }else {
            maxAlturag.setVisible(false);
            maxAlturag.setText("");
        }

        if (rpesog.isSelected()){
            maxPesog.setVisible(true);
        }else {
            maxPesog.setVisible(false);
            maxPesog.setText("");
        }
    }
    public void mostrarRango(ActionEvent evet) {

        if (redad.isSelected()){
            maxEdad.setVisible(true);
        }else {
            maxEdad.setVisible(false);
            maxEdad.setText("");
        }

        if (raltura.isSelected()){
            maxAltura.setVisible(true);
        }else {
            maxAltura.setVisible(false);
            maxAltura.setText("");
        }

        if (rpeso.isSelected()){
            maxPeso.setVisible(true);
        }else {
            maxPeso.setVisible(false);
            maxPeso.setText("");
        }
    }

    public void macroMetodo(ActionEvent event) {
        if(!txtDNI.getText().equals("")){
            btnCerca(event);
        }else if (!altura.getText().equals("")){
            calcularAltura(event);
        }else if (!edad.getText().equals("")){
            calcularEdad(event);
        }else if(!peso.getText().equals("")){
            calcularPeso(event);

        }else {
            List<Pacient> pacients = new ArrayList<>(p);
            updateTable(pacients);
        }

    }


}
