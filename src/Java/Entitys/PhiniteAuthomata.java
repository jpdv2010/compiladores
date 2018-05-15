package Java.Entitys;

import Managers.AuthomatGen;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by joaop on 24/08/2017.
 */
public class PhiniteAuthomata {
    public List<State> stateList=new ArrayList<>();
    private String title;

    public PhiniteAuthomata(List<State> stateList,String title) {
        this.stateList = stateList;
        this.title = title;
    }

    public List<State> generateAuthomatha(BufferedReader reader) throws IOException {
        /*int index=0;
        while(index<5){
            List<Letter> letterList=new ArrayList<>();
            String linha=reader.readLine();

            if(index==0){//--------------------------------------Linha que define os estados---------------
                String[] states=linha.split(" ");
                for(String s:states){
                    State q=new State();
                    q.setId(s);
                    q.setInitial(false);
                    q.setFinal(true);
                    stateList.add(q);
                }
            }

            if(index==1){//---------------------------------------Linha que define o alfabeto---------
                String[] letters=linha.split(" ");
                for(String l:letters){
                    Letter letter=new Letter();
                    letter.setSymbol(l);
                    letterList.add(letter);
                }
            }

            if(index==2){//--------------------------------Linha que define as regras de tranzição---
                String[] tRules=linha.split(";");
                for(String tr:tRules){
                    String[] tRule=tr.split(" ");
                    TransitionRule t=new TransitionRule();
                    for(State s:stateList){
                        if(s.getId().equals(tRule[2])){
                            t.setNextState(s);
                        }
                    }
                    for(Letter l:letterList){
                        if(l.getSymbol().equals(tRule[1])){
                            t.setLetter(l);
                        }
                    }
                    for(State s:stateList){
                        if(s.getId().equals(tRule[0])){
                            s.getTransitionRules().add(t);
                        }
                    }

                }
            }

            if(index==3){//------------------------Linha que define o estado inicial-----------
                String initState=linha;
                for(State s:stateList){
                    if(s.getId().equals(initState)){
                      s.setInitial(true);
                    }
                }
            }
            if(index==4){//--------------------------Linha que define os estados Finais----
                String[] finalStates=linha.split(" ");
                for(String fs:finalStates){
                    for(State s:stateList){
                        if(s.getId().equals(fs)){
                            s.setFinal(true);
                        }
                    }
                }
            }
            index++;
        }
*/
        AuthomatGen authomatGen = new AuthomatGen();
        return authomatGen.ifAuthomat();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}