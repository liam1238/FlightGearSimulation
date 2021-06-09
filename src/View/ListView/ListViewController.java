package View.ListView;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;

public class ListViewController {

    @FXML ListView listView;
    @FXML Button xml;
    StringProperty XmlPath;

    public ListViewController() {
        super();
        XmlPath = new SimpleStringProperty();
    }

    public void openXMLFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("File Choose");
        fc.setInitialDirectory(new File("./resources"));
        //fc.setInitialDirectory(new File("./playback_small"));
        FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("XML Files (*.xml)","*.xml");
        fc.getExtensionFilters().add(ef);
        File chosen = fc.showOpenDialog(null);
        if(chosen!=null)
        {
            //vm.loadXml("resources/"+chosen.getName());
            XmlPath.setValue("resources/"+chosen.getName());

        }
    }


}
