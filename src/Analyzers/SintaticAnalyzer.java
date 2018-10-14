package Analyzers;

import Java.Entitys.Node;
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
    private Node root;

    public SintaticAnalyzer(List<Token> tokenList)
    {
        ptoken = 0;
        getTokenList(tokenList);
        errorLit = new ArrayList<>();
        nexttoken();
        this.root = this.command();
        this.root.addFilho(commands());
        if(!Objects.equals(token.getTokenClass(), "$"))
        {
            errorLit.add(new Error("Esperado $ - Encontrado: " + token.getImage()));
        }
        for(Token t : tokenList){
            System.out.println(t.toString());
        }
        mostraArvore();

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
    private void prevtoken(){token = tokenList[ptoken--];}
    private Node commands()
    {
        Node node = new Node("commands");
        node.addFilho(command());
        if(token.getImage().equals("(")){
            node.addFilho(commands());
        }
        return node;
    }

    private Node command()
    {
        Node node = new Node("command");
        if(token.getImage().equals("(")){
            node.addFilho(new Node(token));
            nexttoken();
            if(Constants.Types.contains(token.getImage())){
                node.addFilho(decl());
            } else {
                switch (token.getImage()) {
                    case "=":
                        node.addFilho(atrib());
                        break;
                    case "...":
                        node.addFilho(loop());
                        break;
                    case "?":
                        node.addFilho(cond());
                        break;
                    case ":<<":
                        node.addFilho(input());
                        break;
                    case ":>>":
                        node.addFilho(output());
                        break;
                    default:
                        errorLit.add(new Error("Erro na linha: " + token.getLine() + "Esperado (atrib, loop, cond, input, output) - Encontrado: " + token.getImage()));
                        break;
                }
            }
        } else {
            errorLit.add(new Error("Erro na linha: " + token.getLine() + " Esperado: '(' - Encontrado: " + token.getImage()));
        }
        return node;
    }

    private Node decl()
    {
        Node node = new Node("decl");
        node.addFilho(type());
        node.addFilho(ids());
        if(Objects.equals(token.getImage(), ")"))
        {
            node.addFilho(new Node(token));
            nexttoken();
        }
        else
        {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado  - Encontrado: " + token.getImage()));
        }
        return node;
    }

    private Node type()
    {
        Node node = new Node("type");
        if(Constants.Types.contains(token.getImage()))
        {
            node.addFilho(new Node(token));
            nexttoken();
        }
        else
        {
            errorLit.add(new Error("Erro linha: " + token.getLine() + "erro de declaração - Encontrado: " + token.getImage()));
        }
        return node;
    }

    private Node ids()
    {
        Node node = new Node("ids");
        if(Objects.equals(token.getTokenClass(), "Identifier"))
        {
            node.addFilho(new Node(token));
            nexttoken();
            node.addFilho(ids2());
        }
        else
        {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado um identificador - Encontrado: " + token.getImage()));
        }
        return node;
    }

    private Node ids2()
    {
        Node node = new Node("ids2");
        if(Objects.equals(token.getTokenClass(), "Identifier"))
        {
            node.addFilho(new Node(token));
            nexttoken();
             node.addFilho(ids2());
        }
        return node;
    }

    private Node atrib() {
        Node node = new Node("atrib");
        if(Objects.equals(token.getImage(),"=")){
            node.addFilho(new Node(token));
            nexttoken();
            if(Objects.equals(token.getTokenClass(),"Identifier")){
                node.addFilho(new Node(token));
                nexttoken();
                if(token.getImage().equals("(")){
                    node.addFilho(exp());
                } else {
                    node.addFilho(operan());
                }

                if(Objects.equals(token.getImage(),")")){
                    node.addFilho(new Node(token));
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
        return node;
    }

    private Node exp() {
        Node node = new Node("exp");
        if(Constants.LiteralConstants.contains(token.getTokenClass())){
            node.addFilho(operan());
        } else {
            if(token.getImage().equals("(")){
                node.addFilho(new Node(token));
                nexttoken();
                node.addFilho(op());
                node.addFilho(exp());
                node.addFilho(exp());
                if(token.getImage().equals(")")){
                    nexttoken();
                } else {
                    errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado ) - Encontrado: " + token.getImage()));
                }
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado ( ou operando - Encontrado: " + token.getImage()));
            }
        }
        return node;
    }

    private Node op() {
        Node node = new Node("op");
        if(token.getTokenClass().equals("Operator")){
            node.addFilho(new Node(token));
            nexttoken();
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado Operador - Encontrado: " + token.getImage()));
        }
        return node;
    }

    private Node operan() {
        Node node = new Node("operan");
        if(Constants.LiteralConstants.contains(token.getTokenClass())){
            node.addFilho(new Node(token));
            nexttoken();
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: Constante literal - Encontrado: " + token.getImage()));
        }
        return node;
    }

    private Node loop(){
        Node node = new Node("loop");
        if(token.getImage().equals("...")){
            node.addFilho(new Node(token));
            nexttoken();
            node.addFilho(exp());
            node.addFilho(commands());
            if(token.getImage().equals(")")){
                node.addFilho(new Node(token));
                nexttoken();
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')' - Encontrado: " + token.getImage()));
            }
        } else{
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: '...' - Encontrado: " + token.getImage()));
        }
        return node;
    }

    private Node cond(){
        Node node = new Node("cond");
        if(token.getImage().equals("?")){
            node.addFilho(new Node(token));
            nexttoken();
            node.addFilho(exp());
            if(token.getImage().equals("(")){
               // node.addFilho(new Node(token));
               // nexttoken();
                node.addFilho(commands());
                if(token.getImage().equals("("))
                    node.addFilho(commands());
                if(token.getImage().equals(")")){
                    node.addFilho(new Node(token));
                    nexttoken();
                } else {
                    errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')' - Encontrado: " + token.getImage()));
                }
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')' - Encontrado: " + token.getImage()));
            }
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: '?' - Encontrado: " + token.getImage()));
        }
        return node;
    }

    private Node elsel2s() {
        Node node = new Node("elsel2s");
        if(token.getImage().equals("(")){
            node.addFilho(new Node(token));
            nexttoken();
            if(token.getImage().equals("(")){
                node.addFilho(commands());
                if(token.getImage().equals(")")){
                    node.addFilho(new Node(token));
                    nexttoken();
                } else {
                    errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')' - Encontrado: " + token.getImage()));
                }
            } else {
                return null;
            }
        }
        return node;
    }

    private Node input(){
        Node node = new Node("input");
        if(token.getImage().equals(":<<")){
            node.addFilho(new Node(token));
            nexttoken();
            if(token.getTokenClass().equals("Identifier")){
                node.addFilho(new Node(token));
                nexttoken();
                if(token.getImage().equals(")")){
                    node.addFilho(new Node(token));
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
        return node;
    }

    private Node output(){
        Node node = new Node("output");
        if(token.getImage().equals(":>>")){
            node.addFilho(new Node(token));
            nexttoken();
            node.addFilho(exp());
            if(token.getImage().equals(")")){
                node.addFilho(new Node(token));
                nexttoken();
            } else {
                errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado: ')' - Encontrado: " + token.getImage()));
            }
        } else {
            errorLit.add(new Error("Erro linha: " + token.getLine() + " Esperado ':>>' - Encontrado: " + token.getImage()));
        }
    return node;
    }

    public void mostraArvore() {
        mostraNo(root, "   ");
    }

    private void mostraNo(Node no, String espaco){
        System.out.println(espaco + no);
        for(Node filho: no.filhos) {
            mostraNo(filho, espaco + "   ");
        }
    }

    public List<Error> getErrorLit() {
        return errorLit;
    }

    public void setErrorLit(List<Error> errorLit) {
        this.errorLit = errorLit;
    }

    public Node getRoot() {
        return root;
    }
}