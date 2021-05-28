package Model;

import java.util.List;
import java.util.Map;

public interface ModelInterface {
    public void openCSVFile(String propertiesFileName);
    public TimeSeries setTimeSeries(String CSVFile);
    public void setAnomalyDetector(TimeSeries timeSeries);
    public void play (int start, int rate);
    public void pause();
    public void stop();
    public void speedUp();
    public void speedDown();
    public void doubleSpeedUp();
    public void doubleSpeedDown();

    public Map<String, List<String>> getMap();
}
