package Algorithms;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class HybridAnomalyDetector implements AnomalyDetectionAlgorithm
{
	private ZScoreAnomalyDetector zScoreAnomalyDetector;
	private LinearRegAnomalyDetector linearRegAlgorithm;
	private List<AnomalyReport> reports;
	private TimeSeries ts_ZScore, ts_Linear_Reg, main_TimeSeries;//for each algorithm

	public float high_Correlation, low_Correlation;
	public ArrayList<Point> circlePoints = new ArrayList<>();
	public ArrayList<CircleColumnPairing> array = new ArrayList<>();
	public ArrayList<ArrayList<Float>> values_For_Linear_Reg = new ArrayList<>();
	public ArrayList<ArrayList<Float>> values_For_Z = new ArrayList<>();
	public ArrayList<ArrayList<Float>> values_For_Welzl = new ArrayList<>();
	public ArrayList<String> features_For_Linear_Reg = new ArrayList<>();
	public ArrayList<String> features_For_Z = new ArrayList<>();
	public ArrayList<String> features_For_Welzl = new ArrayList<>();

	public HybridAnomalyDetector()
	{
		linearRegAlgorithm = new LinearRegAnomalyDetector();
		zScoreAnomalyDetector = new ZScoreAnomalyDetector();
		high_Correlation = (float) 0.98;
		low_Correlation = (float) 0.5;
	}

	public HybridAnomalyDetector(float highCorr, float lowCorr)
	{
		linearRegAlgorithm = new LinearRegAnomalyDetector();
		zScoreAnomalyDetector = new ZScoreAnomalyDetector();
		this.high_Correlation = highCorr;
		this.low_Correlation = lowCorr;
	}

	public int MatchAlgorithm(String name)
	{
		float max_Correlation = 0;
		for(int i = 0; i < linearRegAlgorithm.getCorrelated().size(); i++)
		{
			if((linearRegAlgorithm.getCorrelated().get(i).feature1.compareTo(name) == 0 || linearRegAlgorithm.getCorrelated().get(i).feature2.compareTo(name) == 0)
					&& Math.abs(linearRegAlgorithm.getCorrelated().get(i).correlation) > max_Correlation)
				max_Correlation = Math.abs(linearRegAlgorithm.getCorrelated().get(i).correlation);
		}
		if(max_Correlation >= high_Correlation)
			return 1;
		if(max_Correlation < low_Correlation)
			return -1;
		for(CorrelatedFeatures corr : linearRegAlgorithm.getCorrelated())
			if(corr.feature1.equals(name) || corr.feature2.equals(name))
				return 0;
		return -1;
	}

	public void learnNormal (File trainFile)
	{
		main_TimeSeries = new TimeSeries(trainFile.toString());
		linearRegAlgorithm.learnNormal(main_TimeSeries);
		for(int i = 0; i < main_TimeSeries.valueNames.size(); i++)
		{
			int result = MatchAlgorithm(main_TimeSeries.valueNames.get(i));
			if(result == 1)
			{
				features_For_Linear_Reg.add(main_TimeSeries.getValueNames().get(i));
				values_For_Linear_Reg.add(main_TimeSeries.getValues().get(i));
			} else if (result == -1)
			{
				features_For_Z.add(main_TimeSeries.getValueNames().get(i));
				values_For_Z.add(main_TimeSeries.getValues().get(i));
			}
			else
				{
				features_For_Welzl.add(main_TimeSeries.getValueNames().get(i));
				values_For_Welzl.add(main_TimeSeries.getValues().get(i));
				}
		}
		ts_ZScore = new TimeSeries(features_For_Z, values_For_Z);
		ts_Linear_Reg = new TimeSeries(features_For_Linear_Reg, values_For_Linear_Reg);
		linearRegAlgorithm.learnNormal(ts_Linear_Reg);
		zScoreAnomalyDetector.learnNormal(ts_ZScore);
		for(CorrelatedFeatures corr : linearRegAlgorithm.getCorrelated())
			if(features_For_Welzl.contains(corr.feature1) && features_For_Welzl.contains(corr.feature2))
				array.add(new CircleColumnPairing(corr.c,corr.feature1,corr.feature2));
	}

	public List<AnomalyReport> detect(File testFile)
	{
		linearRegAlgorithm.detect(testFile);
		zScoreAnomalyDetector.detect(testFile);
		reports = new ArrayList<>();
		main_TimeSeries = new TimeSeries(testFile.toString());
		ArrayList<ArrayList<Float>> allFeatureOnes = new ArrayList<ArrayList<Float>>();
		ArrayList<ArrayList<Float>> allFeatureTwos = new ArrayList<ArrayList<Float>>();
		for(CircleColumnPairing ccp : array)
		{
			allFeatureOnes.add(main_TimeSeries.getValues().get(main_TimeSeries.getValueNames().indexOf(ccp.ft1)));
			allFeatureTwos.add(main_TimeSeries.getValues().get(main_TimeSeries.getValueNames().indexOf(ccp.ft2)));
		}
		for(int i = 0; i < allFeatureOnes.size(); i++)
		{
			for(int j = 0; j < allFeatureOnes.get(i).size(); j++)
			{
				Point p = new Point(allFeatureOnes.get(i).get(j),allFeatureTwos.get(i).get(j));
				circlePoints.add(p);
				if(!array.get(i).c.contains(p))
					reports.add(new AnomalyReport(array.get(i).ft1 + "-" + array.get(i).ft2, i+1, allFeatureOnes.get(i).get(j),allFeatureTwos.get(i).get(j)));
			}
		}
		return reports;
	}

	@Override
	public void drawOnGraph(Canvas canvas, String featureName, int timeStamp) {
		if(MatchAlgorithm(featureName) == 1)
			linearRegAlgorithm.drawOnGraph(canvas, featureName, timeStamp);
		else if(MatchAlgorithm(featureName) == -1)
			zScoreAnomalyDetector.drawOnGraph(canvas, featureName, timeStamp);
		else
			{
			Circle circle = null;
			for(CircleColumnPairing cop : array)
				if(cop.ft1.compareTo(featureName) == 0 || cop.ft2.compareTo(featureName) == 0)
					circle = cop.c;
			canvas.getGraphicsContext2D().setStroke(Color.BLACK);
			assert circle != null;
			canvas.getGraphicsContext2D().strokeOval(Math.round(Math.abs(circle.centerPoint.x-circle.Radius)) % 200, Math.round(Math.abs(circle.centerPoint.y-circle.Radius)) % 200, (circle.Radius *2) % 200, (circle.Radius *2) % 200);
			canvas.getGraphicsContext2D().setStroke(Color.RED);
			for(Point p : circlePoints)
				canvas.getGraphicsContext2D().strokeLine(p.x % 200, p.y % 200, p.x % 200, p.y % 200);
			ArrayList<AnomalyReport> list = new ArrayList<>(reports);
			list.removeIf(value -> !value.description.contains(featureName) || value.timeStep > timeStamp);
			for(AnomalyReport ar : list)
				canvas.getGraphicsContext2D().strokeLine(ar.x % 200, ar.y % 200, ar.x % 200, ar.y % 200);
			}
	}
	
	@Override
	public String getCorrelated(String feature)
	{
		for (CorrelatedFeatures c : linearRegAlgorithm.getCorrelated()) {
			if (c.feature1.equals(feature))
				return c.feature2;
			if (c.feature2.equals(feature))
				return c.feature1;
		}
		return feature;
	}
}
