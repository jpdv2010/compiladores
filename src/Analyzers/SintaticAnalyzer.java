package Analyzers;

import Java.Entitys.Token;
import Utils.Constants;

import java.util.ArrayList;
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
        token = tokenList[++ptoken];
    }

    private void commands()
    {
        decl();
        command();
    }

    private void command()
    {
        if(Objects.equals(token.getImage(), ";"))
        {
            decl();
            atrib();
            loop();
            cond();
            input();
            output();
            command();
        }
    }

    private void decl()
    {
        if(Objects.equals(token.getImage(), "("))
        {
            nexttoken();
            type();
            ids();
            if(Objects.equals(token.getImage(), ")"))
            {
                nexttoken();
                type();
            }
            else
            {
                errorLit.add(new Error("erro: Esperado )"));
            }
        }
        else
        {
            errorLit.add(new Error("erro: Esperado ("));
        }
    }

    private void type()
    {
        if(Objects.equals(token.getImage(), "int") || Objects.equals(token.getImage(), "real") || Objects.equals(token.getImage(), "char") || Objects.equals(token.getImage(), "bool"))
        {
            nexttoken();
            ids();
        }
        else
        {
            errorLit.add(new Error("erro de declaração"));
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
            errorLit.add(new Error("erro: Esperado um identificador"));
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
        if(Objects.equals(token.getImage(),"(")){
            nexttoken();
            if(Objects.equals(token.getImage(),"=")){
                nexttoken();
                if(Objects.equals(token.getTokenClass(),"id")){
                    nexttoken();
                    exp();
                    if(Objects.equals(token.getImage(),")")){
                        nexttoken();
                    } else {
                        errorLit.add(new Error("erro: Esperado )"));
                    }
                } else {
                    errorLit.add(new Error("erro: Esperado um identificador"));
                }
            } else {
                errorLit.add(new Error("erro: Esperado ="));
            }
        } else {
            errorLit.add(new Error("erro: Esperado ("));
        }
    }

    private void exp() {
        for(int i = 0; i < Constants.numbers.length; i++){
            if(Constants.numbers[i].equals(token.getImage())){
                operan();
            } else {
                if(token.getImage().equals("(")){
                    nexttoken();
                    op();
                    exp();
                    exp();
                    if(token.getImage().equals(")")){
                        nexttoken();
                        operan();
                    } else {
                        errorLit.add(new Error("Erro: Esperado )"));
                    }
                } else {
                    errorLit.add(new Error("erro: Esperado ( ou operando"));
                }
            }
        }


    }

    private void operan() {
        for(int i = 0; i < Constants.numbers.length; i++){
            if(Constants.numbers[i].equals(token.getImage())){
                nexttoken();
                if(Constants)
            }
        }
    }

    public List<Error> getErrorLit() {
        return errorLit;
    }

    public void setErrorLit(List<Error> errorLit) {
        this.errorLit = errorLit;
    }
}
