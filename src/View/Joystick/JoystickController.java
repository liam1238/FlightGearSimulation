package View.Joystick;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Circle;

public class JoystickController {
    @FXML Circle movingCircle;
    @FXML Slider rudderSlider;
    @FXML Slider throttleSlider;
    @FXML Label yawValue;
    @FXML Label AltitudeValue;
    @FXML Label DirectionValue;
    @FXML Label speedValue;
    @FXML Label PitchValue;
    @FXML Label RollValue;
    @FXML Circle CanvasCircle;

    public JoystickController() { }
}
