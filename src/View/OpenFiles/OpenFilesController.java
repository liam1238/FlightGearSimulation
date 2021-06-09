package View.OpenFiles;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;

public class OpenFilesController {

    @FXML MenuItem csv;
    @FXML MenuItem algo;
    StringProperty CsvPath,AlgoPath,AlgoName;

    public OpenFilesController() {
        super();
        CsvPath = new SimpleStringProperty();
        ObservableList<Object> csvTitle = FXCollections.observableArrayList();
        AlgoPath = new SimpleStringProperty();
        AlgoName = new SimpleStringProperty();
    }

    public void openCSVFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("File Choose");
        fc.setInitialDirectory(new File("./resources"));
        // fc.setInitialDirectory(new File("anomaly_flight.csv"));
        FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("CSV Files (*.csv)","*.csv");
        fc.getExtensionFilters().add(ef);
        File chosen = fc.showOpenDialog(null);
        if(chosen!=null)
        {
            CsvPath.setValue("resources/"+chosen.getName());
            CsvPath.setValue("anomaly_flight.csv/"+chosen.getName());
        }
    }

    public void openCLASSFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("File Choose");
        fc.setInitialDirectory(new File("./out/production/PTM/algorithms"));
        FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("Class Files (*.class)","*.class");
        fc.getExtensionFilters().add(ef);
        File chosen = fc.showOpenDialog(null);
        if(chosen!=null)
        {
            AlgoPath.setValue("resources/"+chosen.getName());
            AlgoName.setValue("model."+chosen.getName().substring(0, chosen.getName().length()-6));
        }
    }

}
