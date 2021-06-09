package View.Icons;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.concurrent.TimeUnit;

public class IconsController {
    @FXML Button doubleBack;
    @FXML Button back;
    @FXML Button play;
    @FXML Button pause;
    @FXML Button stop;
    @FXML Button forward;
    @FXML Button doubleForward;
    @FXML ChoiceBox videoSpeed;
    @FXML Slider videoSlider;
    @FXML Label videoTime;
    StringProperty FlightStatus;
    DoubleProperty forwardCnt;
    double f = 0;
    SimpleDoubleProperty forward2Cnt;
    SimpleDoubleProperty backwardCnt;
    SimpleDoubleProperty backward2Cnt;

    public IconsController() {
        super();
        FlightStatus = new SimpleStringProperty();
        forwardCnt = new SimpleDoubleProperty();
        forward2Cnt = new SimpleDoubleProperty();
        backwardCnt = new SimpleDoubleProperty();
        backward2Cnt = new SimpleDoubleProperty();
    }

    @FXML public void startFlight() {
        FlightStatus.setValue("Fly");
    }

    @FXML private void stopFlight() {
        if (FlightStatus.getValue() == null || (!FlightStatus.getValue().equals("Fly") && !FlightStatus.getValue().equals("pause Fly") && !FlightStatus.getValue().equals("skip"))) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error on stop flight");
            a.setContentText("there is no flight to stop");
            a.showAndWait();
        } else
            FlightStatus.set("not Fly");
    }

    public String toStringTime(Double object) {
        long seconds = object.longValue();
        long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        long remainingSeconds = seconds - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d", minutes) + ":" + String.format("%02d", remainingSeconds);
    }

    @FXML private void pauseFlight() {
        if ( FlightStatus.getValue() == null || FlightStatus.getValue().equals("not Fly")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error on pause flight");
            a.setContentText("there is no flight to pause");
            a.showAndWait();
        } else
            FlightStatus.set("pause Fly");
    }

    @FXML public void SkipForward() {
        forwardCnt.setValue(f++);
        FlightStatus.set("skip");
    }

    @FXML public void SkipForwardTwice() {
        forward2Cnt.setValue(f++);
        FlightStatus.set("skip");
    }

    @FXML public void SkipBackward() {
        backwardCnt.setValue(f++);
        FlightStatus.set("skip");
    }

    @FXML public void SkipBackwardTwice() {
        backward2Cnt.setValue(f++);
        FlightStatus.set("skip");
    }
}


//    @FXML private ImageView doubleSpeedDown;
//    @FXML private ImageView slow;
//    @FXML private ImageView play;
//    @FXML private ImageView pause;
//    @FXML private ImageView stop;
//    @FXML private ImageView speed;
//    @FXML private ImageView doubleSpeedUp;
//
//
//    public Runnable onPlay, onPause, onStop;
//
//    @FXML public Pane IconsPane;
//    @FXML public Label labelSpeed;
//
//
//    public void play(){
//        if(onPlay!=null)
//            onPlay.run();
//    }
//    public void pause(){
//        if(onPause!=null)
//            onPause.run();
//    }
//    public void stop(){
//        if(onStop!=null)
//            onStop.run();
//    }
//  /*  public void playClicked(MouseEvent mouseEvent){ System.out.println("play the simulator"); }
//    public void pauseClicked(MouseEvent mouseEvent){
//        System.out.println("pause the simulator");
//    }
//    public void stopClicked(MouseEvent mouseEvent){
//        System.out.println("stop the simulator");
//    }*/
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
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        doubleSpeedDown.setImage(new Image("speed_down.jpg"));
//        slow.setImage(new Image("slow.jpg"));
//        play.setImage(new Image("play.jpg"));
//        pause.setImage(new Image("pause.jpg"));
//        stop.setImage(new Image("stop.jpg"));
//        speed.setImage(new Image("speed.jpg"));
//        doubleSpeedUp.setImage(new Image("speed_up.jpg"));
//    }
//
//}
