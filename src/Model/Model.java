package Model;

import View.Main;
import ViewModel.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Observable;

public class Model extends Observable implements ModelInterface  {

    StringProperty CsvPath;
    ViewModel vm;
    public static ConfigurationFile config;
    public static SimulatorAPI simulatorApi;

    public Model(){ CsvPath = new SimpleStringProperty(); }

    @Override
    public void openCSVFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("CSV File Choose");
        fc.setInitialDirectory(new File("resources"));
        FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("CSV Files (*.csv)","*.csv");
        fc.getExtensionFilters().add(ef);
        File chosen = fc.showOpenDialog(null);
        if(chosen==null) {
            new Alert(Alert.AlertType.ERROR, "Error: Invalid Selected CSV File, please try again").showAndWait();
            return;
        }
        CsvPath.setValue("resources/" +chosen.getName());
        CsvPath.setValue("anomaly_flight.csv/"+chosen.getName());
        config.flight_data_csv = chosen.getAbsolutePath();
        config.playback_speed_multiplayer = 0; //start on pause mode

        vm = Main.viewModel;
        this.addObserver(vm);
    }

    @Override //play by setting speed to 1
    public void play() { changeFlightSpeed(1); }

    @Override //pause by setting speed to 0
    public void pause() { changeFlightSpeed(0); }

    @Override  //pause and stop the flight. current time will be 0
    public void stop() { pause(); simulatorApi.setCurrentTime(0); }

    @Override //increase speed
    public void speedUp() { changeFlightSpeed(1.75f); }

    @Override //decrease speed
    public void speedDown() { changeFlightSpeed(0.5f); }

    @Override //increase speed twice
    public void doubleSpeedUp() { changeFlightSpeed(2f); }

    @Override //decrease speed twice
    public void doubleSpeedDown() { changeFlightSpeed(0.25f); }

    public void changeSpeedText(){
        try {
            //parse the text and update the simulation speed
            float speed = Float.parseFloat(vm.getSpeedText().getText());
            if (speed < 0 || speed > 5)
                throw new Exception("Invalid speed value");
            changeFlightSpeed(speed);
        } catch(Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: Invalid speed multiplayer value: must be a number between 0 and 5").showAndWait();
            changeFlightSpeed(1); } //change speed back to 1
    }

    public void changeFlightSpeed(float speed) {
        speed = Float.parseFloat((new DecimalFormat("0.00")).format(speed));
        vm.getSpeedText().setText(speed + ""); //update speed text field
        vm.getSpeedSlider().setValue((int)(speed * 100)); //update speed slider
        setChanged();
        notifyObservers();
        simulatorApi.setSimulationSpeed(speed); //set simulation speed
    }

    public void changeInSpeedSlider(){ changeFlightSpeed(((float)(vm.speedMultySlider.getValue()))/100); } //set flight speed

    public void readConfig() {
        try {
            config = ConfigurationFile.readConfigFromXML("config.xml");
            simulatorApi = new FlightGearAPI();
            simulatorApi.start();
        } catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Critical error: Could not read config / playback XML / Invalid simulator path in config").showAndWait();
        }
    }
}
