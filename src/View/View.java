package View;

import View.Graphs.GraphsController;
import View.Icons.IconsController;
import View.Joystick.JoystickController;
import View.ListView.ListViewController;
import ViewModel.Service;
import ViewModel.ViewModel;

import java.util.Observable;

public class View extends Observable {
    ViewModel vm;

    public IconsController ic;
    public GraphsController gc;
    public JoystickController jc;
    public ListViewController lvc;

    public View(){
        vm = Main.viewModel;
        lvc = new ListViewController();
        ic = ListViewController.ic;
        gc = new GraphsController();
        jc = ListViewController.jc;
    }

    public void setAll(){
        ic.setIcons();
        jc.setJoystick();
        lvc.setListView();
        gc.setGraph();

        Service.setDisableALL(true); //disable all buttons on startup before open
        GraphsController.Graph1.setCreateSymbols(false);
        GraphsController.Graph2.setCreateSymbols(false);

        vm = new ViewModel();
        this.addObserver(vm);
    }

    public void bind(){
        ic.bindIcons();
        jc.bindJoystick();
    }

}
