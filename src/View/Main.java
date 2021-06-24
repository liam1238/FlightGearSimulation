package View;

import Model.Model;
import ViewModel.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

	public static ViewModel viewModel;
	public static Scene scene;
	public static boolean SliderTimeTouch = false;
	public static boolean SliderSpeedTouch = false;
	public static boolean ChooseParameter = false;
	public static View view;

	@Override
	public void start(Stage primaryStage) throws IOException {

		System.out.println("Connecting to the flight simulator...");
		Model m = new Model();
		viewModel = new ViewModel(m);
		viewModel.start();
		System.out.println("Connected.");
		BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FlightSimulator.fxml")));
		scene = new Scene(root,900,600);
		view = new View();
		view.setAll();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Flight Simulator");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	@Override
	public void stop(){
		System.out.println("Stage is closing, bye bye...");
	    try { Model.simulatorApi.finalize(); } catch(Exception e) {e.printStackTrace();}; //finalize when we close the stage
	}

	public static void main(String[] args) {
		launch(args);
	}
}
