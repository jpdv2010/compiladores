package Managers;

import Java.Entitys.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaop on 10/04/2018.
 */
public class OperatorsGen {
    private List<Token> operators = new ArrayList<>();

    public OperatorsGen() {
        Token operatorPlus = new Token();
        operatorPlus.setImage("+");
        operators.add(operatorPlus);

        Token operatorLess = new Token();
        operatorLess.setImage("-");
        operators.add(operatorLess);

        Token operatorX = new Token();
        operatorX.setImage("*");
        operators.add(operatorX);

        Token operatorDivision = new Token();
        operatorDivision.setImage("/");
        operators.add(operatorDivision);

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
