# FlightGearSimulation
This Application aim is to detect anomalies in flight using Flight Gear simulator.

In order to use our application, you should downdload the "flight gear simulator" from this website: "https://www.flightgear.org/", and install it in this exact path: "D:/Program Files/FlightGear 2020.3".

The application will open the simulator automaticlly (using a config file we have made) and will open a Socket in the simulator using the localhost ip (127.0.0.1) and port 5400.

After that the simulator will open the server side, the application will connect to simulator and will open the GUI for the user.

By clicking on the "Load CSV File", the user will choose a CSV file (from the resources directory), and then the flight will load to the simulator.

Now, the user can control the speed of the flight using the GUI buttons.

During the flight screening the user can watch anomalies that was detected during the flight, this made possible by anomaly detection algorithms.

For more information about our project, please watch this video: "https://www.youtube.com/watch?v=NxkmcO_SPiE".

To see the UML (Class & Sequence Diagrams) + our project presentation, please go to "Class & Sequence Diagrams" directory. 

![flight gear simulator](https://user-images.githubusercontent.com/74408724/124024217-92ebab80-d9f7-11eb-9a6d-6717bc69c8ec.jpg)
