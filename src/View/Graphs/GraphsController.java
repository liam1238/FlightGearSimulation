package View.Graphs;

import ViewModel.Service;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

public class GraphsController {

    @FXML public static NumberAxis chosenParameter;
    @FXML public static NumberAxis correlatedParameter;
    @FXML public static LineChart Graph1;
    @FXML public static LineChart Graph2;
    @FXML public static Canvas Canvas;


    public GraphsController() {
        super();
    }

    public void setGraph(){
        Graph1 = (LineChart) Service.getNodeByID("Graph1");
        Graph2 = (LineChart) Service.getNodeByID("Graph2");
        Canvas = (Canvas) Service.getNodeByID("Canvas");
        chosenParameter = (NumberAxis) Service.getNodeByID("chosenParameter");
        correlatedParameter = (NumberAxis) Service.getNodeByID("correlatedParameter");
    }
}
