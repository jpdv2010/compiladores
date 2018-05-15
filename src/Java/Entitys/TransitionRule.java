package Java.Entitys;

/**
 * Created by joaop on 24/08/2017.
 */
public class TransitionRule {
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
