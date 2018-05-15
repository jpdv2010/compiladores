package Java.Entitys;

/**
 * Created by joaop on 10/04/2018.
 */
public class Identifier {
    private String image;
    private int line;
    private int column;
    private int id;

    public Identifier() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
