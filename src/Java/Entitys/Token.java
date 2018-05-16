package Java.Entitys;

import java.util.List;

/**
 * Created by joaop on 10/04/2018.
 */
public class Token {
    private String image;
    private List<Integer> identifierList;
    private int line;
    private int column;
    private String tokenClass;

    public Token(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Integer> getIdentifierList() {
        return identifierList;
    }

    public void setIdentifierList(List<Integer> identifierList) {
        this.identifierList = identifierList;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getTokenClass() {
        return tokenClass;
    }

    public void setTokenClass(String tokenClass) {
        this.tokenClass = tokenClass;
    }
}
