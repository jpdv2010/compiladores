package Analyzers;

import Java.Entitys.*;
import Managers.DelimiterGen;
import Managers.OperatorsGen;
import Managers.ReservedWordsGen;
import Utils.Char;
import Utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by joaop on 21/02/2018.
 */
public class LexicalAnalyzer {
    private String code;
    private List<PhiniteAuthomata> authomats;
    private String lexem = "";

    private List<Cls> clsList = new ArrayList<>();
    private List<Token> tokenList = new ArrayList<>();

    private List<Identifier> identifiers = new ArrayList<>();

    public LexicalAnalyzer(String code, List<PhiniteAuthomata> authomatas){
        this.code = code;
        this.authomats = authomatas;
    }

    public boolean Analyze(){
        code = verifyOnCLS(code.split(""));
        //code = CommonUtils.clearCls(code, clsList);
        String[] words = code.split(" ");
        for(String word : words){

            if(!AnalyzeWord(word)) return false;
        }
        return true;
    }

    private boolean AnalyzeWord(String word) {
        return verifyOnReserverWords(word)
                || verifyOnDelimiters(word)
                || verifyOnIdentifier(word)
                || verifyOnOperators(word);
    }

    private boolean verifyOnDelimiters(String word)
    {
        DelimiterGen delimiterGen= new DelimiterGen();
        for(Delimiter delimiter : delimiterGen.getDelimiters())
        {
            if(delimiter.getRepresentation().equals(word))
            {
                generateToken(word,"Delimiter");
                lexem += " (" + word + ")Delimiter";
                return true;
            }
        }
        return false;
    }

    private boolean verifyOnReserverWords(String word)
    {
        ReservedWordsGen reservedWordsGen= new ReservedWordsGen();
        for(Token reservedWord : reservedWordsGen.getReservedWords())
        {
            if(reservedWord.getImage().equals(word))
            {
                generateToken(word,reservedWord.getTokenClass());
                lexem += " (" + word + ")ReservedWord";
                return true;
            }
        }
        return false;
    }

    private boolean verifyOnOperators(String word)
    {
        OperatorsGen operatorsGen= new OperatorsGen();
        for(Token operator : operatorsGen.getOperators())
        {
            if(operator.getImage().equals(word))
            {
                generateToken(word,operator.getTokenClass());
                lexem += " (" + word + ")ReservedWord";
                return true;
            }
        }
        return false;
    }

    private boolean verifyOnIdentifier(String word)
    {
        String token = "";
        for(PhiniteAuthomata authomata : authomats){
            if(verifyLetters(word, authomata)) token = authomata.getTitle();
        }
        if(!token.equals(""))
        {
            generateToken(word,"Identifier");
            lexem += " (" + word + ")Identifier";
            return true;
        }
        return false;
    }

    private String verifyOnCLS(String[] words) {
        int aspasNum = 0;
        List<Char> charList = getCharList(words);
        String cls = "";
        for(Char ch : charList)
        {
            if(Objects.equals(ch.symbol, "\""))
            {
                ch.EnableRemotion();
                if(aspasNum == 0)
                {
                    aspasNum ++;
                }else{
                    aspasNum --;
                    clsList.add(new Cls(cls));
                    generateToken(cls,"CLS");
                    lexem += " (" + cls + ")CLS";
                }
            }else{
                if(aspasNum == 1) {
                    cls += ch.symbol;
                    ch.EnableRemotion();
                }
            }
        }

        return mountsNewCode(charList);
    }

    private List<Char> getCharList(String[] words) {
        List<Char> charList = new ArrayList<>();
        for(String ch : words)
        {
            Char c = new Char();
            c.symbol = ch;
            charList.add(c);
        }
        return charList;
    }

    private String mountsNewCode(List<Char> charList) {
        String newWords = "";
        for(Char ch : charList){
            if(!ch.isRemovible){
                newWords += ch.symbol;
            }
        }
        return newWords;
    }

    private boolean verifyLetters(String word, PhiniteAuthomata authomata) {
        State actualState = new State();
        boolean Aprove=false;
        String[] letters = word.split("");

        for (State s : authomata.stateList) {
            if (s.isInitial()) {
                actualState = s;
            }
        }
        for(String l:letters){
            if(authomata.getTitle().equals("identifier"))
            {
                l = CommonUtils.isLetter(l)?
                        "l"
                        : CommonUtils.isNumber(l)?
                        "n"
                        : "..";
            }
            for (TransitionRule tr:actualState.getTransitionRules()){
                if(tr.getLetter().getSymbol().equals(l)){
                    actualState=tr.getNextState();
                    Aprove=true;
                }
            }
            if(!Aprove)return false;
            Aprove=false;
        }

        return actualState.isFinal();
    }

    private void generateToken(String word, String tokenClass) {
        Token token = new Token();
        token.setImage(word);
        if(Objects.equals(tokenClass,"Identifier"))
        {
            for(Identifier i : identifiers)
            {
                if(!Objects.equals(i,tokenClass))
                {
                    Identifier id = new Identifier();
                    id.setId(identifiers.size());
                    id.setImage(word);
                    identifiers.add(id);
                    token.setIdentifierList(new ArrayList<>());
                    token.getIdentifierList().add(id.getId());
                }
            }
        }
        token.setTokenClass(tokenClass);
        tokenList.add(token);
    }


    public String getLexem() {
        return lexem;
    }

    public void setLexem(String lexem) {
        this.lexem = lexem;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<Identifier> identifiers) {
        this.identifiers = identifiers;
    }
}
