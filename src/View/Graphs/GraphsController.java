package View.Graphs;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.BubbleChart;

public class GraphsController {

    @FXML LineChart CorrelatedChart;
    @FXML LineChart FeatureChart;
    @FXML BubbleChart algoChart;

    public GraphsController() {
        super();
    }
}