package View;

import Model.Model;
import ViewModel.viewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import javax.naming.Binding;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class Controller implements Initializable {
    Map <String, List<String>> listMap;
    Model model = new Model();
    //TimeSeries timeSeries = new Model.TimeSeries();
    viewModel viewModel = new viewModel(model);
    DoubleProperty aileron, elevators;

    public Button OpenCsvFiles;
    @FXML
    private Pane paneParent;
    @FXML
    private Pane attributesTableParent;
    @FXML
    private Slider sliderControllerSpeed;
    @FXML
    private Button openCsvFiles;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private Pane graphsAttributesParent;
    @FXML
    private LineChart graph2;
    @FXML
    private LineChart graph1;
    @FXML
    private LineChart algoGraph;
    @FXML
    private Pane joystickSlider;
    @FXML
    private Slider throttle;
    @FXML
    private Slider rudder;
    @FXML
    private Canvas joystick;
    @FXML
    private ListView attributes;
    @FXML
    private Label labelSpeed;
    @FXML
    private ImageView play;
    @FXML
    private ImageView pause;
    @FXML
    private ImageView stop;
    @FXML
    private ImageView speed;
    @FXML
    private ImageView slow;
    @FXML
    private ImageView doubleSpeedUp;
    @FXML
    private ImageView doubleSpeedDown;

    public Controller() { }

    private boolean atEndOfVideo = false;
    private boolean isPlaying = true;
    private ImageView ivPlay;
    private ImageView ivPause;
    private ImageView ivSpeedDown;
    private ImageView ivSpeedUp;
    private ImageView ivDoubleSpeedUp;
    private ImageView ivDoubleSpeedDown;
    private ImageView ivStop;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> attributes1 = FXCollections.observableArrayList("aileron", "elevator", "rudder", "flaps",
                "slats", "speedbrake", "throttle", "throttle1", "engine-pump", "engine-pump1", "electric-pump", "electric-pump1",
                "external-power", "APU-generator", "latitude-deg", "longitude-deg", "altitude-ft", "roll-deg", "pitch-deg", "heading-deg",
                "side-slip-deg", "airspeed-kt", "glideslope", "vertical-speed-fps", "airspeed-indicator_indicated-speed-kt",
                "altimeter_indicated-altitude-ft", "altimeter_pressure-alt-ft", "attitude-indicator_indicated-pitch-deg",
                "attitude-indicator_indicated-roll-deg", "attitude-indicator_internal-pitch-deg", "attitude-indicator_internal-roll-deg",
                "encoder_indicated-altitude-ft", "encoder_pressure-alt-ft", "gps_indicated-altitude-ft", "gps_indicated-ground-speed-kt",
                "gps_indicated-vertical-speed", "indicated-heading-deg", "magnetic-compass_indicated-heading-deg",
                "slip-skid-ball_indicated-slip-skid", "turn-indicator_indicated-turn-rate", "vertical-speed-indicator_indicated-speed-fpm",
                "engine_rpm");
        choiceBox.setItems(attributes1);

        viewModel.rudder.bind(rudder.valueProperty());
        viewModel.throttle.bind(throttle.valueProperty());
        aileron = new SimpleDoubleProperty();
        elevators = new SimpleDoubleProperty();
        viewModel.aileron.bind(aileron);
        viewModel.elevators.bind(elevators);

        /*  MediaView mediaView = new MediaView(new File(""));
        **********  sliderControllerSpeed.setAccessibleText("1X"); *************

        labelSpeed.setOnMouseClicked(mouseEvent -> {
            if(labelSpeed.getText().equals("1X")) {
                labelSpeed.setText("2X");
                //the video
                //mpVideo.setRate(2.0);
            }
            else
            {
                labelSpeed.setText("1X");
                //mpVideo.setRate(1X);
            }
        });

        sliderControllerSpeed.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasChanging, Boolean isChanging) {
                if(!isChanging)
                {
                    //mpVideo.seek(Duration.seconds(sliderControllerSpeed.getValue());//the slider working at seconds.
                }
            }
        });
       sliderControllerSpeed.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                double currentTime = mpVideo.getCurrentTime().toSeconds();
                if(Math.abs(currentTime - newValue.doubleValue()) > 0.5){
                    //mpVideo.seek(Duration.seconds(newValue.newValue.doubleValue()))
                }
            }
        });

        bindCorrectTimeLabel();
    }


    public void bindCorrectTimeLabel(){
        labelCurrentTime.textProperty().bind(Binding.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getTime(myVideo.getCurrentTime()) + " / ";
            }
        }, mpVideo.CurrentTimeProperty()));

    }
    public String getTime(Duration time)
    {
        int hours = (int)time.toHours();
        int minutes = (int)time.toMinutes();
        int seconds = (int)time.toSeconds();

        if(seconds > 59 ) seconds = seconds % 60;
        if(minutes > 59 ) minutes = minutes % 60;
        if(hours > 59 ) hours = hours % 60;

        if(hours > 0) return String.format("%d:%02d:%02d",hours,minutes,seconds);
        else{
            return String.format("%02d:%02d",minutes,seconds);
        }
    }
*/
    }
    public void playClicked(MouseEvent mouseEvent){
        System.out.println("play the simulator");
    }
    public void pauseClicked(MouseEvent mouseEvent){
        System.out.println("pause the simulator");
    }
    public void stopClicked(MouseEvent mouseEvent){
        System.out.println("stop the simulator");
    }
    public void speedDownClicked(MouseEvent mouseEvent){
        System.out.println("slow down the simulator");
    }
    public void speedUpClicked(MouseEvent mouseEvent){
        System.out.println("speed up the simulator");
    }
    public void doubleSpeedDownClicked(MouseEvent mouseEvent){
        System.out.println("slow down X2 the simulator");
    }
    public void doubleSpeedUpClicked(MouseEvent mouseEvent){
        System.out.println("speed up X2 the simulator");
    }

    public void openCSVFile(MouseEvent mouseEvent){
        System.out.println("open csv file...");
        model.openCSVFile("anomaly_flight.csv");
        listMap = model.getMap();
       // model.setTimeSeries("anomaly_flight.csv");
        model.setAnomalyDetector(model.setTimeSeries("anomaly_flight.csv"));
        System.out.println("Done");
    }




}