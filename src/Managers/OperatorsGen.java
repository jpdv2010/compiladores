package Managers;

import Java.Entitys.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by joaop on 10/04/2018.
 */
public class OperatorsGen {
    private List<Token> operators = Arrays.asList(
            new Token("+"),
            new Token("-"),
            new Token("*"),
            new Token("/"),
            new Token(">"),
            new Token("<"),
            new Token("<="),
            new Token(">="),
            new Token("&")
    );

    public OperatorsGen() {
        setOperatorsClass();
    }

    private void setOperatorsClass() {
        for(Token t : operators){
            t.setTokenClass("Operator");
        }
    }

    public OperatorsGen(List<Token> reservedWords) {
        this.operators = reservedWords;
    }

    public List<Token> getOperators() {
        return operators;
    }

    public void setOperators(List<Token> reservedWords) {
        this.operators = reservedWords;
    }
}
