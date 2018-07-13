package Analyzers;

import Java.Entitys.*;
import Managers.DelimiterGen;
import Managers.OperatorsGen;
import Managers.ReservedWordsGen;
import Utils.Char;
import Utils.CommonUtils;
import Utils.Constants;

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

    public Response Analyze(){
        String[] lines = code.split("\n");
        for(int i = 0; i< lines.length; i++){
            Response resp = AnalyzeLine(lines[i], i + 1);
            if(!resp.isCorrect()) return resp;
        }
        return new Response(true,"");
    }

    private Response AnalyzeLine(String lineData, int lineNumber){
        String[] words = lineData.split(" ");
        int i = 0;
        for(String word : words){
            if(word.split("")[0].equals("\"")){//CASO ENCONTRE ASPAS DUPLAS ELE VERIFICA SE TRATA-SE DE UMA CLS
                String newWords = getNewWords(lineData.split(" "), i);
                String newLine = verifyOnCLS(newWords.split(""), lineNumber);
                Response resp = AnalyzeLine(newLine, lineNumber);
                return resp.isCorrect()? new Response(true,"")
                : new Response(false,"Erro Lexico Linha: " + lineNumber);
            } else {                                    //VERIFICA TODOS OS OUTROS CASOS
                if(!word.equals("") && !word.equals(" ")){
                    if(!AnalyzeWord(word, lineNumber)) return new Response(false, "Erro Lexico Linha: " + lineNumber);
                }
            }
            i++;
        }
        return new Response(true,"");
    }

    private String getNewWords(String[] words, int i) {
        String nw = "";
        for(int j = 0; j < words.length; j++){
            if(j >= i) nw += words[j] + " ";
        }
        return nw;
    }

    private boolean AnalyzeWord(String word, int line) {
        return verifyOnReserverWords(word, line)
                || verifyOnDelimiters(word, line)
                || verifyOnIdentifier(word, line)
                || verifyOnOperators(word, line)
                || verifyOnCli(word, line);
    }

    private boolean verifyOnCli(String word, int line) {
        String[] chars = word.split("");
        for(String ch : chars){
            if(!Constants.numbers.contains(ch)){
                return false;
            }
        }
        generateToken(word, "CLI", line, 0);
        lexem += "(" + word + ")CLI";
        return true;
    }

    private boolean verifyOnDelimiters(String word, int line)
    {
        DelimiterGen delimiterGen= new DelimiterGen();
        for(Delimiter delimiter : delimiterGen.getDelimiters())
        {
            if(delimiter.getRepresentation().equals(word))
            {
                generateToken(word,"Delimiter", line, 0);
                lexem += " (" + word + ")Delimiter";
                return true;
            }
        }
        return false;
    }

    private boolean verifyOnReserverWords(String word, int line)
    {
        ReservedWordsGen reservedWordsGen= new ReservedWordsGen();
        for(Token reservedWord : reservedWordsGen.getReservedWords())
        {
            if(reservedWord.getImage().equals(word))
            {
                generateToken(word,reservedWord.getTokenClass(), line, 0);
                lexem += " (" + word + ")ReservedWord";
                return true;
            }
        }
        return false;
    }

    private boolean verifyOnOperators(String word, int line)
    {
        OperatorsGen operatorsGen= new OperatorsGen();
        for(Token operator : operatorsGen.getOperators())
        {
            if(operator.getImage().equals(word))
            {
                generateToken(word,operator.getTokenClass(), line, 0);
                lexem += " (" + word + ")ReservedWord";
                return true;
            }
        }
        return false;
    }

    private boolean verifyOnIdentifier(String word, int line)
    {
        String token = "";
        for(PhiniteAuthomata authomata : authomats){
            if(verifyLetters(word, authomata)) token = authomata.getTitle();
        }
        if(!token.equals(""))
        {
            generateToken(word,"Identifier", line, 0);
            lexem += " (" + word + ")Identifier";
            return true;
        }
        return false;
    }

    private String verifyOnCLS(String[] words, int line) {
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
                    generateToken(cls, "CLS", line, 1);
                    clsList.add(new Cls(cls));
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

    private void generateToken(String word, String tokenClass, int line, int column) {
        Token token = new Token(word);
        if(Objects.equals(tokenClass,"Identifier"))
        {
            boolean exists = false;
            for(Identifier i : identifiers)
            {
                if(i.getImage().equals(word)) exists = true;
            }
            if(!exists){
                identifiers.add(new Identifier(identifiers.size(),word,line,column));
                token.setIdentifierList(new ArrayList<>());
                token.getIdentifierList().add(identifiers.size());
            }
        }
        token.setTokenClass(tokenClass);
        token.setLine(line);
        token.setColumn(column);
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
