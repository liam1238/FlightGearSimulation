package Model;

import View.Main;
import application.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Observable;

public class Model extends Observable implements ModelInterface  {
    public Runnable play,pause,stop, speedUp, speedDown, doubleSpeedUp, doubleSpeedDown;
    StringProperty CsvPath;

    public Model(){
        CsvPath = new SimpleStringProperty();
    }

    @Override
    public void openCSVFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("CSV File Choose");
        fc.setInitialDirectory(new File("./resources"));
        FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("CSV Files (*.csv)","*.csv");
        fc.getExtensionFilters().add(ef);
        File chosen = fc.showOpenDialog(null);
        if(chosen==null) {
            new Alert(Alert.AlertType.ERROR, "Error: Invalid Selected CSV File, please try again").showAndWait();
            return;
        }
        CsvPath.setValue("resources/"+chosen.getName());
        CsvPath.setValue("anomaly_flight.csv/"+chosen.getName());
        Main.viewModel.conf.flight_data_csv = chosen.getAbsolutePath();
        Main.viewModel.conf.playback_speed_multiplayer = 0; //start on pause mode

        System.out.println("trying to start flying...");
        try { //use API to send flight data
            Main.viewModel.simulatorApi.loadFlightDataFromCSV(Main.viewModel.conf.flight_data_csv);
            Main.viewModel.simulatorApi.sendFlightDataToSimulator();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: Could not send flight data to the simulator").showAndWait();
            return;
        }
        System.out.println("start to fly!");
        Utils.setDisableALL(false); //enable all the icons after we started to fly
        Utils.getNodeByID("openButton").setDisable(true); //disable open button after first use
        ((Label)Utils.getNodeByID("totalFlightTimeLabel")).setText(Utils.msToTimeString(Main.viewModel.simulatorApi.getFlightLength())); //update total flight time label
        ((Slider)Utils.getNodeByID("currentFlightTimeSlider")).setMax(Main.viewModel.simulatorApi.getFlightLength()); //update flight time slider
    }

    @Override //play by setting speed to 1
    public void play() { changeSpeedAndUpdateGUI(1); }

    @Override //pause by setting speed to 0
    public void pause() { changeSpeedAndUpdateGUI(0); }

    @Override  //pause and stop the flight. current time will be 0
    public void stop() { pause(); Main.viewModel.simulatorApi.setCurrentFlightTime(0); }

    @Override //increase speed
    public void speedUp() { changeSpeedAndUpdateGUI(1.75f); }

    @Override //decrease speed
    public void speedDown() { changeSpeedAndUpdateGUI(0.5f); }

    @Override //increase speed twice
    public void doubleSpeedUp() { changeSpeedAndUpdateGUI(2f); }

    @Override //decrease speed twice
    public void doubleSpeedDown() { changeSpeedAndUpdateGUI(0.25f); }

    public void changeTextFiled(){
        try {
            //parse the text and update the simulation speed
            float speedMulty = Float.parseFloat(((TextField) Utils.getNodeByID("speedMultyTextfield")).getText());
            if (speedMulty < 0 || speedMulty > 5)
                throw new Exception("Invalid speed value");
            changeSpeedAndUpdateGUI(speedMulty);
        } catch(Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: Invalid speed multiplayer value: must be a number between 0 and 5").showAndWait();
            changeSpeedAndUpdateGUI(1); } //change speed back to 1
    }

    public void changeSpeedAndUpdateGUI(float speedMulty) {
        speedMulty = Float.parseFloat((new DecimalFormat("0.00")).format(speedMulty));
        ((TextField)Utils.getNodeByID("speedMultyTextfield")).setText(speedMulty + ""); //update speed text field
        ((Slider)Utils.getNodeByID("speedMultySlider")).setValue((int)(speedMulty * 100)); //update speed slider
        Main.viewModel.simulatorApi.setSimulationSpeed(speedMulty); //set simulation speed
    }

    public void changeInSpeedSlider(){
        changeSpeedAndUpdateGUI(((float)(((Slider)Utils.getNodeByID("speedMultySlider")).getValue()))/100); //set flight speed
    }

}
