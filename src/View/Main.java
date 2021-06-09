package View;

import Model.Model;
import Model.SimulatorClient;
import ViewModel.viewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        Parent root = fxmlLoader.load(getClass().getResource("FlightSimulator.fxml").openStream());
//        primaryStage.setTitle("Flight Simulator");
//        Controller c = fxmlLoader.getController();
//        c.init();
//        primaryStage.setScene(new Scene(root, 700, 420));
//        primaryStage.show();
//        Model model = new Model();
//        viewModel viewModel = new viewModel(model);
//        model.addObserver(viewModel);
//        Controller controller = fxmlLoader.getController();
//        //viewModel.addObserver(controller);
//        String ip = "127.0.0.1";
//        int port = 5402;
//        SimulatorClient simulatorClient = new SimulatorClient();
//        //simulatorClient.Connect(ip,port);

        Parent root = FXMLLoader.load(getClass().getResource("FlightSimulator.fxml"));
        primaryStage.setTitle("Flight Simulator");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
        int port = 5402;
        String ip = "127.0.0.1";
        SimulatorClient simulatorClient = new SimulatorClient();
       // simulatorClient.Connect(ip,port);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
