package View.Icons;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Icons extends AnchorPane {
    public Button back;
    public Button doubleBack;
    public Button doubleForward;
    public StringProperty FlightStatus;
    public Button forward;
    public Button pause;
    public Button play;
    public Button stop;
    public Slider videoSlider;
    public ChoiceBox videoSpeed;
    public Label videoTime;
    public DoubleProperty forwardCnt;
    public SimpleDoubleProperty forward2Cnt;
    public SimpleDoubleProperty backwardCnt;
    public SimpleDoubleProperty backward2Cnt;

    public Icons() {
        super();
        try {
            FXMLLoader fxl = new FXMLLoader();
            AnchorPane playerIcons = fxl.load(getClass().getResource("Icons.fxml").openStream());
            IconsController IconsController = fxl.getController();
            back = IconsController.back;
            doubleBack = IconsController.doubleBack;
            doubleForward = IconsController.doubleForward;
            FlightStatus = IconsController.FlightStatus;
            forward = IconsController.forward;
            pause = IconsController.pause;
            play = IconsController.play;
            stop = IconsController.stop;
            videoSlider = IconsController.videoSlider;
            videoSpeed = IconsController.videoSpeed;
            ObservableList<Number> s = FXCollections.observableArrayList(0.5, 1.0, 1.5, 2.0);
            videoSpeed.setItems(s);
            videoSpeed.setValue(1.0);
            videoTime = IconsController.videoTime;
            forwardCnt = IconsController.forwardCnt;
            forward2Cnt = IconsController.forward2Cnt;
            backwardCnt = IconsController.backwardCnt;
            backward2Cnt = IconsController.backward2Cnt;
            this.getChildren().add(playerIcons);
        } catch (IOException e) { e.printStackTrace(); }

        Image im1, im2, im3, im4, im5, im6, im7;
        im1 = new Image("View/Icons/speed_down.jpg");
        im2 = new Image("View/Icons/slow.jpg");
        im3 = new Image("View/Icons/play.jpg");
        im4 = new Image("View/Icons/pause.jpg");
        im5 = new Image("View/Icons/stop.jpg");
        im6 = new Image("View/Icons/speed.jpg");
        im7 = new Image("View/Icons/speed_up.jpg");
        ImageView view1 = new ImageView(im1);
        ImageView view2 = new ImageView(im2);
        ImageView view3 = new ImageView(im3);
        ImageView view4 = new ImageView(im4);
        ImageView view5 = new ImageView(im5);
        ImageView view6 = new ImageView(im6);
        ImageView view7 = new ImageView(im7);
        view1.setFitHeight(35);
        view1.setPreserveRatio(true);
        view2.setFitHeight(35);
        view2.setPreserveRatio(true);
        view3.setFitHeight(35);
        view3.setPreserveRatio(true);
        view4.setFitHeight(35);
        view4.setPreserveRatio(true);
        view5.setFitHeight(35);
        view5.setPreserveRatio(true);
        view6.setFitHeight(35);
        view6.setPreserveRatio(true);
        view7.setFitHeight(35);
        view7.setPreserveRatio(true);
        //Setting the location of the buttons
        doubleBack.setTranslateX(20);
        doubleBack.setTranslateY(25);
        back.setTranslateX(80);
        back.setTranslateY(25);
        play.setTranslateX(140);
        play.setTranslateY(25);
        pause.setTranslateX(200);
        pause.setTranslateY(25);
        stop.setTranslateX(260);
        stop.setTranslateY(25);
        forward.setTranslateX(320);
        forward.setTranslateY(25);
        doubleForward.setTranslateX(380);
        doubleForward.setTranslateY(25);
        //Setting a graphic to the button
        doubleBack.setGraphic(view1);
        back.setGraphic(view2);
        play.setGraphic(view3);
        pause.setGraphic(view4);
        stop.setGraphic(view5);
        forward.setGraphic(view6);
        doubleForward.setGraphic(view7);
    }

    public String toStringTime(Double object) {
        long seconds = object.longValue();
        long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        long remainingSeconds = seconds - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d", minutes) + ":" + String.format("%02d", remainingSeconds);
    }
}
