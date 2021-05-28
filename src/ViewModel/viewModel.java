package ViewModel;

import Model.Model;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Observable;
import java.util.Observer;

public class viewModel extends Observable implements Observer {
    Model m;

    public DoubleProperty aileron;
    public DoubleProperty elevators;
    public DoubleProperty rudder;
    public DoubleProperty throttle;

    public viewModel(Model model) {
        this.m = model;
        model.addObserver(this);
        aileron = new SimpleDoubleProperty();
        elevators = new SimpleDoubleProperty();
        rudder = new SimpleDoubleProperty();
        throttle = new SimpleDoubleProperty();

        aileron.addListener((o,ov,nv)->m.setAileron((double)nv));
        elevators.addListener((o,ov,nv)->m.setElevators((double)nv));
        rudder.addListener((o,ov,nv)->m.setRudder((double)nv));
        throttle.addListener((o,ov,nv)->m.setThrottle((double)nv));
    }


    @Override
    public void update(Observable o, Object arg) {
        if(o==m)
        {

        }
    }
}

