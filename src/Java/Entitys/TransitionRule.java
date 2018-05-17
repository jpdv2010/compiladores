package Java.Entitys;

/**
 * Created by joaop on 24/08/2017.
 */
public class TransitionRule {
    public TransitionRule(Letter letter, State nextState) {
        this.letter = letter;
        this.nextState = nextState;
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public State getNextState() {
        return nextState;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }

    private Letter letter;
    private State nextState;

}
