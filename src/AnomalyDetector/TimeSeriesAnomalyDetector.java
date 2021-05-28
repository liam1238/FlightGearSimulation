package AnomalyDetector;

import Model.TimeSeries;

import java.util.List;

public interface TimeSeriesAnomalyDetector {
    public void learnNormal(TimeSeries ts);
    public List<AnomalyReport> detect (TimeSeries ts);

}
