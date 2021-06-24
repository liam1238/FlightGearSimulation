package View.Icons;

import View.Main;
import ViewModel.Service;
import ViewModel.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.text.DecimalFormat;
import java.util.Observable;

public class IconsController extends Observable {

    public Runnable play, pause, stop, speedUp, speedDown, doubleSpeedUp, doubleSpeedDown;

    ViewModel vm;
    @FXML Button doubleSpeedDownButton;
    @FXML Button speedDownButton;
    @FXML Button playButton;
    @FXML Button pauseButton;
    @FXML Button stopButton;
    @FXML Button speedUpButton;
    @FXML Button doubleSpeedUpButton;
    @FXML public static Slider SpeedSlider;
    @FXML public static TextField SpeedText;
    @FXML public static Slider FlightSlider;
    @FXML public static Label LabelCurrentTime;
    @FXML public static Label TotalFlightTime;

    public IconsController() {
        super();
        vm = Main.viewModel;
        this.addObserver(vm);
        play = ()-> vm.play();
        pause = ()->vm.pause();
        stop = ()->vm.stop();
        speedUp = ()-> vm.speedUp();
        speedDown = ()->vm.speedDown();
        doubleSpeedUp = ()->vm.doubleSpeedUp();
        doubleSpeedDown = ()->vm.doubleSpeedDown();
    }

    public void setIcons(){
        SpeedText = (TextField) Service.getNodeByID("SpeedText");
        LabelCurrentTime = (Label) Service.getNodeByID("LabelCurrentTime");
        FlightSlider = (Slider) Service.getNodeByID("FlightSlider");
        SpeedSlider = (Slider) Service.getNodeByID("SpeedSlider");
        TotalFlightTime = (Label) Service.getNodeByID("TotalFlightTime");
    }

    public void bindIcons(){
        FlightSlider.valueProperty().bindBidirectional(vm.currentFlightTime);
        LabelCurrentTime.textProperty().bindBidirectional(vm.currentTimeString);
        SpeedSlider.valueProperty().bindBidirectional(vm.speedMultySlider);
    }

    public Label getCurrentTimeLabel(){ return LabelCurrentTime; }

    public void onClickPlay() {
        if(play!=null)
            play.run();
        notifyObservers("play");
     }
    public void onClickPause() {
        if(pause!=null)
            pause.run();
        notifyObservers("pause");
    }
    public void onClickStop() {
        if(stop!=null)
            stop.run();
        notifyObservers("stop");
    }
    public void onClickSpeedDown() {
        if(speedDown!=null)
            speedDown.run();
        notifyObservers("slow");
    }
    public void onClickDoubleSpeedDown() {
        if(doubleSpeedDown!=null)
            doubleSpeedDown.run();
        notifyObservers("slower");
    }
    public void onClickSpeedUp() {
        if(speedUp!=null)
            speedUp.run();
        notifyObservers("fast");
    }
    public void onClickDoubleSpeedUp() {
        if(doubleSpeedUp!=null)
            doubleSpeedUp.run();
        notifyObservers("faster");
    }
    public void onChangeSpeedText() { vm.change(); }

    //change label when sliding, but only change flight time when we drop the slider
    public void onMousePressedSlider() {
        FlightSlider.valueProperty().unbindBidirectional(vm.currentFlightTime);
        LabelCurrentTime.textProperty().unbindBidirectional(vm.currentTimeString);
        Main.SliderTimeTouch = true; }

    public void onMouseMovedTimeSlider() {
        FlightSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!Main.SliderTimeTouch)
                return;
            LabelCurrentTime.setText(Service.msToTimeString((long)(FlightSlider.getValue()))); //update label
        });
    }

    public void onMouseReleasedSlider() {
        vm.setCurrentFlightTime((int)(FlightSlider.getValue())); //set flight time
        FlightSlider.valueProperty().bindBidirectional(vm.currentFlightTime);
        LabelCurrentTime.textProperty().bindBidirectional(vm.currentTimeString);
        Main.SliderTimeTouch = false;
    }

    //change label when sliding, but only change flight time when we drop the slider
    public void onMousePressedSpeedSlider() {
        Main.SliderSpeedTouch = true;
    }
    public void onMouseMovedSpeedSlider() {
        SpeedSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!Main.SliderSpeedTouch)
                return;
            SpeedText.setText(Float.parseFloat((new DecimalFormat("0.0"))
                    .format(SpeedSlider.getValue() / 100)) + ""); //round slider data and set it to speed field
        });
    }

    public void onMouseReleasedSpeedSlider() {
        vm.change2();
        Main.SliderSpeedTouch = false;
    }

}

