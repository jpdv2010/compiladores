package Views;

import Java.Entitys.PhiniteAuthomata;
import Java.Entitys.State;
import Managers.AuthomatGen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by joaop on 21/09/2017.
 */
public class ControlPanel extends JFrame {
    JButton btnOpenFile = new JButton();
    public ControlPanel(){
        super("Simulador de Automato");
        setSize(300,200);
        setResizable(false);
        setVisible(true);
        //setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        Container c=new Container();
        btnOpenFile=new JButton("Abrir");
        btnOpenFile.setBounds(10,10,100,20);
        btnOpenFile.addActionListener(openArchive);
        c.add(btnOpenFile);
        c.setVisible(true);
        this.add(c);
    }

    ActionListener openArchive=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser file = new JFileChooser();
            file.setFileSelectionMode(JFileChooser.FILES_ONLY);
            List<State> stateList=new ArrayList<State>();
            int i= file.showSaveDialog(null);
            if (i==1){
                //faz nada
            } else {
                File arquivo = file.getSelectedFile();
                String caminho=arquivo.getPath();
                BufferedReader reader;
                List<PhiniteAuthomata> authomataList = new AuthomatGen().AuthomatGen();
                try {
                    reader = new BufferedReader(new FileReader(caminho));
                    stateList=authomataList.get(0).stateList;
                    TestPanel cp=new TestPanel(stateList);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        }
    };

}
