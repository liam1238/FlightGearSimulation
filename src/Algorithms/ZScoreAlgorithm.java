package Algorithms;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ZScoreAlgorithm implements AnomalyDetectionAlgorithm {
	TimeSeries timeS;
	List<Float> txValues;
	ArrayList<ArrayList<Float>> allZScoreValues = new ArrayList<>();

	public ZScoreAlgorithm() { txValues = new ArrayList<>(); }

	//calculating the average of a specific feature from start to index "until"
	public float calcAvg(TimeSeries ts, int col, int until) {
		float avg = 0;
		for(int i = 0; i < until; i++)
			avg += ts.getValues().get(col).get(i);
		return avg / until;
	}

	//returns values in specific feature from index 0 to "until"
	public float[] cutArray(TimeSeries ts, int col, int until) {
		float[] arr = new float[until];
		for(int i = 0; i < until; i++)
			arr[i] = ts.getValues().get(col).get(i);
		return arr;
	}

	//calculates Z score for specific feature, from start to "until"
	public float ZScore(TimeSeries ts, int col, int until) {
		float avg = calcAvg(ts,col, until);
		return Math.abs(ts.values.get(col).get(until) - avg) / (float)Math.sqrt(StatLib.var(cutArray(ts,col,until)));
	}

	//calculate Tx
	public void learnNormal (File trainFile) {
		TimeSeries ts = new TimeSeries(trainFile.toString());
		learnNormal(ts);
	}

	//we fill the ZScore matrix
	public void learnNormal (TimeSeries ts) {
		for(int j = 0; j < ts.values.size(); j++) {
			allZScoreValues.add(new ArrayList<>());
			for(int i = 0; i < ts.getValues().get(j).size(); i++)
				allZScoreValues.get(j).add(ZScore(ts,j,i));
		}

		//here we calculate Tx, according to explanation in the manual
		float max, curr;
		for(int i = 0; i <ts.values.size(); i++) {
			max = 0;
			for(int j = 0; j < ts.values.get(i).size(); j++) {
				if((curr = ZScore(ts,i,j)) > max)
					max = curr;
			}
			txValues.add(max);
		}
	}
	public List<AnomalyReport> detect (File testFile) {
		TimeSeries ts = new TimeSeries(testFile.toString());
		return detect(ts);
	}

	public List<AnomalyReport> detect (TimeSeries ts) {
		timeS = ts;
		List<AnomalyReport> anomalies = new ArrayList<>();
		float current;
		for(int i = 0; i < ts.getValues().size(); i++) {
			for(int j = 0; j < ts.getValues().get(i).size(); j++) {	//check if current ZScore is over the "not problematic" limit, calculated as Tx in learnNormal
				current = ZScore(ts,i,j);
				if(i < txValues.size() && current > txValues.get(i))
					anomalies.add(new AnomalyReport(ts.getValueNames().get(i),i+1,i+1,current));//IMPORTANT!!! CHANGE TIMESTAMP, X,Y WHEN YOU KNOW WHAT TO PUT IN IT
			}
		}
		return anomalies;
	}

	@Override
	public void drawOnGraph(Canvas canvas, String featureName, int timeStamp) {
		ArrayList<Point> points = new ArrayList<>();
		for(int i = 0; i < timeStamp; i++)
			points.add(new Point(allZScoreValues.get(timeS.getValueNames().indexOf(featureName)).get(i), i+1));
		
		//stroke ZScore lines (via points)
		canvas.getGraphicsContext2D().setStroke(Color.BLACK);
		for(int i = 0; i < timeStamp-1; i++)
			canvas.getGraphicsContext2D().strokeLine(points.get(i).x % 200, points.get(i).y % 200, points.get(i+1).x % 200, points.get(i+1).y % 200);
	}

	@Override
	public String getCorrelated(String feature) {
		return feature;
	}

}