package View.ListView;

import Model.Model;
import View.Graphs.GraphsController;
import View.Icons.IconsController;
import View.Joystick.JoystickController;
import View.Main;
import ViewModel.Service;
import ViewModel.ViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ListViewController {

    public static ViewModel vm;

    @FXML ListView listView;
    @FXML Button openButton;
    @FXML ListView<String> ListView;
    @FXML ListView<String> parametersList;

    StringProperty XmlPath;
    public static IconsController ic;
    public static JoystickController jc;
    int flightLength;

    public ListViewController() {
        super();
        vm = Main.viewModel;
        XmlPath = new SimpleStringProperty();
        ic = new IconsController();
        jc = new JoystickController();
    }

        public void setListView(){
        openButton = (Button) Service.getNodeByID("openButton");
        parametersList = (ListView<String>) Service.getNodeByID("parametersList");
        ListView = (ListView<String>) Service.getNodeByID("ListView");
    }

    public void onClickOpen() {
        vm.openCSV(); // call the model to choose a file and use it to display the flight over the flight gear

        vm.startFlight();
        flightLength = vm.getFlightLength();
        Service.setDisableALL(false); //enable all the icons after we started to fly
        openButton.setDisable(true); //disable open button after first use
        IconsController.TotalFlightTime.setText(Service.msToTimeString(vm.getFlightLength())); //update total flight time label
        IconsController.FlightSlider.setMax(vm.getFlightLength()); //update flight time slider

       //ic.onMouseMovedTimeSlider();
        ic.onMouseMovedSpeedSlider();

        vm.bind();

        //set current time updater
        Timer currentTimer = new Timer();
        currentTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {  //update time slider as time goes on...
                    if (Main.SliderTimeTouch)
                        return;
                    IconsController.FlightSlider.setValue(vm.getCurrentTime());
                    IconsController.LabelCurrentTime.setText(Service.msToTimeString(vm.getCurrentTime()));

                    if (vm.getCurrentTime() >= flightLength) //when flight finished, reset the timers
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
                    float rudder = vm.getParameter("rudder");
                    if (rudder > -999)
                        JoystickController.rudderSlider.setValue(rudder);
                    float throttle = vm.getParameter("throttle");
                    if (throttle > -999)
                       JoystickController.throttleSlider.setValue(throttle);

                    //get flight parameter values and set them to the joystick circle's offset
                    float aileron = vm.getParameter("aileron");
                    float elevator = vm.getParameter("elevator");
                    if (aileron > -999 && elevator > -999) {
                        Circle joystick = JoystickController.movingCircle;
                        joystick.setTranslateX(aileron*150);
                        joystick.setTranslateY(elevator*-150);
                    }
                });
            }
        }, 0, 100);

                jc.setDashboardPanel();

        //set graphs updater
        Timer graphTimer = new Timer();
        graphTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!Main.ChooseParameter) //don't run if graphs are uninitiated
                        return;

                    //erase graph data - so we can also support time jumps
                    GraphsController.Graph1.getData().clear();
                    GraphsController.Graph2.getData().clear();
                    GraphsController.Canvas.getGraphicsContext2D().clearRect(0, 0, GraphsController.Canvas
                            .getWidth(), GraphsController.Canvas.getHeight());

                    //create series for param graphs (start from 00:00:05 and move one second for each graph node)
                    XYChart.Series series = new XYChart.Series();
                    for (int time = 5000; time < vm.getCurrentTime(); time += 1000)
                        try { series.getData().add(new XYChart.Data(time, Float.parseFloat(vm.getFlightData()[vm.getFlightDataIndex(time)].split(",")[parametersList.getSelectionModel()
                                .getSelectedIndex()]))); }
                    catch (Exception e) {e.printStackTrace(); }

                    GraphsController.Graph1.getData().add(series); //assign series
                    series = new XYChart.Series();
                    for (int time = 5000; time < Model.simulatorApi.getCurrentTime(); time += 1000)
                        try { series.getData().add(new XYChart.Data(time, Float.parseFloat(vm.getFlightData()[vm.getFlightDataIndex(time)].split(",")[vm.getParameterIndex((ViewModel.algorithm
                                .getCorrelated(parametersList.getSelectionModel().getSelectedItem())))])));
                        } catch (Exception e) {e.printStackTrace(); }

                    GraphsController.Graph2.getData().add(series); //assign series

                    //draw anomaly graph on canvas via algorithm
                    ViewModel.algorithm.drawOnGraph(GraphsController.Canvas, parametersList.getSelectionModel()
                            .getSelectedItem(), vm.getFlightDataIndex(vm.getCurrentTime()));
                });
            }
        }, 0, 100);

        //set lists-view values
        List<String> flightData;
        flightData = vm.getDataList();
        parametersList.getItems().addAll(flightData);
        ListView.getItems().addAll("SimpleAnomalyDetector", "ZScoreAlgorithm", "HybridAlgorithm");
        ListView.getSelectionModel().select(0);
        onMouseClickedListView(); //update detection algorithm to default
        ic.onMouseMovedTimeSlider();
    }

    public void onMouseClickedListView() {
        //update current algorithm
        Service.loadAlgorithm(ListView.getSelectionModel().getSelectedItem());
    }

    public void onMouseClickedParametersList() {
        Main.ChooseParameter = true;
        //gets parameter that we choose
        GraphsController.chosenParameter.setLabel((parametersList.getSelectionModel().getSelectedItems().get(0)));
        //gets the correlated parameter
        GraphsController.correlatedParameter.setLabel(ViewModel.algorithm.getCorrelated(parametersList.getSelectionModel().getSelectedItem()));
    }

}
