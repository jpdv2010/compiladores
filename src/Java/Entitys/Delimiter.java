package Java.Entitys;

/**
 * Created by joaop on 05/03/2018.
 */
public class Delimiter {
    private String representation;
    private String title = "Delimiter";

    public Delimiter(String representation) {
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }

    public void setRepresentation(String representation) {
        this.representation = representation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
