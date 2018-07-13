package Analyzers;

import Java.Entitys.Identifier;
import Java.Entitys.Node;
import Java.Entitys.Token;
import Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SemanticAnalyzer {
    private Node root;
    private List<Identifier> identifierList;
    private String out = "";

    @Override
    public String toString() {
        return out;
    }

    public SemanticAnalyzer(Node root, List<Identifier> identifierList) {
        this.root = root;
        this.identifierList = identifierList;
        this.out = command(root);
        this.out += commands(root.getFilho(2));
    }

    private String commands(Node node){
        String cmds = "";
        if(node.filhos.size() > 0){
            cmds += command(node.getFilho(0));
            if(node.filhos.size() > 1){
                cmds += commands(node.getFilho(1));
            }
        }
        return cmds;
    }

    private String command(Node node){
        if(node.filhos.size() > 1){
            switch (node.getFilho(1).toString()){
                case "decl":
                    return decl(node.getFilho(1));
                case "atrib":
                    return atrib(node.getFilho(1));
                case "loop":
                    return loop(node.getFilho(1));
                case "cond":
                    return cond(node.getFilho(1));
                case "input":
                    return input(node.getFilho(1));
                case "output":
                    return output(node.getFilho(1));
                default:
                    return "";
            }
        } else {
            return "";
        }
    }

    private String decl(Node node){
        String decl = "";
        String type = type(node.getFilho(0));
        List<Token> ids = ids(node.getFilho(1));
        decl += (type) + " ";
        for(int i = 0; i < ids.size(); i++){
            if(i == ids.size()-1){
                decl += ids.get(i).getImage() + " = 0";
            } else {
                decl += ids.get(i).getImage() + " = 0" + ", ";
            }
        }
        return decl +=";";
    }

    private String atrib(Node node){
        String atrib = "";
        if(node.filhos.size() > 1){
            atrib += node.getFilho(1).toString();
            atrib += " = ";
            atrib += exp(node.getFilho(2));
        }
        atrib += ";";
        return atrib;
    }

    private String loop(Node node){
        String loop = "";
        loop += "while";
        if(node.filhos.size() > 1){
            loop += exp(node.getFilho(1));
        }
        loop += "{";
        if(node.filhos.size() > 2){
            loop += commands(node.getFilho(2));
        }
        loop += "}";
        return loop;
    }

    private String cond(Node node){
        String cond = "";
        cond += "if";
        if(node.filhos.size() > 1){
            cond += exp(node.getFilho(1));
        }
        cond += "{";
        if(node.filhos.size() > 3){
            cond += commands(node.getFilho(3));
        }
        cond += "}";
        if(node.filhos.size() > 4){
            cond += elsel2s(node.getFilho(4));
        }
        return cond;
    }

    private String elsel2s(Node node) {
        String elseL2s = "";
        elseL2s += "else {";
        if(node.filhos.size() > 1){
            elseL2s += commands(node.getFilho(1));
        }
        elseL2s += "}";
        return elseL2s;
    }

    private String input(Node node){
        String input = "";
        input += "scanf(";
        if(node.filhos.size() > 1){
            input += node.getFilho(1).toString();
        }
        input += ");";
        return input;
    }

    private String output(Node node){
        String output = "";
        output += "printf(";
        if(node.filhos.size() > 1){
            output += exp(node.getFilho(1));
        }
        output += ");";
        return output;
    }

    private String exp(Node node) {
        String exp = "";
        if(node.filhos.size() > 0){
            if(node.filhos.size() > 3){
                String op = op(node.getFilho(1));
                exp += "(" + exp(node.getFilho(2)) + op + exp(node.getFilho(3)) + ")";
            } else {
                if(node.getFilho(0).getToken() != null && node.getFilho(0).getToken().getTokenClass().equals("CLS")){
                    exp += "\"" + operan(node.getFilho(0)) + "\"";
                } else  exp += operan(node.getFilho(0));
            }
        }
        return exp;
    }

    private String op(Node node) {
        return node.getFilho(0).toString();
    }

    private String operan(Node node) {
        return node.getFilho(0).toString();
    }

    private List<Token> ids(Node node) {
        List<Token> ids = new ArrayList<>();
        if(node.filhos.size() > 0){
            ids.add(new Token(node.getFilho(0).toString()));
            if(node.filhos.size() > 1){
                ids.addAll(ids(node.getFilho(1)));
            }
        }
        return ids;
    }

    private String type(Node node) {
        return node.getFilho(0).toString();
    }
}
