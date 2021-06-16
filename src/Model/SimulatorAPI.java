package Model;

import java.io.IOException;
import java.util.List;

//Interface for the flight simulator communication
public interface SimulatorAPI {

	void start() throws IOException, InterruptedException;
	void finalize();
	void loadFlightDataFromCSV(String filename) throws IOException;
	float getFlightParameter(String paramName);
	void setSimulationSpeed(float speedMultiPlayer);
	void sendFlightDataToSimulator();
	void setCurrentFlightTime(int currentTimeInMS);
	int getFlightLength();
	int getCurrentFlightTime();
	String[] getFlightData();
	List<String> getFlightDataList();
	int getFlightDataIndexByMsTime(int MSTime);
	int getFlightParameterIndex(String paramName);
}
