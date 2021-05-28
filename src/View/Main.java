package View;

import Model.Model;
import ViewModel.viewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FlightSimulator.fxml"));
        primaryStage.setTitle("Flight Simulator");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
        Model model = new Model();
        viewModel viewModel = new viewModel(model);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
