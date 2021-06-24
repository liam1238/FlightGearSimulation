package Algorithms;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Commands {
	
	// Default IO interface
	public interface DefaultIO{
		String readText();
		void write(String text);
		float readVal();
		void write(float val);
	}
	
	// the default IO to be used in all commands
	DefaultIO dio;
	public Commands(DefaultIO dio, int id) {
		this.dio=dio;
		sharedState.train = "anomalyTrain" + id + ".csv";
		sharedState.test = "anomalyTest" + id + ".csv";
	}
	
	// you may add other helper classes here
	public String upload()
	{
		String data = "";
		String line = "";
		while (!((line = dio.readText()).equals("done")))
			data += line + "\n";
		return data.substring(0, data.length()-1);
	}
	
	public void uploadFile(String path)
	{
		PrintWriter out = null;
		String data = upload();
		try {
			out = new PrintWriter(new FileWriter(path));
			out.write(data);
			out.close();
		} catch(IOException e) {e.printStackTrace();}
	}
	
	// the shared state of all commands
	private static class SharedState {
		SimpleAnomalyDetector anomalyDetector = new SimpleAnomalyDetector();
		String train;
		String test;
	}
	
	private final SharedState sharedState = new SharedState();

	
	// Command abstract class
	public abstract static class Command {
		protected String description;
		
		public Command(String description) {
			this.description=description;
		}
		
		public abstract void execute();
	}
	
	public class UploadCSVCommand extends Command {
		public UploadCSVCommand() {
			super("upload a time series csv file");
		}
		
		@Override
		public void execute() {
			dio.write("Please upload your local train CSV file.\n");
			uploadFile(sharedState.train);
			dio.write("Upload complete.\n");
			dio.write("Please upload your local test CSV file.\n");
			uploadFile(sharedState.test);
			dio.write("Upload complete.\n");
		}
	}
	
	public class ThresholdCommand extends Command {
		public ThresholdCommand() {
			super("algorithm settings");
		}
		
		@Override
		public void execute() {
			dio.write("The current correlation threshold is " + sharedState.anomalyDetector.maxThreshold + "\n");
			dio.write("Type a new threshold" + "\n");
			
			String input = dio.readText();
			if (input == "") {
				execute();
				return;
			}
			try {
				float threshold = Float.parseFloat(input);
				if (threshold < 0 || threshold > 1) {
					dio.write("please choose a value between 0 and 1.\n");
					execute();
					return;
				}
				sharedState.anomalyDetector.maxThreshold = threshold;
			} catch (NumberFormatException e) {
				dio.write("please choose a value between 0 and 1.\n");
				execute();
			}
		}
	}
	
	public class RunAlgorithmCommand extends Command {
		public RunAlgorithmCommand() 
		{
			super("detect anomalies");
		}
		
		@Override
		public void execute() {
			TimeSeries trainFile = new TimeSeries(sharedState.train), testFile = testFile = new TimeSeries(sharedState.test);
			
			sharedState.anomalyDetector.learnNormal(trainFile);
			sharedState.anomalyDetector.detect(testFile);
			
			dio.write("anomaly detection complete.\n");
		}
	}
	
	public class PrintReportsCommand extends Command {
		public PrintReportsCommand() {
			super("display results");
		}
		
		@Override
		public void execute() {
			for (AnomalyReport detection : sharedState.anomalyDetector.reported)
				dio.write(detection.timeStamp + "\t" + detection.description + "\n");
			dio.write("Done.\n");
		}
	}
	
	public class CommandFive extends Command {		
		public CommandFive() {
			super("upload anomalies and analyze results");
		}
		
		@Override
		public void execute() {
			
			dio.write("Please upload your local anomalies file.\n");
			String realTime = upload();
			dio.write("Upload complete.\n");
			
			float[] rates = sharedState.anomalyDetector.analyzeTimestamps(realTime);
			if (String.valueOf(rates[0]).length() > 5)
				rates[0] = Float.parseFloat(String.valueOf(rates[0]).substring(0, 5));
			if (String.valueOf(rates[1]).length() > 5)
				rates[1] = Float.parseFloat(String.valueOf(rates[1]).substring(0, 5));
			dio.write("True Positive Rate: " + rates[0] + "\n");
			dio.write("False Positive Rate: " + rates[1] + "\n");
		}
	}
}

