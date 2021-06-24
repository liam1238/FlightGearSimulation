package View.Graphs;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class Graphs extends HBox {

    public Graphs() {
        super();
        HBox graph;
        try {
            FXMLLoader fxl = new FXMLLoader();
            graph = fxl.load(getClass().getResource("Graphs.fxml").openStream());
            GraphsController graphsController = fxl.getController();
            this.getChildren().add(graph);
        } catch (IOException e) { e.printStackTrace(); }
    }
}

