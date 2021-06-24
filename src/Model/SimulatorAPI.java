package Model;

import java.io.IOException;
import java.util.List;

//Interface for the flight simulator communication
public interface SimulatorAPI {

	void start() throws IOException, InterruptedException;
	void finalize();
	void loadFromCSV(String filename) throws IOException;
	float getParameter(String name);
	void setSimulationSpeed(float speedMultiPlayer);
	void sendDataToSimulator();
	void setCurrentTime(int currentTimeInMS);
	int getFlightLength();
	int getCurrentTime();
	String[] getData();
	List<String> getDataList();
	int getFlightDataIndexByMs(int MS);
	int getFlightParameterIndex(String name);
	void startToFly();

}
