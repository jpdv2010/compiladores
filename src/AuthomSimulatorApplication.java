import Java.Entitys.PhiniteAuthomata;
import Java.Entitys.State;
import Managers.AuthomatGen;
import Views.ControlPanel;
import Views.TestPanel;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaop on 05/09/2017.
 */
public class AuthomSimulatorApplication {
    public static void main(String args[]){
        List<State> stateList=new ArrayList<State>();
        BufferedReader reader;
        List<PhiniteAuthomata> authomataList = new AuthomatGen().AuthomatGen();
        stateList=authomataList.get(0).stateList;
        TestPanel cp=new TestPanel(stateList);
        cp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
