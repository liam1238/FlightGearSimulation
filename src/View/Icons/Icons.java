package View.Icons;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Icons extends AnchorPane {

    public Button doubleSpeedDownButton;
    public Button speedDownButton;
    public Button playButton;
    public Button pauseButton;
    public Button stopButton;
    public Button speedUpButton;
    public Button doubleSpeedUpButton;
    public TextField speedText;
    public Slider FlightSlider;
    public Label currentTimeLabel;
    public Slider speedSlider;

    public Icons() {
        super();
        try {
            FXMLLoader fxl = new FXMLLoader();
            AnchorPane playerIcons = fxl.load(getClass().getResource("Icons.fxml").openStream());
            IconsController IconsController = fxl.getController();
            speedDownButton = IconsController.speedDownButton;
            doubleSpeedDownButton = IconsController.doubleSpeedDownButton;
            doubleSpeedUpButton = IconsController.doubleSpeedUpButton;
            speedUpButton = IconsController.speedUpButton;
            pauseButton = IconsController.pauseButton;
            playButton = IconsController.playButton;
            stopButton = IconsController.stopButton;
            speedText = IconsController.SpeedText;
            currentTimeLabel = IconsController.LabelCurrentTime;
            FlightSlider = IconsController.FlightSlider;
            speedSlider = IconsController.SpeedSlider;
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
        doubleSpeedDownButton.setTranslateX(20);
        doubleSpeedDownButton.setTranslateY(25);
        speedDownButton.setTranslateX(80);
        speedDownButton.setTranslateY(25);
        playButton.setTranslateX(140);
        playButton.setTranslateY(25);
        pauseButton.setTranslateX(200);
        pauseButton.setTranslateY(25);
        stopButton.setTranslateX(260);
        stopButton.setTranslateY(25);
        speedUpButton.setTranslateX(320);
        speedUpButton.setTranslateY(25);
        doubleSpeedUpButton.setTranslateX(380);
        doubleSpeedUpButton.setTranslateY(25);
        //Setting a graphic to the button
        doubleSpeedDownButton.setGraphic(view1);
        speedDownButton.setGraphic(view2);
        playButton.setGraphic(view3);
        pauseButton.setGraphic(view4);
        stopButton.setGraphic(view5);
        speedUpButton.setGraphic(view6);
        doubleSpeedUpButton.setGraphic(view7);
    }

}
