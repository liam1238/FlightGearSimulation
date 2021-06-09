package View.Joystick;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class JoystickController {
    @FXML Circle movingCircle;
    @FXML Slider rudder;
    @FXML Slider throttle;
    @FXML Label yawValue;
    @FXML Label AltitudeValue;
    @FXML Label DirectionValue;
    @FXML Label speedValue;
    @FXML Label PitchValue;
    @FXML Label RollValue;
    @FXML Circle CanvasCircle;

    public JoystickController() { }
}



//public class JoystickController implements Initializable {
//
//    @FXML public Pane joystickSliderPane;
//    @FXML public Canvas joystick;
//
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//    }
//}
