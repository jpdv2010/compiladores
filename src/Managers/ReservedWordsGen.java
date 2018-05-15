package Managers;

import Java.Entitys.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaop on 05/03/2018.
 */
public class ReservedWordsGen {
    private List<Token> reservedWords = new ArrayList<>();

    public ReservedWordsGen() {
        Token reservedWordIF = new Token();
        reservedWordIF.setImage("?");
        reservedWords.add(reservedWordIF);

        Token reservedWordELSE = new Token();
        reservedWordELSE.setImage("else");
        reservedWords.add(reservedWordELSE);

        Token reservedWordOUTPUT = new Token();
        reservedWordOUTPUT.setImage(":>>");
        reservedWords.add(reservedWordOUTPUT);

        Token reservedWordINPUT = new Token();
        reservedWordINPUT.setImage(":<<");
        reservedWords.add(reservedWordINPUT);

        Token reservedWordINT = new Token();
        reservedWordINT.setImage("int");
        reservedWords.add(reservedWordINT);

        Token reservedWordFLOAT = new Token();
        reservedWordFLOAT.setImage("float");
        reservedWords.add(reservedWordFLOAT);

        Token reservedWordSTRING = new Token();
        reservedWordFLOAT.setImage("string");
        reservedWords.add(reservedWordFLOAT);

        Token reservedWordWHILE = new Token();
        reservedWordFLOAT.setImage("while");
        reservedWords.add(reservedWordFLOAT);

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
