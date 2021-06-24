package Model;

public class simulationHandler {
	private final Socket simOut;
	private final String[] flightData;
	
	private final int flightLength; //the length of the flight in m.s.
	private int currentTimeInMS = 0; //the current flight time in m.s.
	private int timeJumpMS = FlightGearAPI.defaultDelay; //the amount of ms to skip each iteration (used for flight speed change)
	private final float timeCorrection; //inner value used to convert times to match the sample rate
	
	public simulationHandler(Socket socketOut, String[] flightData, int sampleRate, float speedMultiplayer) {
		simOut = socketOut;
		this.flightData = flightData;
		timeCorrection = (float)FlightGearAPI.defaultDelay / (float)sampleRate;
		timeJumpMS *= speedMultiplayer * timeCorrection;
		flightLength = this.flightData.length * sampleRate;
	}
	
	public int getFlightDataIndexByMsTime(long msTime) { return (int)((msTime/FlightGearAPI.defaultDelay)* timeCorrection); }

	//send flightData to the simulator

	public void sendFlightDataToSimulator() throws InterruptedException {
		for (; currentTimeInMS < flightLength; currentTimeInMS += timeJumpMS) {
			simOut.writeLine(flightData[getFlightDataIndexByMsTime(currentTimeInMS)]);
			Thread.sleep(FlightGearAPI.defaultDelay);
		}
	}
	
	public void setFlightSpeed(float speedMultiPlayer) { timeJumpMS = (int)(FlightGearAPI.defaultDelay * (speedMultiPlayer * timeCorrection)); }

	public int getFlightLength() {
		return flightLength;
	}
	
	public void setCurrentFlightTime(int currentTimeInMs) { currentTimeInMS = currentTimeInMs; }
	
	public int getCurrentFlightTime() {
		return currentTimeInMS;
	}

	public String[] getFlightData() { return flightData; }
	
	public void close() { simOut.close(); }
}
