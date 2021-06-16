package View.Icons;

import View.Main;
import ViewModel.ViewModel;
import application.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.text.DecimalFormat;

public class IconsController {
    ViewModel vm;
    @FXML Slider speedMultySlider;
    @FXML Button superSlowButton;
    @FXML Button slowButton;
    @FXML Button playButton;
    @FXML Button pauseButton;
    @FXML Button stopButton;
    @FXML Button fastButton;
    @FXML Button superFastButton;
    @FXML TextField speedMultyTextfield;
    @FXML Slider currentFlightTimeSlider;
    @FXML Label currentFlightTimeLabel;

    public IconsController() {
        super();
        vm = Main.viewModel;
    }

    public void onClickPlay() { vm.play(); }
    public void onClickPause() { vm.pause(); }
    public void onClickStop() { vm.stop(); }
    public void onClickSlow() { vm.speedDown(); }
    public void onClickSuperSlow() { vm.doubleSpeedDown(); }
    public void onClickFast() { vm.speedUp(); }
    public void onClickSuperFast() { vm.doubleSpeedUp(); }
    public void onTextChangedMultField() { vm.change(); }

    //change label when sliding, but only change flight time when we drop the slider
    public void onMousePressedTimeSlider() { Main.isTimeSliding = true; }
    public void onMouseMovedTimeSlider() {
        ((Slider)Utils.getNodeByID("currentFlightTimeSlider")).valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!Main.isTimeSliding)
                return;
            ((Label)Utils.getNodeByID("currentFlightTimeLabel")).setText(Utils.msToTimeString((long)((Slider)Utils.getNodeByID("currentFlightTimeSlider")).getValue())); //update label
        });
    }

    public void onMouseReleasedTimeSlider() {
        Main.viewModel.simulatorApi.setCurrentFlightTime((int)((Slider)Utils.getNodeByID("currentFlightTimeSlider")).getValue()); //set flight time
        Main.isTimeSliding = false;
    }

    //change label when sliding, but only change flight time when we drop the slider
    public void onMousePressedSpeedSlider() {
        Main.isSpeedSliding = true;
    }
    public void onMouseMovedSpeedSlider() {
        ((Slider) Utils.getNodeByID("speedMultySlider")).valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!Main.isSpeedSliding)
                return;
            ((TextField) Utils.getNodeByID("speedMultyTextfield")).setText(Float.parseFloat((new DecimalFormat("0.0"))
                    .format(((Slider) Utils.getNodeByID("speedMultySlider")).getValue() / 100)) + ""); //round slider data and set it to speed field
        });
    }

    public void onMouseReleasedSpeedSlider() {
        vm.change2();
        Main.isSpeedSliding = false;
    }

}
