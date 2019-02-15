import model.Hospital;
import model.Pacient;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String csvFile = "src/main/java/data/Hospital.csv";

    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        List<Pacient> p = new ArrayList<>();

       p.addAll(hospital.loadPacients(csvFile));


    }

}
