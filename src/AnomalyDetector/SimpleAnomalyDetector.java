package AnomalyDetector;

import Model.TimeSeries;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static AnomalyDetector.StatLib.*;

public class SimpleAnomalyDetector implements TimeSeriesAnomalyDetector {
    private ArrayList<CorrelatedFeatures> correlatedFeatures;

    public SimpleAnomalyDetector(){ }

    @Override
    public void learnNormal(TimeSeries ts)  {
        correlatedFeatures = new ArrayList<CorrelatedFeatures>();
        float threshold = 0.9f;
        int flag = 0, flag2 = 1, count = 0;
        int times = (ts.fd.features.size() * (ts.fd.features.size() -1) / 2);
        int length = (ts.fd.getNumberOfValues() / ts.fd.features.size());

        while (flag != times){
            float[] x = new float[length], y = new float[length];

            Point[] points = new Point[x.length];
            String maxFeature = "";
            float maxPearsonValue = 0;
            for (int i=0;i<length;i++){
                x[i] = (float)ts.fd.f[count].get(i);
                y[i] = (float)ts.fd.f[flag2].get(i);
            }
            float pearsonResult = pearson(x,y);

            if(pearsonResult > maxPearsonValue)
                maxPearsonValue = pearsonResult;

            if(pearsonResult > threshold){
                maxFeature = ts.fd.getFeatureName(flag2);
                for(int j = 0; j < length; j++)
                    points[j] = new Point((int)x[j],(int)y[j]);
            }
            if (!(maxFeature.equals(""))){
                Line line = linear_reg(points); // קו רגרסיה
                float max_deviation = 0; // היסט מקסימלי
                for(Point p: points) {
                    float dev_result = dev(p, line);
                    if(dev_result > max_deviation) {
                        max_deviation = dev_result;
                    }
                }

                CorrelatedFeatures cf = new CorrelatedFeatures(ts.fd.getFeatureName(count), maxFeature, maxPearsonValue, line, max_deviation);
                this.correlatedFeatures.add(cf);
            }
            if(flag2 < ts.fd.features.size()-1)
                flag2++;
            else {
                count++;
                flag2 = count+1;
            }
            flag++;
        }
    }

    @Override
    public List<AnomalyReport> detect (TimeSeries ts){
        ArrayList<AnomalyReport> anomalyReports = new ArrayList<AnomalyReport>();
        int length = (ts.fd.getNumberOfValues() / ts.fd.features.size()), count = 0, flag = 1;
        float[] x = new float[length];
        float[] y = new float[length];
        for(CorrelatedFeatures cf: this.correlatedFeatures){
            x = ts.fd.getArrayOfValues(cf.feature1);
            y = ts.fd.getArrayOfValues(cf.feature2);

            for (int j = 0; j < length; j++) {
                Point point = new Point((int)x[j],(int)y[j]);
                if(dev(point,cf.lin_reg) > cf.threshold) {
                    String description = cf.feature1 + "-" + cf.feature2;
                    anomalyReports.add(new AnomalyReport(description, (long)j+1));
                }
            }
        }
        return anomalyReports;
    }

    public List<CorrelatedFeatures> getNormalModel () {
        return this.correlatedFeatures;
    }
}

