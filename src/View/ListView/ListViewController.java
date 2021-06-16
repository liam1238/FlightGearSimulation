package View.ListView;

import View.Icons.IconsController;
import View.Main;
import ViewModel.ViewModel;
import application.Utils;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ListViewController {
    ViewModel vm;

    @FXML ListView listView;
    @FXML Button openButton;
    StringProperty XmlPath;
    IconsController ic;

    public ListViewController() {
        super();
        XmlPath = new SimpleStringProperty();
        ic = new IconsController();
        vm = Main.viewModel;
    }

    public void onClickOpen() {
        vm.openCSV(); // call the model to choose a file and use it to display the flight over the flight gear

        //set slider events
        ic.onMouseMovedTimeSlider();
        ic.onMouseMovedSpeedSlider();

        //set current time updater
        Timer currentTimer = new Timer();
        currentTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {  //update time slider as time goes on...
                    if (Main.isTimeSliding)
                        return;
                    ((Slider)Utils.getNodeByID("currentFlightTimeSlider")).setValue(Main.viewModel.simulatorApi.getCurrentFlightTime());
                    ((Label)Utils.getNodeByID("currentFlightTimeLabel")).setText(Utils.msToTimeString(Main.viewModel.simulatorApi.getCurrentFlightTime()));

                    if (Main.viewModel.simulatorApi.getCurrentFlightTime() >= Main.viewModel.simulatorApi.getFlightLength()) //when flight finished, reset the timers
                        ic.onClickStop();
                });
            }
        }, 0, 100);

        //set joystick updater
        Timer joystickTimer = new Timer();
        joystickTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    //get flight parameter values and set them to joystick sliders
                    float rudder = Main.viewModel.simulatorApi.getFlightParameter("rudder");
                    if (rudder > -999)
                        ((Slider)Utils.getNodeByID("rudderSlider")).setValue(rudder);
                    float throttle = Main.viewModel.simulatorApi.getFlightParameter("throttle");
                    if (throttle > -999)
                        ((Slider)Utils.getNodeByID("throttleSlider")).setValue(throttle);

                    //get flight parameter values and set them to the joystick circle's offset
                    float aileron = Main.viewModel.simulatorApi.getFlightParameter("aileron");
                    float elevator = Main.viewModel.simulatorApi.getFlightParameter("elevator");
                    if (aileron > -999 && elevator > -999) {
                        Circle joystick = (Circle)Utils.getNodeByID("joystickCircle");
                        joystick.setTranslateX(aileron*150);
                        joystick.setTranslateY(elevator*-150);
                    }
                });
            }
        }, 0, 100);

        //set dashboard panel updater
        Timer dashboardTimer = new Timer();
        dashboardTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    //get flight parameter values and set them to gauges
                    ((Label)Utils.getNodeByID("altitudeLabel")).setText(Main.viewModel.simulatorApi.getFlightParameter("altimeter_indicated-altitude-ft")+"");
                    ((Label)Utils.getNodeByID("airspeedLabel")).setText(Main.viewModel.simulatorApi.getFlightParameter("airspeed-indicator_indicated-speed-kt")+"");
                    ((Label)Utils.getNodeByID("headingLabel")).setText(Main.viewModel.simulatorApi.getFlightParameter("indicated-heading-deg")+"");
                    ((Label)Utils.getNodeByID("rollLabel")).setText(Main.viewModel.simulatorApi.getFlightParameter("attitude-indicator_indicated-roll-deg")+"");
                    ((Label)Utils.getNodeByID("pitchLabel")).setText(Main.viewModel.simulatorApi.getFlightParameter("attitude-indicator_internal-pitch-deg")+"");
                    ((Label)Utils.getNodeByID("yawLabel")).setText(Main.viewModel.simulatorApi.getFlightParameter("heading-deg")+"");
                });
            }
        }, 0, 100);

        //set graphs updater
        Timer graphTimer = new Timer();
        graphTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!Main.paramSelected) //don't run if graphs are uninitiated
                        return;

                    //erase graph data - so we can also support time jumps
                    ((LineChart)Utils.getNodeByID("paramGraph1")).getData().clear();
                    ((LineChart)Utils.getNodeByID("paramGraph2")).getData().clear();
                    ((Canvas)Utils.getNodeByID("anomalyCanvas1")).getGraphicsContext2D().clearRect(0, 0, ((Canvas)Utils.getNodeByID("anomalyCanvas1"))
                            .getWidth(), ((Canvas)Utils.getNodeByID("anomalyCanvas1")).getHeight());

                    //create series for param graphs (start from 00:00:05 and move one second for each graph node)
                    XYChart.Series series = new XYChart.Series();
                    for (int time = 5000; time < Main.viewModel.simulatorApi.getCurrentFlightTime(); time += 1000)
                        try { series.getData().add(new XYChart.Data(time, Float.parseFloat(Main.viewModel.simulatorApi.getFlightData()[Main.viewModel.simulatorApi
                                .getFlightDataIndexByMsTime(time)].split(",")[((ListView)Utils.getNodeByID("parameterListView")).getSelectionModel()
                                .getSelectedIndex()]))); }
                    catch (Exception e) {e.printStackTrace(); }

                    ((LineChart)Utils.getNodeByID("paramGraph1")).getData().add(series); //assign series
                    series = new XYChart.Series();
                    for (int time = 5000; time < Main.viewModel.simulatorApi.getCurrentFlightTime(); time += 1000)
                        try { series.getData().add(new XYChart.Data(time, Float.parseFloat(Main.viewModel.simulatorApi.getFlightData()[Main.viewModel.simulatorApi
                                .getFlightDataIndexByMsTime(time)].split(",")[Main.viewModel.simulatorApi.getFlightParameterIndex((Main.viewModel.plugin
                                .getCorrelated(((ListView)Utils.getNodeByID("parameterListView")).getSelectionModel().getSelectedItem().toString())))])));
                        } catch (Exception e) {e.printStackTrace(); }

                    ((LineChart)Utils.getNodeByID("paramGraph2")).getData().add(series); //assign series

                    //draw anomaly graph on canvas via plugin
                    Main.viewModel.plugin.drawOnGraph((Canvas)Utils.getNodeByID("anomalyCanvas1"), ((ListView)Utils.getNodeByID("parameterListView")).getSelectionModel()
                            .getSelectedItem().toString(), Main.viewModel.simulatorApi.getFlightDataIndexByMsTime(Main.viewModel.simulatorApi.getCurrentFlightTime()));
                });
            }
        }, 0, 100);

        //set lists-view values
        List<String> flightData;
        flightData = Main.viewModel.simulatorApi.getFlightDataList();
        ((ListView)Utils.getNodeByID("parameterListView")).getItems().addAll(flightData);
        ((ListView)Utils.getNodeByID("classListView")).getItems().addAll(new String[] { "SimpleAnomalyDetector", "ZScoreAlgorithm", "HybridAlgorithm" });
        ((ListView)Utils.getNodeByID("classListView")).getSelectionModel().select(0);
        onMouseClickedClassListView(); //update detection plugin to default
    }

    public void onMouseClickedClassListView() {
        //update current plugin
        Utils.loadPlugin(((ListView)Utils.getNodeByID("classListView")).getSelectionModel().getSelectedItem().toString());
    }

    public void onMouseClickedParameterListView() {
        //update category axis label
        Main.paramSelected = true;
        ((NumberAxis)Utils.getNodeByID("paramCategoryAxis1")).setLabel((String)(((ListView)Utils.getNodeByID("parameterListView")).getSelectionModel().getSelectedItems().get(0)));
        ((NumberAxis)Utils.getNodeByID("paramCategoryAxis2")).setLabel(Main.viewModel.plugin.getCorrelated(((ListView)Utils.getNodeByID("parameterListView")).getSelectionModel().getSelectedItem().toString()));
    }

}
