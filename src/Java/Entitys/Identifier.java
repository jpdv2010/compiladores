package Java.Entitys;

/**
 * Created by joaop on 10/04/2018.
 */
public class Identifier {
    private String image;
    private int line;
    private int column;
    private int id;
    private String value;

    public Identifier(int id,String image, int line, int column) {
        this.image = image;
        this.line = line;
        this.column = column;
    }

    public void setIdentifier(String img, Object vl){

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
