package ViewModel;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

//contain any global configurations for the simulator
public class ConfigurationFile {

	public String simulator_path;
	public String simulator_playback;
	public String flight_data_csv;
	public float playback_speed_multiplayer;
	public int playback_sample_rate_ms;
	public int simulator_input_port;
	public int simulator_output_port;
	public int init_sleep_seconds;

//	  Reads a ConfigurationFile from an XML using XMLDecoder
	public static ConfigurationFile readConfigFromXML(String xmlPath) throws FileNotFoundException {
		XMLDecoder decoder = new XMLDecoder(new FileInputStream(xmlPath));
		return (ConfigurationFile) decoder.readObject();
	}

//	  Writes a ConfigurationFile to an XML using XMLEncoder
	public static void saveConfigToXML(ConfigurationFile conf, String xmlPath) throws FileNotFoundException {
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(xmlPath)));
		encoder.writeObject(conf);
		encoder.close();
	}
}
