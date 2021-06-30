package View.ListView;

import java.io.IOException;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ListView extends AnchorPane {
    public javafx.scene.control.ListView list;
    public Button xml;
    public StringProperty xmlPath;

    public ListView() {
        try {
            FXMLLoader fxl = new FXMLLoader();
            AnchorPane listView = fxl.load(this.getClass().getResource("ListView.fxml").openStream());
            ListViewController listViewController = fxl.getController();
            this.list = listViewController.listView;
            xmlPath = listViewController.XmlPath;
            this.getChildren().add(listView);
        } catch (IOException e) { e.printStackTrace(); }
    }
}
