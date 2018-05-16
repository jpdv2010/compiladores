package Java.Entitys;

import java.util.*;

/**
 * Created by joaop on 24/08/2017.
 */
public class State {

    private List<TransitionRule> transitionRules=new ArrayList<>();
    private boolean isInitial=false;
    private boolean isFinal=false;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TransitionRule> getTransitionRules() {
        return transitionRules;
    }

    public void setTransitionRules(List<TransitionRule> transitionRules) {
        this.transitionRules = transitionRules;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean initial) {
        isInitial = initial;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }
}
