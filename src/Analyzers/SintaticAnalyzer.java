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
            errorLit.add(new Error("Esperado $ - Encontrado: " + token.getImage()));
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
                        errorLit.add(new Error("Erro na linha: " + token.getLine() + "Esperado (atrib, loop, cond, input, output) - Encontrado: " + token.getImage()));
                        break;
                }
            }
        } else {
            errorLit.add(new Error("Erro na linha: " + token.getLine() + " Esperado: '(' - Encontrado: " + token.getImage()));
        }
    }

    private void decl()
    {
        type();
        ids();
        if(Objects.equals(token.getImage(), ")"))
        {
            nexttoken();
        }
        else
        {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado  - Encontrado: " + token.getImage()));
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
            errorLit.add(new Error("Erro linha: " + token.getLine() + "erro de declaração - Encontrado: " + token.getImage()));
        }
    }

    private void ids()
    {
        if(Objects.equals(token.getTokenClass(), "Identifier"))
        {
            nexttoken();
            ids2();

        }
        else
        {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado um identificador - Encontrado: " + token.getImage()));
        }
    }

    private void ids2()
    {
        if(Objects.equals(token.getTokenClass(), "Identifier"))
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
                    errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado ) - Encontrado: " + token.getImage()));
                }
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado um identificador - Encontrado: " + token.getImage()));
            }
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado = - Encontrado: " + token.getImage()));
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
                    errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado ) - Encontrado: " + token.getImage()));
                }
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado ( ou operando - Encontrado: " + token.getImage()));
            }
        }
    }

    private void op() {
        if(token.getTokenClass().equals("Operator")){
            nexttoken();
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado Operador - Encontrado: " + token.getImage()));
        }
    }

    private void operan() {
        if(Constants.LiteralConstants.contains(token.getTokenClass())){
            nexttoken();
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: Constante literal - Encontrado: " + token.getImage()));
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
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')' - Encontrado: " + token.getImage()));
            }
        } else{
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: '...' - Encontrado: " + token.getImage()));
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
                        errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')' - Encontrado: " + token.getImage()));
                    }
                } else {
                    errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')' - Encontrado: " + token.getImage()));
                }
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')' - Encontrado: " + token.getImage()));
            }
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: '?' - Encontrado: " + token.getImage()));
        }
    }

    private void elsel2s() {
        if(token.getImage().equals("(")){
            nexttoken();
            commands();
            if(token.getImage().equals(")")){
                nexttoken();
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')' - Encontrado: " + token.getImage()));
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
                    errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')' - Encontrado: " + token.getImage()));
                }
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado um identificador - Encontrado: " + token.getImage()));
            }
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado ':<<' - Encontrado: " + token.getImage()));
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
                    errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')' - Encontrado: " + token.getImage()));
                }
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado ':>>' - Encontrado: " + token.getImage()));
            }
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: '(' - Encontrado: " + token.getImage()));
        }
    }

    public List<Error> getErrorLit() {
        return errorLit;
    }

    public void setErrorLit(List<Error> errorLit) {
        this.errorLit = errorLit;
    }
}