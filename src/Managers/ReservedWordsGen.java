package Managers;

import Java.Entitys.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by joaop on 05/03/2018.
 */
public class ReservedWordsGen {
    private List<Token> reservedWords = Arrays.asList(
            new Token("?")
            ,new Token("else")
            ,new Token(":>>")
            ,new Token(":<<")
            ,new Token("int")
            ,new Token("float")
            ,new Token("=")
            ,new Token("bool")
            ,new Token("char")
            ,new Token("string")
            ,new Token("...")
    );
    public ReservedWordsGen() {
        setReservedWordsClass();
    }

    private void setReservedWordsClass() {
        for(Token t : reservedWords){
            t.setTokenClass("ReservedWord");
        }
    }

    public ReservedWordsGen(List<Token> reservedWords) {
        this.reservedWords = reservedWords;
    }

    public List<Token> getReservedWords() {
        return reservedWords;
    }

    public void setReservedWords(List<Token> reservedWords) {
        this.reservedWords = reservedWords;
    }
}
