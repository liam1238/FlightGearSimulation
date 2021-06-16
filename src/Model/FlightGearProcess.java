package Model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//A proxy process for the simulator
public class FlightGearProcess extends Process {
	Process flightGear;

//	  Starts the FlightGear process in it's own directory, with arguments to open sockets for communication to it
	public FlightGearProcess(String path, int inPort, int outPort) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(path + "/bin/fgfs.exe", "--generic=socket,in," + (FlightGearAPI.defaultDelay / 10)
				+ ",127.0.0.1," + outPort + ",tcp,playback_small", "--generic=socket,out," + (FlightGearAPI.defaultDelay / 10) + ",127.0.0.1,"
				+ inPort + ",tcp,playback_small", "--fdm=null");
		pb.directory(new File(path + "/bin"));
		flightGear = pb.start();
	}
	
	//proxy functions
	@Override
	public OutputStream getOutputStream() {
		return flightGear.getOutputStream();
	}

	@Override
	public InputStream getInputStream() {
		return flightGear.getInputStream();
	}

	@Override
	public InputStream getErrorStream() {
		return flightGear.getErrorStream();
	}

	@Override
	public int waitFor() throws InterruptedException { return flightGear.waitFor(); }

	@Override
	public int exitValue() {
		return flightGear.exitValue();
	}

	@Override
	public void destroy() {
		flightGear.destroy();
	}
	
}
