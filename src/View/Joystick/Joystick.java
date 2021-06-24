package View.Joystick;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Joystick extends AnchorPane {
    public DoubleProperty aileron;
    public DoubleProperty elevators;
    public DoubleProperty rudder;
    public DoubleProperty throttle;

    public JoystickController myJoystickController;

    public Joystick() {
        AnchorPane joy;
        try {
        FXMLLoader fxl = new FXMLLoader();
        joy = fxl.load(this.getClass().getResource("Joystick.fxml").openStream());
        this.myJoystickController = fxl.getController();
        this.getChildren().add(joy);
        } catch (IOException e) { e.printStackTrace(); }
    }
}
