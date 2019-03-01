package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.*;
import model.Hospital;
import model.Pacient;
import model.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerLlista implements Initializable {

    private String csvFile = null;
    private List<Pacient> p = new ArrayList<>();
    private List<Pacient> esp = new ArrayList<>();
    private ObservableList<Pacient> data, espera;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML TableView<Pacient> tablePacients, llistaespera;
    @FXML JFXButton btnLoadFile, delete;
    @FXML JFXTextField txtDNI, txtNom, txtCognoms,edad,maxEdad,altura,maxAltura,peso,maxPeso,edadg,maxEdadg,alturag,maxAlturag,pesog,maxPesog ;
    @FXML JFXCheckBox redad, raltura, rpeso,redadg, ralturag, rpesog;
    @FXML PieChart idPieChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = FXCollections.observableArrayList();
        espera = FXCollections.observableArrayList();
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

    private void updateData() {
        Hospital hospital = new Hospital();
        esp.addAll(hospital.llistaEsperaUpdate("./src/main/java/data/llistaespera.csv"));
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


    public void clickTable(MouseEvent event) throws IOException {
        //Cal verificar si hi ha alguna selecció feta al fer doble click
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert update = new Alert(Alert.AlertType.INFORMATION);

        if (event.getClickCount() == 2 && !tablePacients.getSelectionModel().isEmpty()){
            System.out.println(tablePacients.getSelectionModel().getSelectedItem().getNom());
            alert.setTitle("Lista de espera");
            alert.setHeaderText("¿Quieres añadir a esta persona a la lista de espera?");
            alert.setContentText("Datos del paciente:\nNombre: "+tablePacients.getSelectionModel().getSelectedItem().getNom()+
                    "\nApellidos: "+tablePacients.getSelectionModel().getSelectedItem().getCognoms()+
                    "\nFecha de nacimiento: "+tablePacients.getSelectionModel().getSelectedItem().getDataNaixament()+
                    "\nGenero: "+tablePacients.getSelectionModel().getSelectedItem().getGenere()+
                    "\nTelefono: "+tablePacients.getSelectionModel().getSelectedItem().getTelefon());

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                FileWriter fw= new FileWriter("./src/main/java/data/llistaespera.csv", true);
                BufferedWriter bw = new BufferedWriter(fw);
//                bw.newLine();
                bw.write(tablePacients.getSelectionModel().getSelectedItem().getDNI() + "," + tablePacients.getSelectionModel().getSelectedItem().getNom()+ "," +tablePacients.getSelectionModel().getSelectedItem().getCognoms()+ "," +tablePacients.getSelectionModel().getSelectedItem().getDataNaixament().format(formatter)+ "," +tablePacients.getSelectionModel().getSelectedItem().getGenere()+ "," +tablePacients.getSelectionModel().getSelectedItem().getTelefon()+ "," +tablePacients.getSelectionModel().getSelectedItem().getPes()+","+tablePacients.getSelectionModel().getSelectedItem().getAlçada() + "\n");
                bw.close();

                update.setTitle("Lista de espera");
                update.setHeaderText("Lista de espera actualizada");
                update.setContentText(" ");
                Optional<ButtonType> actializar = update.showAndWait();

                if (actializar.get() == ButtonType.OK){

                    espera.clear();
                    esp.clear();
                    TableColumn DNIe = new TableColumn("DNI");
                    TableColumn Nome = new TableColumn("Nom");
                    TableColumn Cognomse = new TableColumn("Cognoms");
                    TableColumn DataNaixe = new TableColumn("Data de Naixament");
                    TableColumn Genree = new TableColumn("Gènere");
                    TableColumn Telefone = new TableColumn("Telèfon");
                    TableColumn pese = new TableColumn("Pes");
                    TableColumn Alçadae = new TableColumn("Alçada");

                    // COMPTE!!!! les propietats han de tenir getters i setters
                    DNIe.setCellValueFactory(new PropertyValueFactory<Pacient, String>("DNI"));
                    Nome.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Nom"));
                    Cognomse.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Cognoms"));
                    DataNaixe.setCellValueFactory(new PropertyValueFactory<Pacient, String>("DataNaixament"));
                    Genree.setCellValueFactory(new PropertyValueFactory<Pacient, String>("genere"));
                    Telefone.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Telefon"));
                    pese.setCellValueFactory(new PropertyValueFactory<Pacient, Float>("Pes"));
                    Alçadae.setCellValueFactory(new PropertyValueFactory<Pacient, Integer>("Alçada"));

                    llistaespera.getColumns().addAll(DNIe, Nome, Cognomse, DataNaixe, Genree, Telefone, pese, Alçadae);

                    updateData();
                    espera.addAll(esp);
                    llistaespera.setItems(espera);

                 }else if (result.get() == ButtonType.CANCEL){

            }
            }else if (result.get() == ButtonType.CANCEL){

            }
        }
    }


    public void deleteTable(MouseEvent event) throws IOException {
        //Cal verificar si hi ha alguna selecció feta al fer doble click
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert update = new Alert(Alert.AlertType.INFORMATION);


        if (event.getClickCount() == 1 && !llistaespera.getSelectionModel().isEmpty()){
            System.out.println(llistaespera.getSelectionModel().getSelectedItem().getNom());
            alert.setTitle("Lista de espera");
            alert.setHeaderText("¿Quieres eliminar a esta persona a la lista de espera?");
            alert.setContentText("Datos del paciente:\nNombre: "+llistaespera.getSelectionModel().getSelectedItem().getNom()+
                    "\nApellidos: "+llistaespera.getSelectionModel().getSelectedItem().getCognoms()+
                    "\nFecha de nacimiento: "+llistaespera.getSelectionModel().getSelectedItem().getDataNaixament()+
                    "\nGenero: "+llistaespera.getSelectionModel().getSelectedItem().getGenere()+
                    "\nTelefono: "+llistaespera.getSelectionModel().getSelectedItem().getTelefon());

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
//                bw.newLine();

                File file = new File ("./src/main/java/data/llistaespera.csv");
                BufferedReader br = new BufferedReader(new FileReader(file));

                File file2 = new File ("./src/main/java/data/llistaespera.csv");
                BufferedWriter bw = new BufferedWriter(new FileWriter(file2));

//                File file2 = new File ("./src/main/java/data/temp.csv");
//                BufferedWriter bw = new BufferedWriter(new FileWriter(file2));

                String line = tablePacients.getSelectionModel().getSelectedItem().getDNI() + "," + tablePacients.getSelectionModel().getSelectedItem().getNom()+ "," +tablePacients.getSelectionModel().getSelectedItem().getCognoms()+ "," +tablePacients.getSelectionModel().getSelectedItem().getDataNaixament().format(formatter)+ "," +tablePacients.getSelectionModel().getSelectedItem().getGenere()+ "," +tablePacients.getSelectionModel().getSelectedItem().getTelefon()+ "," +tablePacients.getSelectionModel().getSelectedItem().getPes()+","+tablePacients.getSelectionModel().getSelectedItem().getAlçada();
                String linea = br.readLine();

               while (linea != null) {

                   if (!linea.equals(line)) {
                       bw.write(br.readLine());
                       System.out.println(linea);
                       System.out.println(line);
//                   }else {

                   }
                   linea = br.readLine();
               }


                br.close();
                bw.close();

                //boolean delete = file.delete();
                //boolean b = file2.renameTo(file);


                update.setTitle("Lista de espera");
                update.setHeaderText("Lista de espera actualizada");
                update.setContentText(" ");
                Optional<ButtonType> actializar = update.showAndWait();

                if (actializar.get() == ButtonType.OK){
                    //copiarArchivo();
                    espera.clear();
                    esp.clear();

                    updateData();
                    espera.addAll(esp);
                    llistaespera.setItems(espera);

                }else if (result.get() == ButtonType.CANCEL){

                }
            }else if (result.get() == ButtonType.CANCEL){

            }
        }
    }

    public void copiarArchivo() throws IOException {


        File file = new File ("./src/main/java/data/temp.csv");
        BufferedReader br = new BufferedReader(new FileReader(file));

        File file2 = new File ("./src/main/java/data/llistaespera.csv");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file2));


        String lineaa = br.readLine();

        while (lineaa != null) {

            bw.write(lineaa+"\n");
            lineaa = br.readLine();
        }
    }

    /**
     * Exemple de PieChart
     * Quants pacients homes i quants pacients dones hi ha
     * @param event
     */
    public void btnChart(ActionEvent event) {

        if (redadg.isSelected()) {

            idPieChart.getData().clear();
            long total = p.stream()
                    .filter(pacient -> pacient.getEdat() >= Integer.parseInt(edadg.getText()) && pacient.getEdat() <= Integer.parseInt(maxEdadg.getText()))
                    .count();
            long resto = p.stream()
                    .filter(pacient -> pacient.getEdat() < Integer.parseInt(edadg.getText()) || pacient.getEdat() > Integer.parseInt(maxEdadg.getText()))
                    .count();
            idPieChart.setTitle("Edad");
            idPieChart.getData().add(new PieChart.Data("Total: " + total, total));
            idPieChart.getData().add(new PieChart.Data("Resto: " + resto, resto));
        } else {
            idPieChart.getData().clear();
            long total = p.stream()
                    .filter(pacient -> pacient.getEdat() == Integer.parseInt(edadg.getText()))
                    .count();
            long resto = p.stream()
                    .filter(pacient -> pacient.getEdat() < Integer.parseInt(edadg.getText()) || pacient.getEdat() > Integer.parseInt(edadg.getText()))
                    .count();
            idPieChart.setTitle("Edad");
            idPieChart.getData().add(new PieChart.Data("Total: " + total, total));
            idPieChart.getData().add(new PieChart.Data("Resto: " + resto, resto));

        }
    }

    public void alturaChart(ActionEvent event) {

        if(ralturag.isSelected()) {
            idPieChart.getData().clear();
            long total = p.stream()
                    .filter(pacient -> pacient.getAlçada() >= Integer.parseInt(alturag.getText()) && pacient.getAlçada() <= Integer.parseInt(maxAlturag.getText()))
                    .count();
            long resto = p.stream()
                    .filter(pacient -> pacient.getAlçada() < Integer.parseInt(alturag.getText()) || pacient.getAlçada() > Integer.parseInt(maxAlturag.getText()))
                    .count();
            idPieChart.setTitle("Altura");
            idPieChart.getData().add(new PieChart.Data("Total: " + total, total));
            idPieChart.getData().add(new PieChart.Data("Resto: " + resto, resto));
        }else {

            idPieChart.getData().clear();
            long total = p.stream()
                    .filter(pacient -> pacient.getAlçada() == Integer.parseInt(alturag.getText()))
                    .count();
            long resto = p.stream()
                    .filter(pacient -> pacient.getAlçada() < Integer.parseInt(alturag.getText()) || pacient.getAlçada() > Integer.parseInt(alturag.getText()))
                    .count();
            idPieChart.setTitle("Altura");
            idPieChart.getData().add(new PieChart.Data("Total: " + total, total));
            idPieChart.getData().add(new PieChart.Data("Resto: " + resto, resto));

        }
    }

    public void pesoChart(ActionEvent event) {

        if(rpesog.isSelected()) {
            idPieChart.getData().clear();
            long total = p.stream()
                    .filter(pacient -> pacient.getPes() >= Float.parseFloat(pesog.getText()) && pacient.getPes() <= Float.parseFloat(maxPesog.getText()))
                    .count();
            long resto = p.stream()
                    .filter(pacient -> pacient.getPes() < Float.parseFloat(pesog.getText()) || pacient.getPes() > Float.parseFloat(maxPesog.getText()))
                    .count();
            idPieChart.setTitle("Peso");
            idPieChart.getData().add(new PieChart.Data("Total: " + total, total));
            idPieChart.getData().add(new PieChart.Data("Resto: " + resto, resto));
        }else {
            idPieChart.getData().clear();
            long total = p.stream()
                    .filter(pacient -> pacient.getPes() == Float.parseFloat(pesog.getText()))
                    .count();
            long resto = p.stream()
                    .filter(pacient -> pacient.getPes() < Float.parseFloat(pesog.getText()) || pacient.getPes() > Float.parseFloat(pesog.getText()))
                    .count();
            idPieChart.setTitle("Peso");
            idPieChart.getData().add(new PieChart.Data("Total: " + total, total));
            idPieChart.getData().add(new PieChart.Data("Resto: " + resto, resto));
        }
    }

    public void macroMetodo(ActionEvent event) {
        if(!edad.getText().equals("") && !altura.getText().equals("") && !peso.getText().equals("") ) {
            if(rpeso.isSelected() && redad.isSelected() && raltura.isSelected()) {
                List<Pacient> pacients = p.stream()
                        .filter(pacient -> pacient.getPes() >= Float.parseFloat(peso.getText()) && pacient.getPes() <= Float.parseFloat(maxPeso.getText()))
                        .filter(pacient -> pacient.getAlçada() >= Integer.parseInt(altura.getText()) && pacient.getAlçada() <= Integer.parseInt(maxAltura.getText()))
                        .filter(pacient -> pacient.getEdat() >= Integer.parseInt(edad.getText()) && pacient.getEdat() <= Integer.parseInt(maxEdad.getText()))
                        .collect(Collectors.toList());
                updateTable(pacients);
            }else {
                idPieChart.getData().clear();
                List<Pacient> pacients = p.stream()
                        .filter(pacient -> pacient.getPes() == Float.parseFloat(peso.getText()))
                        .filter(pacient -> pacient.getAlçada() == Integer.parseInt(altura.getText()))
                        .filter(pacient -> pacient.getEdat() == Integer.parseInt(edad.getText()))
                        .collect(Collectors.toList());
                updateTable(pacients);
            }
        }
        else if(!txtDNI.getText().equals("")) {
            btnCerca(event);
        }else if(!edad.getText().equals("") && !altura.getText().equals("")){
            if( redad.isSelected() && raltura.isSelected()) {
                data.clear();
                List<Pacient> pacients = p.stream()

                        .filter(pacient -> pacient.getAlçada() >= Integer.parseInt(altura.getText()) && pacient.getAlçada() <= Integer.parseInt(maxAltura.getText()))
                        .filter(pacient -> pacient.getEdat() >= Integer.parseInt(edad.getText()) && pacient.getEdat() <= Integer.parseInt(maxEdad.getText()))
                        .collect(Collectors.toList());
                updateTable(pacients);
            }else {
                List<Pacient> pacients = p.stream()
                        .filter(pacient -> pacient.getAlçada() == Integer.parseInt(altura.getText()))
                        .filter(pacient -> pacient.getEdat() == Integer.parseInt(edad.getText()))
                        .collect(Collectors.toList());
                updateTable(pacients);
            }
        }else  if(!altura.getText().equals("") && !peso.getText().equals("")){
            if(rpeso.isSelected()&& raltura.isSelected()) {
                List<Pacient> pacients = p.stream()
                        .filter(pacient -> pacient.getPes() >= Float.parseFloat(peso.getText()) && pacient.getPes() <= Float.parseFloat(maxPeso.getText()))
                        .filter(pacient -> pacient.getAlçada() >= Integer.parseInt(altura.getText()) && pacient.getAlçada() <= Integer.parseInt(maxAltura.getText()))
                        .collect(Collectors.toList());
                updateTable(pacients);
            }else {
                idPieChart.getData().clear();
                List<Pacient> pacients = p.stream()
                        .filter(pacient -> pacient.getPes() == Float.parseFloat(peso.getText()))
                        .filter(pacient -> pacient.getAlçada() == Integer.parseInt(altura.getText()))
                        .collect(Collectors.toList());
                updateTable(pacients);
            }

        }else if(!edad.getText().equals("") && !peso.getText().equals("")) {
            if(rpeso.isSelected() && redad.isSelected()) {
                List<Pacient> pacients = p.stream()
                        .filter(pacient -> pacient.getPes() >= Float.parseFloat(peso.getText()) && pacient.getPes() <= Float.parseFloat(maxPeso.getText()))
                        .filter(pacient -> pacient.getEdat() >= Integer.parseInt(edad.getText()) && pacient.getEdat() <= Integer.parseInt(maxEdad.getText()))
                        .collect(Collectors.toList());
                updateTable(pacients);
            }else {
                idPieChart.getData().clear();
                List<Pacient> pacients = p.stream()
                        .filter(pacient -> pacient.getPes() == Float.parseFloat(peso.getText()))
                        .filter(pacient -> pacient.getEdat() == Integer.parseInt(edad.getText()))
                        .collect(Collectors.toList());
                updateTable(pacients);
            }
        }else if (!txtDNI.getText().equals("")){
            btnCerca(event);
        }else if (!edad.getText().equals("")){
            calcularEdad(event);
        }else if(!peso.getText().equals("")) {
            calcularPeso(event);
        }else if(!altura.getText().equals("")){
            calcularAltura(event);

        }else {
            List<Pacient> pacients = new ArrayList<>(p);
            updateTable(pacients);
        }

    }

    public void macroMetodoGrafico(ActionEvent event){
        if(!edadg.getText().equals("") && !alturag.getText().equals("") && !pesog.getText().equals("") ){
            if(rpesog.isSelected() && redadg.isSelected() && ralturag.isSelected()) {
                idPieChart.getData().clear();
                long total = p.stream()
                        .filter(pacient -> pacient.getPes() >= Float.parseFloat(pesog.getText()) && pacient.getPes() <= Float.parseFloat(maxPesog.getText()))
                        .filter(pacient -> pacient.getAlçada() >= Integer.parseInt(alturag.getText()) && pacient.getAlçada() <= Integer.parseInt(maxAlturag.getText()))
                        .filter(pacient -> pacient.getEdat() >= Integer.parseInt(edadg.getText()) && pacient.getEdat() <= Integer.parseInt(maxEdadg.getText()))
                        .count();
                long resto = p.stream()
                        .count()-total;
                idPieChart.setTitle("Peso");
                idPieChart.getData().add(new PieChart.Data("Total: " + total, total));
                idPieChart.getData().add(new PieChart.Data("Resto: " + resto, resto));
            }else {
                idPieChart.getData().clear();
                long total = p.stream()
                        .filter(pacient -> pacient.getPes() == Float.parseFloat(pesog.getText()))
                        .filter(pacient -> pacient.getAlçada() == Integer.parseInt(alturag.getText()))
                        .filter(pacient -> pacient.getEdat() == Integer.parseInt(edadg.getText()))
                        .count();
                long resto = p.stream()
                        .count() - total;
                idPieChart.getData().add(new PieChart.Data("Total: " + total, total));
                idPieChart.getData().add(new PieChart.Data("Resto: " + resto, resto));
            }
        }else if(!edadg.getText().equals("") && !alturag.getText().equals("")){
            btnChart(event);
            alturaChart(event);
        }else  if(!alturag.getText().equals("") && !pesog.getText().equals("")){
            alturaChart(event);
            pesoChart(event);
        }else if(!edadg.getText().equals("") && !pesog.getText().equals("")){
            btnChart(event);
            pesoChart(event);
        }else if(!edadg.getText().equals("") && pesog.getText().equals("") && alturag.getText().equals("")){
            btnChart(event);
        }else if(edadg.getText().equals("") && !pesog.getText().equals("") && alturag.getText().equals("")){
            pesoChart(event);
        }else if(edadg.getText().equals("") && pesog.getText().equals("") && !alturag.getText().equals("")) {
            alturaChart(event);
        }
        else {
            List<Pacient> pacients = new ArrayList<>(p);
            updateTable(pacients);
        }
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




}
