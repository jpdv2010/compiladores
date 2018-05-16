package Analyzers;

import Java.Entitys.Token;
import Utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by joaop on 10/04/2018.
 */
public class SintaticAnalyzer {
    private int ptoken;
    private Token[] tokenList;
    private Token token;
    private List<Error> errorLit;

    public SintaticAnalyzer(List<Token> tokenList)
    {
        ptoken = 0;
        getTokenList(tokenList);
        errorLit = new ArrayList<>();
        nexttoken();
        commands();
        if(!Objects.equals(token.getTokenClass(), "$"))
        {
            errorLit.add(new Error("Esperado $"));
        }
    }

    private void getTokenList(List<Token> tokenList) {
        int i = 0;
        this.tokenList = new Token[tokenList.size()];
        for(Token t : tokenList)
        {
            this.tokenList[i] = t;
            i++;
        }
    }

    private void nexttoken() {
        token = tokenList[ptoken++];
    }

    private void commands()
    {
        command();
        if(token.getImage().equals("(")){
            commands();
        }
    }

    private void command()
    {
        if(token.getImage().equals("(")){
            nexttoken();
            if(Constants.Types.contains(token.getImage())){
                decl();
            } else {
                switch (token.getImage()) {
                    case "=":
                        atrib();
                        break;
                    case "...":
                        loop();
                        break;
                    case "?":
                        cond();
                        break;
                    case ":<<":
                        input();
                        break;
                    case ":>>":
                        output();
                        break;
                    default:
                        break;
                }
            }
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: '('"));
        }
    }

    private void decl()
    {
        type();
        ids();
        if(Objects.equals(token.getImage(), ")"))
        {
            nexttoken();
            type();
        }
        else
        {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado )"));
        }
    }

    private void type()
    {
        if(Constants.Types.contains(token.getImage()))
        {
            nexttoken();
        }
        else
        {
            errorLit.add(new Error("Erro linha: " + token.getLine() + "erro de declaração"));
        }
    }

    private void ids()
    {
        if(Objects.equals(token.getTokenClass(), "id"))
        {
            nexttoken();
            ids2();

        }
        else
        {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado um identificador"));
        }
    }

    private void ids2()
    {
        if(Objects.equals(token.getTokenClass(), "id"))
        {
            nexttoken();
            ids2();
        }
    }

    private void atrib() {
        if(Objects.equals(token.getImage(),"=")){
            nexttoken();
            if(Objects.equals(token.getTokenClass(),"Identifier")){
                nexttoken();
                exp();
                if(Objects.equals(token.getImage(),")")){
                    nexttoken();
                } else {
                    errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado )"));
                }
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado um identificador"));
            }
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado ="));
        }
    }

    private void exp() {
        if(Constants.LiteralConstants.contains(token.getTokenClass())){
            operan();
        } else {
            if(token.getImage().equals("(")){
                nexttoken();
                op();
                exp();
                exp();
                if(token.getImage().equals(")")){
                    nexttoken();
                } else {
                    errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado )"));
                }
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado ( ou operando"));
            }
        }
    }

    private void op() {
        if(token.getTokenClass().equals("Operator")){
            nexttoken();
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado Operador"));
        }
    }

    private void operan() {
        if(Constants.LiteralConstants.contains(token.getTokenClass())){
            nexttoken();
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: Constante literal"));
        }
    }

    private void loop(){
        if(token.getImage().equals("...")){
            nexttoken();
            exp();
            commands();
            if(token.getImage().equals(")")){
                nexttoken();
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')'"));
            }
        } else{
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: '...'"));
        }
    }

    private void cond(){
        if(token.getImage().equals("?")){
            nexttoken();
            exp();
            if(token.getImage().equals("(")){
                nexttoken();
                commands();
                if(token.getImage().equals(")")){
                    nexttoken();
                    elsel2s();
                    if(token.getImage().equals(")")){
                        nexttoken();
                    } else {
                        errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')'"));
                    }
                } else {
                    errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')'"));
                }
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')'"));
            }
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: '?'"));
        }
    }

    private void elsel2s() {
        if(token.getImage().equals("(")){
            nexttoken();
            commands();
            if(token.getImage().equals(")")){
                nexttoken();
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')'"));
            }
        }
    }

    private void input(){
        if(token.getImage().equals(":<<")){
            nexttoken();
            if(token.getTokenClass().equals("Identifier")){
                nexttoken();
                if(token.getImage().equals(")")){
                    nexttoken();
                } else {
                    errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')'"));
                }
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado um identificador"));
            }
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado ':<<'"));
        }
    }

    private void output(){
        if(token.getImage().equals("(")){
            nexttoken();
            if(token.getImage().equals(":>>")){
                nexttoken();
                exp();
                if(token.getImage().equals(")")){
                    nexttoken();
                } else {
                    errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')'"));
                }
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado ':>>'"));
            }
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: '('"));
        }
    }

    public List<Error> getErrorLit() {
        return errorLit;
    }

    public void setErrorLit(List<Error> errorLit) {
        this.errorLit = errorLit;
    }
}