package View;

import View.Graphs.Graphs;
import View.Icons.Icons;
import View.Joystick.Joystick;
import View.ListView.ListView;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML Joystick MyJoystick;
    @FXML ListView MyListView;
    @FXML Graphs MyGraph;
    @FXML Icons myIcons;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MyJoystick.aileron = new SimpleDoubleProperty();
        MyJoystick.elevators = new SimpleDoubleProperty();
        MyJoystick.rudder = new SimpleDoubleProperty();
        MyJoystick.throttle = new SimpleDoubleProperty();

    }
}
