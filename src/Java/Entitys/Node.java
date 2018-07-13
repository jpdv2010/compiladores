package Java.Entitys;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Node pai;
    public List<Node> filhos = new ArrayList<>();

    private String tipo;

    public Token getToken() {
        return token;
    }

    private Token token;

    public Node(String tipo){
        this.tipo = tipo;
    }

    public Node(Token token){
        this.token = token;
    }

    public void addFilho(Node filho){
        filhos.add(filho);
        filho.pai = this;
    }

    public Node getFilho(int pos){
        return filhos.get(pos);
    }

    public String toString() {
        if(tipo != null) {
            return tipo;
        } else {
            return token.getImage();
        }
    }
}
