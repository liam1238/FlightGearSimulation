package Algorithms;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

public class LinearRegAnomalyDetector implements TimeSeriesAnomalyDetector, AnomalyDetectionAlgorithm
{
	private final ArrayList<CorrelatedFeatures> correlated;
	public int steps = 0;
	public List<AnomalyReport> reports;
	public float maxThreshold = 0.9f;
	public HashMap<CorrelatedFeatures,Line> map = new HashMap<>();
	private TimeSeries ts;

	public LinearRegAnomalyDetector()
	{
		correlated = new ArrayList<>();
		reports = new ArrayList<>();
	}

	public ArrayList<CorrelatedFeatures> getCorrelated()
	{
		return correlated;
	}
	public void learnNormal(TimeSeries ts)
	{
		float threshold = (float)0.9;
		float[] firstValue, secondValue;
		int maxPearsonIndex = -1;
		float currentPearson = 0, maxPearson = 0;

		for(int i = 0; i < ts.values.size(); i++)
		{
			maxPearsonIndex = -1;
			maxPearson = 0;
			firstValue = new float[ts.values.get(i).size()];
			for(int k = 0; k < firstValue.length; k++)
				firstValue[k] = ts.values.get(i).get(k);

			for(int j = i + 1; j < ts.values.size(); j++)
			{
				secondValue = new float[ts.values.get(j).size()];
				for(int l = 0; l < secondValue.length; l++)
					secondValue[l] = ts.values.get(j).get(l);
				if(i != j && (Math.abs(currentPearson = StatLib.pearson(firstValue, secondValue)) > threshold))
				{
					if(Math.abs(currentPearson) > Math.abs(maxPearson)) {
						maxPearsonIndex = j;
						maxPearson = currentPearson;
					}
				}
			}
			Point[] points = new Point[firstValue.length];
			if(maxPearsonIndex != -1)
			{
				for(int m = 0; m < points.length; m++)
					points[m] = new Point(firstValue[m], ts.values.get(maxPearsonIndex).get(m));
				Line le = StatLib.linear_reg(points);
				CorrelatedFeatures cf = new CorrelatedFeatures(ts.getValueNames().get(i), ts.getValueNames().get(maxPearsonIndex),maxPearson ,le, maxDevFromLine(points, le),MinimalCircle.CreateCircle(Arrays.asList(points)));
				correlated.add(cf);
				map.put(cf, le);
			}
		}
	}

	public float maxDevFromLine(Point[] points, Line l)
	{
		float max = 0, curr = 0;
		for(Point p : points) {
			if((curr = StatLib.dev(p, l)) > max)
				max = curr;
		}
		return max;
	}

	@Override
	public List<AnomalyReport> detect(TimeSeries ts)
	{
		this.ts = ts;

		int index1 = -1, index2 = -1;
		List<AnomalyReport> anomalyReports = new LinkedList<>();
		for(int i = 0; i < ts.values.get(0).size(); i++)
		{
			for (CorrelatedFeatures correlatedFeatures : correlated)
			{
				index1 = ts.getValueNames().indexOf(correlatedFeatures.feature1);
				index2 = ts.getValueNames().indexOf(correlatedFeatures.feature2);
				if (index1 != -1 && index2 != -1)
				{
					Point p = new Point(ts.values.get(index1).get(i), ts.values.get(index2).get(i));
					if (StatLib.dev(p, correlatedFeatures.lin_reg) > (correlatedFeatures.threshold + 0.35))
					{
						String s = String.join("-", correlatedFeatures.feature1, correlatedFeatures.feature2);
						anomalyReports.add(new AnomalyReport(s, i + 1, ts.values.get(ts.valueNames.indexOf(correlatedFeatures.feature1)).get(i + 1),
								ts.values.get(ts.valueNames.indexOf(correlatedFeatures.feature2)).get(i + 1)));
					}
				}
			}
		}
		steps = ts.values.get(0).size();
		reports = anomalyReports;
		return anomalyReports;
	}


	public List<CorrelatedFeatures> getNormalModel(){
		return correlated;
	}

	public float[] analyzeTimestamps(String ts)
	{
		StringBuilder s = new StringBuilder();
		int i = 1;
		while (i < reports.size())
		{
			long timeStep = reports.get(i-1).timeStep;
			long j = timeStep;
			while (i < reports.size() && reports.get(i).timeStep == reports.get(i-1).timeStep +1 && reports.get(i).description.equals(reports.get(i-1).description))
			{
				i++;
				j++;
			}
			s.append(timeStep).append(",").append(j).append("\n");
			i++;
		}

		int FP = 0;
		int TP = 0;
		String[] str1 = ts.split("\n");
		String[] str2 = s.toString().split("\n");
		int P = str1.length;
		int N = steps;

		for (String value : str1)
		{
			String[] data1 = value.split(",");
			int[] inted1 = new int[data1.length];
			inted1[0] = Integer.parseInt(data1[0]);
			inted1[1] = Integer.parseInt(data1[1]);
			N -= (inted1[1] - inted1[0] + 1);
		}

		for (i = 0; i < str2.length; i++)
		{
			String[] data2 = str2[i].split(",");
			int[] inted2 = new int[data2.length];
			inted2[0] = Integer.parseInt(data2[0]);
			inted2[1] = Integer.parseInt(data2[1]);
			boolean fpflag = false;

			for (String value : str1)
			{
				String[] data = value.split(",");
				int[] inted = new int[data.length];
				inted[0] = Integer.parseInt(data[0]);
				inted[1] = Integer.parseInt(data[1]);

				if ((inted2[0] <= inted[0] && inted2[1] >= inted[1]) || (inted2[0] >= inted[0] && inted2[0] <= inted[1]) || (inted2[1] >= inted[0] && inted2[1] <= inted[1])) {
					TP++;
					fpflag = true;
					break;
				}
			}
			if (!fpflag)
				FP++;
		}
		return new float[] { (float)TP/(float)P, (float)FP/(float)N };
	}

	@Override
	public void learnNormal(File learn)
	{
		TimeSeries ts = new TimeSeries(learn.toString());
		learnNormal(ts);
	}

	@Override
	public List<AnomalyReport> detect(File file)
	{
		TimeSeries ts = new TimeSeries(file.toString());
		return detect(ts);
	}

	@Override
	public void drawOnGraph(Canvas canvas, String feature, int timeStamp)
	{
		Line l = new Line(0,0);
		//draw the points
		ArrayList<Point> points = new ArrayList<>();
		int index = -1;
		for(int i = 0; i < correlated.size(); i++)
			if(correlated.get(i).feature1.compareTo(feature) == 0 || correlated.get(i).feature2.compareTo(feature) == 0)
				index = i;
		if (index == -1)
			return;
		for(int i = 0; i < timeStamp; i++)
			points.add(new Point(ts.values.get(ts.valueNames.indexOf(correlated.get(index).feature1)).get(i), ts.values.get(ts.valueNames.indexOf(correlated.get(index).feature1)).get(i)));
		for(Entry<CorrelatedFeatures, Line> e : map.entrySet()) {
			if(e.getKey().feature1.compareTo(feature) == 0 || e.getKey().feature2.compareTo(feature) == 0)
				l = e.getValue();
		}

		ArrayList<AnomalyReport> list = new ArrayList<>(reports);
		list.removeIf(value -> !value.description.contains(feature) || value.timeStep > timeStamp);

		canvas.getGraphicsContext2D().setStroke(Color.BLACK);
		canvas.getGraphicsContext2D().strokeLine(1 % 200, l.f(1) % 200, timeStamp % 200, l.f(timeStamp) % 200);

		canvas.getGraphicsContext2D().setStroke(Color.RED);
	    for(Point p: points)
			canvas.getGraphicsContext2D().strokeLine(p.x % 200, p.y % 200, p.x % 200, p.y % 200);
	    for(AnomalyReport ar: list)
			canvas.getGraphicsContext2D().strokeLine(ar.x % 200, ar.y % 200, ar.x % 200, ar.y % 200);
	}

	@Override
	public String getCorrelated(String feature)
	{
		for (CorrelatedFeatures c : correlated)
		{
			if (c.feature1.equals(feature))
				return c.feature2;
			if (c.feature2.equals(feature))
				return c.feature1;
		}
		return feature;
	}
}
