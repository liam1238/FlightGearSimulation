package ViewModel;

import Model.Model;
import View.Main;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import Algorithms.AnomalyDetectionAlgorithm;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Service {

	public static String msToTimeString(long ms) {	// Parses a time in m.s. to an HH:MM:SS format
		SimpleDateFormat df = (new SimpleDateFormat("HH:mm:ss"));
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.format((new Date(ms)));
	}

	public static Node getNodeByID(String id) {
		return Main.scene.getRoot().lookup("#" + id);
	}

	public static void setDisableALL(boolean disableAll) {
		getNodeByID("playButton").setDisable(disableAll);
		getNodeByID("pauseButton").setDisable(disableAll);
		getNodeByID("stopButton").setDisable(disableAll);
		getNodeByID("doubleSpeedDownButton").setDisable(disableAll);
		getNodeByID("speedDownButton").setDisable(disableAll);
		getNodeByID("speedUpButton").setDisable(disableAll);
		getNodeByID("doubleSpeedUpButton").setDisable(disableAll);
		getNodeByID("SpeedSlider").setDisable(disableAll);
		getNodeByID("SpeedText").setDisable(disableAll);
		getNodeByID("LabelCurrentTime").setDisable(disableAll);
		getNodeByID("FlightSlider").setDisable(disableAll);
		getNodeByID("TotalFlightTime").setDisable(disableAll);
		getNodeByID("parametersList").setDisable(disableAll);
		getNodeByID("ListView").setDisable(disableAll);
	}
	
	public static void loadAlgorithm(String classname) {
		URLClassLoader urlClassLoader;
		try {
			urlClassLoader = URLClassLoader.newInstance(new URL[] {
			 new URL("file://" + System.getProperty("user.dir") + "\\bin")
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
			new Alert(Alert.AlertType.ERROR, "Error: Could not load detection Algorithms directory").showAndWait();
			return;
		}

		Class<AnomalyDetectionAlgorithm> Algorithms;
		try {
			Algorithms = (Class<AnomalyDetectionAlgorithm>)(urlClassLoader.loadClass("Algorithms." + classname));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			new Alert(Alert.AlertType.ERROR, "Error: Could not load detection Algorithm").showAndWait();
			return;
		}

		try {
			ViewModel.algorithm = Algorithms.newInstance();
			ViewModel.algorithm.learnNormal(new File("reg_flight.csv"));
			ViewModel.algorithm.detect(new File(Model.config.flight_data_csv));
		} catch (InstantiationException | IllegalAccessException e) {
			new Alert(Alert.AlertType.ERROR, "Error: Detection algorithm failure").showAndWait();
		}
	}
}
