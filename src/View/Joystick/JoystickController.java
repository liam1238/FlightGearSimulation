package View.Joystick;

import View.Main;
import ViewModel.Service;
import ViewModel.ViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Circle;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class JoystickController extends Observable {

    ViewModel vm;

    @FXML public static Circle movingCircle;
    @FXML public static Slider rudderSlider;
    @FXML public static Slider throttleSlider;
    @FXML public static Label yawValue;
    @FXML public static Label AltitudeValue;
    @FXML public static Label DirectionValue;
    @FXML public static Label speedValue;
    @FXML public static Label PitchValue;
    @FXML public static Label RollValue;
    @FXML public static Circle CanvasCircle;

    public JoystickController() {
        vm = Main.viewModel;
    }

    public Slider getRudder(){
        return rudderSlider;
    }

     public void setJoystick() {
         rudderSlider = (Slider) Service.getNodeByID("rudderSlider");
         throttleSlider = (Slider) Service.getNodeByID("throttleSlider");
         movingCircle = (Circle) Service.getNodeByID("joystickCircle");
         AltitudeValue = (Label) Service.getNodeByID("AltitudeValue");
         speedValue = (Label) Service.getNodeByID("speedValue");
         DirectionValue = (Label) Service.getNodeByID("DirectionValue");
         RollValue = (Label) Service.getNodeByID("RollValue");
         PitchValue = (Label) Service.getNodeByID("PitchValue");
         yawValue = (Label) Service.getNodeByID("yawValue");
     }

     public void bindJoystick(){
        rudderSlider.valueProperty().bindBidirectional(vm.rudder);
        throttleSlider.valueProperty().bindBidirectional(vm.throttle);
        movingCircle.translateXProperty().bindBidirectional(vm.aileron);
        movingCircle.translateYProperty().bindBidirectional(vm.elevator);
        AltitudeValue.textProperty().bindBidirectional(vm.altitude);
        speedValue.textProperty().bindBidirectional(vm.speed);
        DirectionValue.textProperty().bindBidirectional(vm.direction);
        RollValue.textProperty().bindBidirectional(vm.roll);
        PitchValue.textProperty().bindBidirectional(vm.pitch);
        yawValue.textProperty().bindBidirectional(vm.yaw);
    }

    public void setDashboardPanel(){
        //set dashboard panel updater
        Timer dashboardTimer = new Timer();
        dashboardTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    //get flight parameter values and set them to dashboard
                    AltitudeValue.setText(vm.getParameter("altimeter_indicated-altitude-ft") + "");
                    speedValue.setText(vm.getParameter("airspeed-indicator_indicated-speed-kt")+"");
                    DirectionValue.setText(vm.getParameter("indicated-heading-deg")+"");
                    RollValue.setText(vm.getParameter("attitude-indicator_indicated-roll-deg")+"");
                    PitchValue.setText(vm.getParameter("attitude-indicator_internal-pitch-deg")+"");
                    yawValue.setText(vm.getParameter("heading-deg")+"");
                });
            }
        }, 0, 100);
    }
}
