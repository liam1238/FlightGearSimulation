package View;

import Model.Model;
import ViewModel.viewModel;
import XML.Item;
import XML.StaXParser;
import XML.StaxWriter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    Map <String, List<String>> listMap;
    Model model = new Model();
    //TimeSeries timeSeries = new Model.TimeSeries();
    viewModel viewModel = new viewModel(model);
    DoubleProperty aileron, elevators;

    @FXML
    public Button ReadAndWriteXML;
    @FXML
    public Pane paneParent;
    @FXML
    public Pane attributesTableParent;
    @FXML
    public Slider sliderControllerSpeed;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private ChoiceBox choiceBox2;
    @FXML
    public Pane joystickSliderPane;
    @FXML
    private Slider throttle;
    @FXML
    private Slider rudder;
    @FXML
    public Canvas joystick;
    @FXML
    public ListView attributes;
    @FXML
    public Label labelSpeed;
    @FXML
    public ImageView play;
    @FXML
    public ImageView pause;
    @FXML
    public ImageView stop;
    @FXML
    public ImageView speed;
    @FXML
    public ImageView slow;
    @FXML
    public ImageView doubleSpeedUp;
    @FXML
    public ImageView doubleSpeedDown;

    public Controller() { }

    private boolean atEndOfVideo = false;
    private boolean isPlaying = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> attributes1 = FXCollections.observableArrayList("airspeed-indicator", "altimeter_pressure",
                "attitude-pitch_deg", "attitude-roll_deg", "attitude-pitch_deg", "attitude-roll_deg", "gps_altitude", "magnetic-compass",
                "skid-ball", "vertical-speed");
        choiceBox.setItems(attributes1);

        ObservableList<String> CSVFiles = FXCollections.observableArrayList("anomaly_flight.csv", "reg_flight.csv");
        choiceBox2.setItems(CSVFiles);

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

    public void WriteXML(MouseEvent mouseEvent){
        System.out.println("XML file read and write...");
        StaXParser read = new StaXParser();
        StaxWriter write = new StaxWriter();
        List<Item> readConfig = read.readConfig("playback_small.xml");
        write.Writer(readConfig);
    }

    public void openCSVFile(MouseEvent mouseEvent) {
        System.out.println("open csv file...");
        if(choiceBox2.getSelectionModel().getSelectedItem().equals("anomaly_flight.csv")){
            model.openCSVFile("anomaly_flight.csv");
            System.out.println("anomaly_flight opened");
        } else {
            model.openCSVFile("reg_flight.csv");
            System.out.println("reg_flight opened");
        }
        listMap = model.getMap();
        model.setAnomalyDetector(model.setTimeSeries("anomaly_flight.csv"));
        System.out.println("Done");
    }

    public void AttributesChoose(MouseEvent mouseEvent){
        switch (choiceBox.getSelectionModel().getSelectedIndex()){
            case 0:
                listMap.get("airspeed-indicator_indicated-speed-kt");
                //map.paint(airspeed);
                break;
            case 1:
                listMap.get("altimeter_pressure-alt-ft");
                break;
            case 2:
                listMap.get("attitude-indicator_indicated-pitch-deg");
                break;
            case 3:
                listMap.get("attitude-indicator_indicated-roll-deg");
                break;
            case 4:
                listMap.get("attitude-indicator_internal-pitch-deg");
                break;
            case 5:
                listMap.get("attitude-indicator_internal-roll-deg");
                break;
            case 6:
                listMap.get("gps_indicated-altitude-ft");
                break;
            case 7:
                listMap.get("magnetic-compass_indicated-heading-deg");
                break;
            case 8:
                listMap.get("slip-skid-ball_indicated-slip-skid");
                break;
            case 9:
                listMap.get("vertical-speed-indicator_indicated-speed-fpm");
                break;

        }
    }

}
