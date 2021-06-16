package ViewModel;

import Model.FlightGearAPI;
import Model.Model;
import Model.SimulatorAPI;
import javafx.scene.control.Alert;
import plugin.AnomalyDetectionAlgorithm;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ViewModel extends Observable implements Observer {
    Model model;
   // public Runnable play,pause,stop, speedUp, speedDown, doubleSpeedUp, doubleSpeedDown;

    public static ConfigurationFile conf;
    public static SimulatorAPI simulatorApi;
    public static AnomalyDetectionAlgorithm plugin;
    public ViewModel(Model m){
        this.model = m;
        m.addObserver(this);
    }

    public void start(){
        try {
            conf = ConfigurationFile.readConfigFromXML("config.xml");
            simulatorApi = new FlightGearAPI();
            simulatorApi.start();
        } catch (IOException | InterruptedException e1) {
            new Alert(Alert.AlertType.ERROR, "Critical error: Could not read config / playback XML / Invalid simulator path in config").showAndWait();
        }
    }

    @Override
    public void update(Observable o, Object arg) { }

    public void openCSV(){ model.openCSVFile(); }
    public void play() {model.play(); setChanged(); notifyObservers();}
    public void pause(){ model.pause(); setChanged(); notifyObservers();}
    public void stop(){ model.stop(); setChanged(); notifyObservers();}
    public void speedUp(){ model.speedUp();setChanged(); notifyObservers(); }
    public void speedDown(){ model.speedDown(); setChanged(); notifyObservers();}
    public void doubleSpeedUp(){ model.doubleSpeedUp(); setChanged(); notifyObservers();}
    public void doubleSpeedDown(){ model.doubleSpeedDown(); setChanged(); notifyObservers();}
    public void change(){ model.changeTextFiled(); }
    public void change2(){ model.changeInSpeedSlider(); }
}
