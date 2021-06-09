package View;

import View.Graphs.Graphs;
import View.Icons.Icons;
import View.Joystick.Joystick;
import View.ListView.ListView;
import View.OpenFiles.OpenFiles;
import ViewModel.viewModel;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    viewModel vm;

    @FXML Joystick MyJoystick;
    @FXML ListView MyListView;
    @FXML Graphs MyGraph;
    @FXML Icons myIcons;
    @FXML OpenFiles MyOpenFiles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MyJoystick.aileron = new SimpleDoubleProperty();
        MyJoystick.elevators = new SimpleDoubleProperty();
        MyJoystick.rudder = new SimpleDoubleProperty();
        MyJoystick.throttle = new SimpleDoubleProperty();
    }

}

//    public Runnable onPlay, onPause, onStop;
//
//    @FXML public Pane paneParent;
//    @FXML public Slider sliderControllerSpeed;
//    @FXML private ChoiceBox choiceBox2;
//    @FXML private ChoiceBox choiceBox;
//    @FXML public Button ReadAndWriteXML;
//    @FXML private Slider throttle;
//    @FXML private Slider rudder;
//    @FXML View.Icons.Icons Icons;
//
//    @FXML public Pane IconsPane;
//    @FXML public Label labelSpeed;
//    @FXML public ImageView play;
//    @FXML public ImageView pause;
//    @FXML public ImageView stop;
//    @FXML public ImageView speed;
//    @FXML public ImageView slow;
//    @FXML public ImageView doubleSpeedUp;
//    @FXML public ImageView doubleSpeedDown;
//
//    Map<String, List<String>> listMap;
//    Model model = new Model();
//    ViewModel.viewModel viewModel = new viewModel(model);
//    DoubleProperty aileron, elevators;
//
//    public void init() {
//        ObservableList<String> attributes1 = FXCollections.observableArrayList("airspeed-indicator", "altimeter_pressure",
//                "attitude-pitch_deg", "attitude-roll_deg", "attitude-pitch_deg", "attitude-roll_deg", "gps_altitude", "magnetic-compass",
//                "skid-ball", "vertical-speed");
//        choiceBox.setItems(attributes1);
//
//        ObservableList<String> CSVFiles = FXCollections.observableArrayList("anomaly_flight.csv", "reg_flight.csv");
//        choiceBox2.setItems(CSVFiles);
//
//        if(this.rudder!=null && this.throttle!=null) {
//            viewModel.rudder.bind(rudder.valueProperty());
//            viewModel.throttle.bind(throttle.valueProperty());
//            aileron = new SimpleDoubleProperty();
//            elevators = new SimpleDoubleProperty();
//            viewModel.aileron.bind(aileron);
//            viewModel.elevators.bind(elevators);
//
//            viewModel vm = new viewModel();
//           // Icons.controller.onPlay=vm.play;
//           // Icons.controller.onPause=vm.pause;
//           // Icons.controller.onStop=vm.stop;
//
//            doubleSpeedDown = new ImageView();
//            slow = new ImageView();
//            play = new ImageView();
//            pause = new ImageView();
//            stop = new ImageView();
//            speed = new ImageView();
//            doubleSpeedUp = new ImageView();
//
//            doubleSpeedDown.setImage(new Image("speed_down.jpg"));
//            slow.setImage(new Image("slow.jpg"));
//            play.setImage(new Image("play.jpg"));
//            pause.setImage(new Image("pause.jpg"));
//            stop.setImage(new Image("stop.jpg"));
//            speed.setImage(new Image("speed.jpg"));
//            doubleSpeedUp.setImage(new Image("speed_up.jpg"));
//
//
//        }
//    }
//
//
//    public void WriteXML(MouseEvent mouseEvent){
//        System.out.println("XML file read and write...");
//        StaXParser read = new StaXParser();
//        StaxWriter write = new StaxWriter();
//        List<Item> readConfig = read.readConfig("playback_small.xml");
//        write.Writer(readConfig);
//    }
//
//    public void openCSVFile(MouseEvent mouseEvent) {
//        System.out.println("open csv file...");
//        if(choiceBox2.getSelectionModel().getSelectedItem().equals("anomaly_flight.csv")){
//            model.openCSVFile("anomaly_flight.csv");
//            System.out.println("anomaly_flight opened");
//        } else {
//            model.openCSVFile("reg_flight.csv");
//            System.out.println("reg_flight opened");
//        }
//        listMap = model.getMap();
//        model.setAnomalyDetector(model.setTimeSeries("anomaly_flight.csv"));
//        System.out.println("Done");
//    }
//
//    public void AttributesChoose(MouseEvent mouseEvent){
//        switch (choiceBox.getSelectionModel().getSelectedIndex()){
//            case 0:
//                listMap.get("airspeed-indicator_indicated-speed-kt");
//                //map.paint(airspeed);
//                break;
//            case 1:
//                listMap.get("altimeter_pressure-alt-ft");
//                break;
//            case 2:
//                listMap.get("attitude-indicator_indicated-pitch-deg");
//                break;
//            case 3:
//                listMap.get("attitude-indicator_indicated-roll-deg");
//                break;
//            case 4:
//                listMap.get("attitude-indicator_internal-pitch-deg");
//                break;
//            case 5:
//                listMap.get("attitude-indicator_internal-roll-deg");
//                break;
//            case 6:
//                listMap.get("gps_indicated-altitude-ft");
//                break;
//            case 7:
//                listMap.get("magnetic-compass_indicated-heading-deg");
//                break;
//            case 8:
//                listMap.get("slip-skid-ball_indicated-slip-skid");
//                break;
//            case 9:
//                listMap.get("vertical-speed-indicator_indicated-speed-fpm");
//                break;
//
//        }
//    }
//
//
//    public void playClicked(MouseEvent mouseEvent){
//        System.out.println("play the simulator");
//    }
//    public void pauseClicked(MouseEvent mouseEvent){
//        System.out.println("pause the simulator");
//    }
//    public void stopClicked(MouseEvent mouseEvent){
//        System.out.println("stop the simulator");
//    }
//    public void speedDownClicked(MouseEvent mouseEvent){
//        System.out.println("slow down the simulator");
//    }
//    public void speedUpClicked(MouseEvent mouseEvent){
//        System.out.println("speed up the simulator");
//    }
//    public void doubleSpeedDownClicked(MouseEvent mouseEvent){
//        System.out.println("slow down X2 the simulator");
//    }
//    public void doubleSpeedUpClicked(MouseEvent mouseEvent){
//        System.out.println("speed up X2 the simulator");
//    }
//
//
//    public Controller(){}
//
//    private boolean atEndOfVideo = false;
//    private boolean isPlaying = true;
//
//
//
//
//
//    @Override
//    public void update(Observable o, Object arg) {
//
//    }
//
//
//}