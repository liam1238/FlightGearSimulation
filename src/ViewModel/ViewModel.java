package ViewModel;

import Model.Model;
import View.Icons.IconsController;
import View.View;
import javafx.beans.property.*;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import Algorithms.AnomalyDetectionAlgorithm;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ViewModel extends Observable implements Observer {
    public static Model m;
    public static View view;
    public static AnomalyDetectionAlgorithm algorithm;
    public static IconsController ic;

    public FloatProperty aileron = new SimpleFloatProperty(0);
    public FloatProperty elevator = new SimpleFloatProperty(0);
    public FloatProperty rudder = new SimpleFloatProperty(0);
    public FloatProperty throttle = new SimpleFloatProperty(0);
    public IntegerProperty currentFlightTime = new SimpleIntegerProperty(0);
    public IntegerProperty speedMultySlider = new SimpleIntegerProperty(0);
    public StringProperty yaw = new SimpleStringProperty("0");
    public StringProperty roll = new SimpleStringProperty("0");
    public StringProperty pitch = new SimpleStringProperty("0");
    public StringProperty speed = new SimpleStringProperty("0");
    public StringProperty direction = new SimpleStringProperty("0");
    public StringProperty altitude = new SimpleStringProperty("0");
    public StringProperty currentTimeString = new SimpleStringProperty("0");

//    public SimpleDoubleProperty rate = new SimpleDoubleProperty();

    public ViewModel(Model m){
        ViewModel.m = m;
        m.addObserver(this);
    }

    public ViewModel() {
        view = new View();
        ic = view.ic;
        m.addObserver(this);
        ic.addObserver(this);
    }

    public void start() { m.readConfig(); }

    public void startFlight(){ Model.simulatorApi.startToFly(); }

    @Override
    public void update(Observable o, Object arg) {
//        System.out.println(o.toString());
        if (o == view) {

            if (arg.equals("play")) {
                play();
                setChanged();
            }
            if (arg.equals("pause")) {
                pause();
                setChanged();
            }
            if (arg.equals("stop")) {
                stop();
                setChanged();
            }
            if (arg.equals("slow")) {
                speedDown();
                setChanged();
            }
            if (arg.equals("fast")) {
                speedUp();
                setChanged();
            }
            if (arg.equals("slower")) {
                doubleSpeedDown();
                setChanged();
            }
            if (arg.equals("faster")) {
                doubleSpeedUp();
                setChanged();
            }
        }
    }

    public void openCSV(){ m.openCSVFile(); }
    public TextField getSpeedText(){return IconsController.SpeedText;}
    public Slider getSpeedSlider(){return IconsController.SpeedSlider;}
    public void bind(){ view.bind();}
    public void play() {m.play(); }
    public void pause(){ m.pause(); setChanged(); notifyObservers();}
    public void stop(){ m.stop(); setChanged(); notifyObservers();}
    public void speedUp(){ m.speedUp(); setChanged(); notifyObservers(); }
    public void speedDown(){ m.speedDown(); setChanged(); notifyObservers();}
    public void doubleSpeedUp(){ m.doubleSpeedUp(); setChanged(); notifyObservers();}
    public void doubleSpeedDown(){ m.doubleSpeedDown(); setChanged(); notifyObservers();}
    public void change(){ m.changeSpeedText(); }
    public void change2(){ m.changeInSpeedSlider(); }
    public int getFlightLength() { return Model.simulatorApi.getFlightLength(); }
    public int getCurrentTime(){return Model.simulatorApi.getCurrentTime();}
    public float getParameter(String s){ return Model.simulatorApi.getParameter(s); }
    public String[] getFlightData(){return Model.simulatorApi.getData();}
    public List<String> getDataList(){return Model.simulatorApi.getDataList();}
    public int getFlightDataIndex(int MS){return Model.simulatorApi.getFlightDataIndexByMs(MS); }
    public int getParameterIndex(String s){return Model.simulatorApi.getFlightParameterIndex(s);}
    public void setCurrentFlightTime(int time){ Model.simulatorApi.setCurrentTime(time);}
}
