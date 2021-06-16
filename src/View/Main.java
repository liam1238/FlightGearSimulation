package View;

import Model.Model;
import ViewModel.ViewModel;
import application.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

	public static ViewModel viewModel;
	public static Scene scene;
	public static boolean isTimeSliding = false;
	public static boolean isSpeedSliding = false;
	public static boolean paramSelected = false;

	@Override
	public void start(Stage primaryStage) throws IOException {

		System.out.println("Connecting to the flight simulator...");
		Model m = new Model();
		viewModel = new ViewModel(m);
		viewModel.start();
		System.out.println("Connected.");
		FXMLLoader fxmlLoader = new FXMLLoader();
		BorderPane root = fxmlLoader.load(getClass().getResource("FlightSimulator.fxml"));
		primaryStage.setTitle("Flight Simulator");
		scene = new Scene(root,900,630);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		Utils.setDisableALL(true); //disable all icons before opening a csv file
		((LineChart)Utils.getNodeByID("paramGraph1")).setCreateSymbols(false); //unable line charts from display dots
		((LineChart)Utils.getNodeByID("paramGraph2")).setCreateSymbols(false); //unable line charts from display dots
	}

	@Override
	public void stop(){
		System.out.println("Stage is closing, bye bye...");
	    try { ViewModel.simulatorApi.finalize(); } catch(Exception e) {e.printStackTrace();}; //finalize when we close the stage
	}

	public static void main(String[] args) {
		launch(args);
	}
}
