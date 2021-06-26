# FlightGearSimulation
Application to detect anomalies in flight using flight gear simulator.

In order to use our application, you should downdload the "flight gear simulator" from this website: "https://www.flightgear.org/", and put him in this exact path: "C:/Program Files/FlightGear 2020.3".

The application will open the simulator by her self (using a config file we made) and will open a Socket in the simulator using the localhost ip (127.0.0.1) and port 5400.

After the simulator opens the server side, the application will connect to him and will open the GUI for the user.

On click on the "Load CSV File", the user will give the application a CSV file (from the resources directory), and the flight will load to the simulator to show and to the GUI to controll it.
