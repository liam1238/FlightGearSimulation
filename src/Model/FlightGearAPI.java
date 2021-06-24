package Model;

import javafx.scene.control.Alert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//Class that contains the functions as an API for communication with the FlightGear flight simulator
	public class FlightGearAPI extends Observable implements SimulatorAPI {
	ConfigurationFile config = Model.config;

	public static class ParametersOfFlight { //A wrapper class that contains a flight parameter
	public String _name;
	public float _value;

		public ParametersOfFlight(String name, float value) {
			_name = name; _value = value;
		}
	}

	String ip = "127.0.0.1";
	public static final int defaultDelay = 50; //the default delay in m.s. for communication with the simulator
	private Socket socketIn = null;
	private Process simulator;
	private Thread dataInThread;
	private simulationHandler dataHandler;
	private Thread dataOutThread;
	private final Timer flightTimer = new Timer();

	private final List<ParametersOfFlight> flightData = new ArrayList<>(); //simulation data - updates in real time

	public List<String> getDataList() {
		 List<String> flightData = new ArrayList<>();
		for (ParametersOfFlight flightDatum : this.flightData) flightData.add(flightDatum._name);
		 return flightData;
	}
	@Override
	public void startToFly() {
		System.out.println("trying to start flying...");
		try { //use API to send flight data
			loadFromCSV(config.flight_data_csv);
			sendDataToSimulator();
		} catch (IOException e) {
			new Alert(Alert.AlertType.ERROR, "ERROR: Could not send flight data XML to simulator").showAndWait();
			e.printStackTrace();
			return;
		}
		System.out.println("start to fly!");

		//state updates
		flightTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				setChanged();
				notifyObservers();
			}
		}, 0, 100);
	}

	//Reads flight parameters from socket and parse them into the flightData
	private void updateFlightDataFromSocket() {
		String[] line = socketIn.readline().split(",");
		for (int i = 0; i < line.length; i++) {
			try {
				flightData.get(i)._value = Float.parseFloat(line[i]); //keep the parameters order

			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				flightData.get(i)._value = -9999; }
		}
	}

	//handle changes in flight parameters during the flight
	private void updateDataLive() {
		while (true) {
			updateFlightDataFromSocket();
			try { Thread.sleep(defaultDelay); } catch (InterruptedException e) {e.printStackTrace(); }
		}
	}

	//read playback xml into Document and parse it
	private void FromXMLToFlightData() {
		File playback = new File(config.simulator_playback);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc;
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(playback);
		} catch (ParserConfigurationException | SAXException | IOException e) { e.printStackTrace(); return; }
		doc.getDocumentElement().normalize();

		//go over all chunks in output
		NodeList nodeList = ((Element)(doc.getElementsByTagName("output").item(0))).getElementsByTagName("chunk");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			Element e = (Element) node;
			//for each chunk add a ParametersOfFlight to the list (with the chunk's name and an initial value)
			flightData.add(new ParametersOfFlight(e.getElementsByTagName("name").item(0).getTextContent(), -9999));
		}
	}

	//copy the "playback_small.xml" file from the project's directory to the simulators protocol path
	private void copyPlaybackToSimulator() throws IOException {
		File sourceFile = new File(config.simulator_playback);
		File destFile = new File(config.simulator_path + "/data/protocol/playback_small.xml");
		if (!sourceFile.exists())
	        return;
	    if (!destFile.exists())
	        destFile.createNewFile();

	    //transfer the data
	    FileChannel source;
	    FileChannel destination;
	    source = new FileInputStream(sourceFile).getChannel();
	    destination = new FileOutputStream(destFile).getChannel();
	    if (source != null)
	        destination.transferFrom(source, 0, source.size());

	    //close the files
	    if (source != null)
	        source.close();
		destination.close();
	}

	public FlightGearAPI() throws IOException {
		FromXMLToFlightData();
		copyPlaybackToSimulator();
	}

	  //open flight gear, wait for connection and then starts the updateSimDataAgent
	@Override
	public void start() throws IOException, InterruptedException {

		simulator = new FlightProcess(config.simulator_path, config.simulator_input_port, config.simulator_output_port);

		//open server socket, wait for simulator to finish uploading, than start the update thread
		socketIn = new Socket(config.simulator_input_port);
		Thread.sleep(config.init_sleep_seconds* 1000L);
		dataInThread = new Thread(this::updateDataLive);
		dataInThread.start();
	}

	@Override
	public void finalize() {
		if (dataOutThread!=null)
			dataOutThread.stop();
		if (dataHandler!=null)
			dataHandler.close();
		if (dataInThread!=null)
			dataInThread.stop();
		if (socketIn!=null)
			socketIn.close();
		if (simulator!=null)
			simulator.destroy();
	}

	@Override
	public void loadFromCSV(String filename) throws IOException {  //load CSV file with flight data
		Socket simOut = new Socket(ip, config.simulator_output_port);
		dataHandler = new simulationHandler(simOut, new String (Files.readAllBytes(Paths.get(filename))).split("\n"),
				config.playback_sample_rate_ms, config.playback_speed_multiplayer); //read all lines from the CSV and instantiate dataHandler
	}

	@Override
	public float getParameter(String paramName) {
		for (ParametersOfFlight param : flightData)
			if (param._name.equals(paramName))
				return param._value;
		return 0;
	}

	@Override
	public int getFlightParameterIndex(String name) {
		for (int i = 0; i < flightData.size(); i++)
			if (flightData.get(i)._name.equals(name))
				return i;
		return -1;
	}

		@Override
	public void setSimulationSpeed(float speedMultiplayer) { dataHandler.setFlightSpeed(speedMultiplayer); }

	@Override //send flight data to the simulator (preloaded)
	public void sendDataToSimulator() {
		dataOutThread = new Thread(()->{
			try {
				dataHandler.sendFlightDataToSimulator();
			} catch (InterruptedException e) { e.printStackTrace(); }
		});
		dataOutThread.start();
	}

	@Override
	public void setCurrentTime(int currentTimeInMS) { dataHandler.setCurrentFlightTime(currentTimeInMS); }

	@Override
	public int getFlightLength() { return dataHandler.getFlightLength(); }

	@Override
	public int getCurrentTime() { return dataHandler.getCurrentFlightTime(); }

	@Override
	public String[] getData() { return dataHandler.getFlightData(); }

	@Override
	public int getFlightDataIndexByMs(int MS) { return dataHandler.getFlightDataIndexByMsTime(MS); }
}

