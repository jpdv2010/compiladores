package Managers;

import Java.Entitys.Letter;
import Java.Entitys.PhiniteAuthomata;
import Java.Entitys.State;
import Java.Entitys.TransitionRule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by joaop on 21/02/2018.
 */
public class AuthomatGen {

    public List<PhiniteAuthomata> AuthomatGen() {
        List<PhiniteAuthomata> authomataList = new ArrayList<>();
        //authomataList.add(new PhiniteAuthomata(defaultAuthomat(),"default"));
        //authomataList.add(new PhiniteAuthomata(ifAuthomat(),"if"));
        //authomataList.add(new PhiniteAuthomata(elseAuthomat(),"else"));
        authomataList.add(new PhiniteAuthomata(idAuthomat(), "identifier"));
        authomataList.add(new PhiniteAuthomata(clsAuthomat(), "CLS"));
        return  authomataList;
    }

    /*public List<State> defaultAuthomat(){
        List<State> stateList = new ArrayList<>();

        State s0=new State();
        s0.setFinal(false);
        s0.setInitial(true);
        s0.setId("q0");

        State s1=new State();
        s1.setFinal(false);
        s1.setInitial(false);
        s1.setId("q1");

        State s2=new State();
        s2.setFinal(true);
        s2.setInitial(false);
        s2.setId("q2");

        State s3=new State();
        s3.setFinal(true);
        s3.setInitial(false);
        s3.setId("q3");

        Letter l1=new Letter();
        l1.setSymbol("a");

        Letter l2=new Letter();
        l2.setSymbol("b");

        Letter l3=new Letter();
        l3.setSymbol("c");

        Letter l4=new Letter();
        l4.setSymbol("d");

        Letter l5=new Letter();
        l5.setSymbol("e");

        TransitionRule tr1=new TransitionRule();
        tr1.setLetter(l1);
        tr1.setNextState(s1);
        s0.getTransitionRules().add(tr1);
        stateList.add(s0);

        TransitionRule tr2=new TransitionRule();
        tr2.setLetter(l2);
        tr2.setNextState(s1);
        s1.getTransitionRules().add(tr2);

        TransitionRule tr3=new TransitionRule();
        tr3.setLetter(l3);
        tr3.setNextState(s2);
        s1.getTransitionRules().add(tr3);

        TransitionRule tr4=new TransitionRule();
        tr4.setLetter(l5);
        tr4.setNextState(s3);
        s1.getTransitionRules().add(tr4);
        stateList.add(s1);

        TransitionRule tr5=new TransitionRule();
        tr5.setLetter(l3);
        tr5.setNextState(s2);
        s2.getTransitionRules().add(tr5);

        TransitionRule tr6=new TransitionRule();
        tr6.setLetter(l4);
        tr6.setNextState(s3);
        s2.getTransitionRules().add(tr6);
        stateList.add(s2);

        TransitionRule tr7=new TransitionRule();
        tr7.setLetter(l5);
        tr7.setNextState(s3);
        s3.getTransitionRules().add(tr7);
        stateList.add(s3);
        return stateList;
    }*/

   /* public List<State> ifAuthomat(){
        List<State> stateList = new ArrayList<>();

        State s0=new State();
        s0.setFinal(false);
        s0.setInitial(true);
        s0.setId("q0");

        State s1=new State();
        s1.setFinal(false);
        s1.setInitial(false);
        s1.setId("q1");

        State s2=new State();
        s2.setFinal(true);
        s2.setInitial(false);
        s2.setId("q2");

        Letter l1=new Letter();
        l1.setSymbol("i");

        Letter l2=new Letter();
        l2.setSymbol("f");

        TransitionRule tr1=new TransitionRule();
        tr1.setLetter(l1);
        tr1.setNextState(s1);
        s0.getTransitionRules().add(tr1);
        stateList.add(s0);

        TransitionRule tr2=new TransitionRule();
        tr2.setLetter(l2);
        tr2.setNextState(s2);
        s1.getTransitionRules().add(tr2);
        stateList.add(s1);
        stateList.add(s2);
        return stateList;
    }*/

    /*public List<State> elseAuthomat(){
        List<State> stateList = new ArrayList<>();

        State s0=new State();
        s0.setFinal(false);
        s0.setInitial(true);
        s0.setId("q0");

        State s1=new State();
        s1.setFinal(false);
        s1.setInitial(false);
        s1.setId("q1");

        State s2=new State();
        s2.setFinal(false);
        s2.setInitial(false);
        s2.setId("q2");

        State s3=new State();
        s3.setFinal(false);
        s3.setInitial(false);
        s3.setId("q3");

        State s4=new State();
        s4.setFinal(true);
        s4.setInitial(false);
        s4.setId("q4");

        Letter l1=new Letter();
        l1.setSymbol("e");

        Letter l2=new Letter();
        l2.setSymbol("l");

        Letter l3=new Letter();
        l3.setSymbol("s");

        Letter l4=new Letter();
        l4.setSymbol("e");

        TransitionRule tr1=new TransitionRule();
        tr1.setLetter(l1);
        tr1.setNextState(s1);
        s0.getTransitionRules().add(tr1);
        stateList.add(s0);

        TransitionRule tr2=new TransitionRule();
        tr2.setLetter(l2);
        tr2.setNextState(s2);
        s1.getTransitionRules().add(tr2);
        stateList.add(s1);

        TransitionRule tr3=new TransitionRule();
        tr3.setLetter(l3);
        tr3.setNextState(s3);
        s2.getTransitionRules().add(tr3);
        stateList.add(s2);

        TransitionRule tr4=new TransitionRule();
        tr4.setLetter(l4);
        tr4.setNextState(s4);
        s3.getTransitionRules().add(tr4);
        stateList.add(s3);
        stateList.add(s4);

        return stateList;
    }*/

    public List<State> idAuthomat(){
        List<State> stateList = new ArrayList<>();

        State s0=new State();
        s0.setFinal(false);
        s0.setInitial(true);
        s0.setId("q0");

        State s1=new State();
        s1.setFinal(true);
        s1.setInitial(false);
        s1.setId("q1");

        Letter l1=new Letter();
        l1.setSymbol("l");

        Letter l2=new Letter();
        l2.setSymbol("n");

        s0.getTransitionRules().add(new TransitionRule(l1, s1));
        stateList.add(s0);

        s1.getTransitionRules().add(new TransitionRule(l2, s1));

        s1.getTransitionRules().add(new TransitionRule(l1, s1));
        stateList.add(s1);

        return stateList;
    }

    public List<State> clsAuthomat(){
        List<State> stateList = new ArrayList<>();

        State s0=new State();
        s0.setFinal(false);
        s0.setInitial(true);
        s0.setId("q0");

        State s1=new State();
        s1.setFinal(false);
        s1.setInitial(false);
        s1.setId("q1");

        State s2=new State();
        s2.setFinal(false);
        s2.setInitial(false);
        s2.setId("q2");

        State s3=new State();
        s3.setFinal(true);
        s3.setInitial(false);
        s3.setId("q3");

        Letter l1=new Letter();
        l1.setSymbol("c");

        Letter l2=new Letter();
        l2.setSymbol("l");

        Letter l3=new Letter();
        l2.setSymbol("s");

        s0.getTransitionRules().add(new TransitionRule(l1, s1));
        stateList.add(s0);

        s1.getTransitionRules().add(new TransitionRule(l2, s2));
        stateList.add(s1);

        s2.getTransitionRules().add(new TransitionRule(l3, s3));
        stateList.add(s2);

        s3.getTransitionRules().add(new TransitionRule(l1, s1));
        stateList.add(s3);

        return stateList;
    }
}