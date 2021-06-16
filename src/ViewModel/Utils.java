package application;

import View.Main;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import plugin.AnomalyDetectionAlgorithm;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

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
		getNodeByID("superSlowButton").setDisable(disableAll);
		getNodeByID("slowButton").setDisable(disableAll);
		getNodeByID("fastButton").setDisable(disableAll);
		getNodeByID("superFastButton").setDisable(disableAll);
		getNodeByID("speedMultySlider").setDisable(disableAll);
		getNodeByID("speedMultyTextfield").setDisable(disableAll);
		getNodeByID("currentFlightTimeLabel").setDisable(disableAll);
		getNodeByID("currentFlightTimeSlider").setDisable(disableAll);
		getNodeByID("totalFlightTimeLabel").setDisable(disableAll);
		getNodeByID("parameterListView").setDisable(disableAll);
		getNodeByID("classListView").setDisable(disableAll);
	}
	
	public static void loadPlugin(String classname) {
		URLClassLoader urlClassLoader;
		try {
			urlClassLoader = URLClassLoader.newInstance(new URL[] {
			 new URL("file://" + System.getProperty("user.dir") + "\\bin")
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
			new Alert(Alert.AlertType.ERROR, "Error: Could not load detection plugin directory").showAndWait();
			return;
		}

		Class<AnomalyDetectionAlgorithm> plugin;
		try {
			plugin = (Class<AnomalyDetectionAlgorithm>)(urlClassLoader.loadClass("plugin." + classname));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			new Alert(Alert.AlertType.ERROR, "Error: Could not load detection plugin").showAndWait();
			return;
		}

		try {
			Main.viewModel.plugin = plugin.newInstance();
			Main.viewModel.plugin.learnNormal(new File("reg_flight.csv"));
			Main.viewModel.plugin.detect(new File(Main.viewModel.conf.flight_data_csv));
		} catch (InstantiationException | IllegalAccessException e) {
			new Alert(Alert.AlertType.ERROR, "Error: Detection plugin failure").showAndWait();
			return;
		}
	}
}
