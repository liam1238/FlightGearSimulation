package Algorithms;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ZScoreAnomalyDetector implements AnomalyDetectionAlgorithm
{
	TimeSeries ts;
	List<Float> values;
	ArrayList<ArrayList<Float>> allZScoreValues = new ArrayList<>();

	public ZScoreAnomalyDetector() { values = new ArrayList<>(); }

	public float CalcAvg(TimeSeries ts, int col, int end)
	{
		float avg = 0;
		for(int i = 0; i < end; i++)
			avg += ts.getValues().get(col).get(i);
		return avg / end;
	}

	public float[] cutArray(TimeSeries ts, int col, int end)
	{
		float[] arr = new float[end];
		for(int i = 0; i < end; i++)
			arr[i] = ts.getValues().get(col).get(i);
		return arr;
	}

	public float ZScore(TimeSeries ts, int col, int end)
	{
		float avg = CalcAvg(ts,col, end);
		return Math.abs(ts.values.get(col).get(end) - avg) / (float)Math.sqrt(StatLib.var(cutArray(ts,col, end)));
	}

	public void learnNormal (File train)
	{
		TimeSeries ts = new TimeSeries(train.toString());
		learnNormal(ts);
	}

	public void learnNormal (TimeSeries ts)
	{
		for(int j = 0; j < ts.values.size(); j++)
		{
			allZScoreValues.add(new ArrayList<>());
			for(int i = 0; i < ts.getValues().get(j).size(); i++)
				allZScoreValues.get(j).add(ZScore(ts,j,i));
		}

		float max, current;
		for(int i = 0; i <ts.values.size(); i++)
		{
			max = 0;
			for(int j = 0; j < ts.values.get(i).size(); j++)
			{
				if((current = ZScore(ts,i,j)) > max)
					max = current;
			}
			values.add(max);
		}
	}

	public List<AnomalyReport> detect (File anomalyFile)
	{
		TimeSeries ts = new TimeSeries(anomalyFile.toString());
		return detect(ts);
	}

	public List<AnomalyReport> detect (TimeSeries ts)
	{
		this.ts = ts;
		List<AnomalyReport> anomalies = new ArrayList<>();
		float current;
		for(int i = 0; i < ts.getValues().size(); i++) {
			for(int j = 0; j < ts.getValues().get(i).size(); j++)
			{
				current = ZScore(ts,i,j);
				if(i < values.size() && current > values.get(i))
					anomalies.add(new AnomalyReport(ts.getValueNames().get(i),i+1,i+1,current));
			}
		}
		return anomalies;
	}

	@Override
	public void drawOnGraph(Canvas canvas, String featureName, int timeStamp)
	{
		ArrayList<Point> points = new ArrayList<>();
		for(int i = 0; i < timeStamp; i++)
			points.add(new Point(allZScoreValues.get(ts.getValueNames().indexOf(featureName)).get(i), i+1));

		canvas.getGraphicsContext2D().setStroke(Color.BLACK);
		for(int i = 0; i < timeStamp-1; i++)
			canvas.getGraphicsContext2D().strokeLine(points.get(i).x % 200, points.get(i).y % 200, points.get(i+1).x % 200, points.get(i+1).y % 200);
	}

	@Override
	public String getCorrelated(String feature) {
		return feature;
	}
}
