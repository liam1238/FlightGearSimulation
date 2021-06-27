package Algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TimeSeries {

	public ArrayList<String> valueNames;
	public ArrayList<ArrayList<Float>> values;

	public TimeSeries()
	{
		valueNames = new ArrayList<>();
		values = new ArrayList<>();
	}
	public TimeSeries(ArrayList<String> names,ArrayList<ArrayList<Float>> values)
	{
		valueNames = names;
		this.values = values;
	}

	public TimeSeries(String csvFileName)
	{
		valueNames = new ArrayList<>();
		values = new ArrayList<>();
        boolean flag = true;
        try {
        BufferedReader reader = new BufferedReader(new FileReader(csvFileName));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] vals = line.split(",");
            if (flag)
            {
                for (String value : vals) {
                    valueNames.add(value);
                    values.add(new ArrayList<Float>());
                }
                flag = false;
            } else
                for (int i = 0; i < vals.length; i++)
                    values.get(i).add(Float.parseFloat(vals[i]));
        }
        reader.close();
		} catch (IOException e) { e.printStackTrace(); }
	}

	public ArrayList<String> getValueNames() {
		return valueNames;
	}

	public void setValueNames(ArrayList<String> valueNames) {
		this.valueNames = valueNames;
	}

	public ArrayList<ArrayList<Float>> getValues() {
		return values;
	}

	public void setValues(ArrayList<ArrayList<Float>> values) {
		this.values = values;
	}

	public void addValue(String name, Float val)
	{
		if(valueNames.contains(name))
			values.get(valueNames.indexOf(name)).add(val);
		else
			{
				valueNames.add(name);
				values.add(new ArrayList<Float>());
				values.get(values.size()-1).add(val);
			}
	}

	public Float valueInSpecificTime(Float time, String name)
	{
		int neededIndex = -1;
		for(int i = 0; i < values.get(0).size(); i++)
			if(values.get(0).get(i).equals(time))
				neededIndex = i;
		if(neededIndex != -1)
			return values.get(valueNames.indexOf(name)).get(neededIndex);
		return null;
	}
}

