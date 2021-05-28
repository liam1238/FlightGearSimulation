package Model;

import AnomalyDetector.SimpleAnomalyDetector;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Model extends Observable implements ModelInterface {
    //private SimulatorClient simulatorClient;
    Map<String, List<String>> properties;
    List <String> names;
    List<List<String>> list = new ArrayList<>();
    Socket fg;
    int k = 0, size = 0;
    PrintWriter out2fg;
    TimeSeries timeSeries = new TimeSeries();

    @Override
    public Map<String, List <String>> getMap() {
        return properties;
    }

    public Model() {
        int port = 5402;
        String ip = "127.0.0.1";
      /*  try{
            fg = new Socket(ip, port);
            out2fg = new PrintWriter(fg.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

        public void setAileron(double x) {
            String command = properties.get("aileron").get(0);
            out2fg.println(command + " " + x);
            out2fg.flush();
        }

            public void setElevators(double x) {
                String command = properties.get("elevators").get(0);
                out2fg.println(command + " " + x);
                out2fg.flush();
            }

            public void setRudder(double x) {
                String command = properties.get("rudder").get(0);
                out2fg.println(command + " " + x);
                out2fg.flush();
            }

            public void setThrottle(double x) {
                String command = properties.get("throttle").get(0);
                out2fg.println(command + " " + x);
                out2fg.flush();
            }

    @Override
    public void finalize() {
        try {
            out2fg.close();
            fg.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openCSVFile(String propertiesFileName) {
       // simulatorClient = new SimulatorClient();
        properties = new LinkedHashMap<>();
        String sp[];
        try {
            BufferedReader in = new BufferedReader(new FileReader(propertiesFileName));
            String line;
            while ((line = in.readLine()) != null) {
                sp = line.split(",");
                names = new ArrayList<>();
                names.addAll(Arrays.asList(sp));
                list.add(k,names);
                k++;
            }
            size = names.size();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=0; i<size;i++) {
            names = new ArrayList<>();
            for (int j = 1; j < list.size(); j++)
                names.add(String.valueOf(list.get(j).get(i)));
            properties.put(list.get(0).get(i), names);
        }
        //int x = 0; //just for debug
        setChanged();
        notifyObservers();
    }

    @Override
    public TimeSeries setTimeSeries(String CSVFile) {
        timeSeries.readFromFile(CSVFile);
        setChanged();
        notifyObservers();
        return timeSeries;
    }

    @Override
    public void setAnomalyDetector(TimeSeries timeSeries) {
        SimpleAnomalyDetector anomalyDetector = new SimpleAnomalyDetector();
        anomalyDetector.learnNormal(timeSeries);
        anomalyDetector.detect(timeSeries);
        anomalyDetector.getNormalModel();
        setChanged();
        notifyObservers();
    }

    @Override
    public void play(int start, int rate) {


        setChanged();
        notifyObservers();
    }

    @Override
    public void pause() {


        setChanged();
        notifyObservers();
    }

    @Override
    public void stop() {


        setChanged();
        notifyObservers();
    }

    @Override
    public void speedUp(){


        setChanged();
        notifyObservers();
    }

    @Override
    public void speedDown(){


        setChanged();
        notifyObservers();
    }

    @Override
    public void doubleSpeedUp(){


        setChanged();
        notifyObservers();
    }

    @Override
    public void doubleSpeedDown(){


        setChanged();
        notifyObservers();
    }


}

