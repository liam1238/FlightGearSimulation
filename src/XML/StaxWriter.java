package XML;

import java.io.*;
import java.util.Iterator;
import java.util.List;

public class StaxWriter
{
    public void Writer (List<Item> configFile)
    {
        String name, node;
        final String AILERON = "aileron";
        final String ELEVATOR = "elevator";
        final String RUDDER = "rudder";
        final String FLAPS = "flaps";
        final String SPEEDBRAKE = "speedbrake";
        final String THROTTLE = "throttle";
        final String LATITUDE = "latitude-deg";
        final String LONGITUDE = "longitude-deg";
        final String ALTITUDE = "altitude-ft";
        final String PITCH = "pitch-deg";
        final String ROLL = "roll-deg";
        final String HEADING = "heading-deg";
        final String YAW = "side-slip-deg";
        final String AIRSPEED = "airspeed-kt";
        final String V_SPEED = "vertical-speed-fps";
        final String ENGINE = "engine_rpm";
        // clocks:
        final String AIRSPEED_IND = "airspeed-indicator_indicated-speed-kt";
        final String ALTIMETER = "altimeter_indicated-altitude-ft";
        final String ALTIMETER_PRESSURE = "altimeter_pressure-alt-ft";
        final String ALTITUDE_IND_PITCH = "attitude-indicator_indicated-pitch-deg";
        final String ALTITUDE_IND_ROLL = "attitude-indicator_indicated-roll-deg";
        final String ALTITUDE_IND_PITCH_INT = "attitude-indicator_internal-pitch-deg";
        final String GPS_ALTITUDE = "gps_indicated-altitude-ft";
        final String GPS_GROUND = "gps_indicated-ground-speed-kt";
        final String GPS_VERTICAL = "gps_indicated-vertical-speed";
        final String INDICATED_HEADING = "indicated-heading-deg";
        int i = 0;
        try
        {
            FileWriter myWriter = new FileWriter("filename.txt"); //changed to bufferedWriter
            Item item = new Item();

            for (i =1; i<configFile.size() +1; i++)
            {
                item = configFile.get(i-1);
                name = item.getName();
                switch (name)
                {
                    case INDICATED_HEADING:
                    case GPS_VERTICAL:
                    case GPS_GROUND:
                    case GPS_ALTITUDE:
                    case ALTITUDE_IND_PITCH_INT:
                    case ALTITUDE_IND_ROLL:
                    case ALTITUDE_IND_PITCH:
                    case ALTIMETER_PRESSURE:
                    case ALTIMETER:
                    case AIRSPEED_IND:
                    case V_SPEED:
                    case AIRSPEED:
                    case ENGINE:
                    case YAW:
                    case HEADING:
                    case ROLL:
                    case PITCH:
                    case ALTITUDE:
                    case LONGITUDE:
                    case LATITUDE:
                    case THROTTLE:
                    case SPEEDBRAKE:
                    case FLAPS:
                    case RUDDER:
                    case AILERON:
                    case ELEVATOR:
                        myWriter.write(name + ",");
                        node = item.getNode();
                        myWriter.write( "set" + " " + node + "\n");
                        // i = מספר העמודה בקובץ ה-CSV
                        break;
                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

}
