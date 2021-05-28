package Model;

import java.io.*;
import java.util.*;

public class TimeSeries {
    public TimeSeries() {}

    public FeaturesData fd = new FeaturesData();

    public TimeSeries(FeaturesData fd) {
        this.fd.f = new ArrayList[fd.NumberOfValues];
        this.fd.features = new ArrayList<>(fd.features.size());
        for (int i=0; i< fd.features.size(); i++)
            this.fd.f[i] = fd.f[i];
    }

    public static class FeaturesData {
        public List<String> features;
        public ArrayList[] f;
        public int NumberOfValues;

        public int getNumberOfValues() {
            return NumberOfValues;
        }  // returns the number of values in the file

        public String getFeatureName(int index) {
            if (index <= features.size())
                return this.features.get(index);
            else {
                System.out.println("the index is bigger then the size of features");
                return null;
            }
        }

        public float[] getArrayOfValues(String s) { // i starts from zero
            int length = this.features.size();
            float[] value = new float[this.getNumberOfValues() / length];
            for (int i = 0; i < length; i++) {
                if (this.features.get(i).equals(s)) {
                    for (int j = 0; j < this.getNumberOfValues() / length; j++)
                        value[j] = (float) this.f[i].get(j);
                }
            }
            return value;
        }
    }

    public TimeSeries readFromFile(String csvFileName) {
        try {
            StringBuilder Sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(csvFileName));
            String st;
            st = br.readLine(); // read the first line
            Sb.append(st);
            Sb.append(",");
            int length = (Sb.toString().split(",").length); // the number of features in the file
            List<String> array;
            while ((st = br.readLine()) != null) {
                //System.out.println(st);
                Sb.append(st);
                Sb.append(",");
            }
            array = Arrays.asList(Sb.toString().split(","));

            fd.features = Arrays.asList(new String[length]);
            for (int i = 0; i < length; i++) {
                fd.features.set(i, array.get(i));
            }
            int tmp = ((Sb.toString().split(",").length));
            fd.NumberOfValues = ((Sb.toString().split(",").length) - fd.features.size()); // the number of values in the file
            int count = 0;
            fd.f = new ArrayList[length];
            int flag = 0;
            while (flag != fd.features.size()) {
                count = 0;
                fd.f[flag] = new ArrayList<>();
                for (int j = fd.features.size() + flag; j < tmp; j += fd.features.size()) {
                    fd.f[flag].add(count, Float.valueOf(array.get(j)));
                    count++;
                }
                flag++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TimeSeries(fd);
    }
}