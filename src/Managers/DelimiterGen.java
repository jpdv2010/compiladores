package Managers;

import Java.Entitys.Delimiter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaop on 05/03/2018.
 */
public class DelimiterGen {
    private List<Delimiter> delimiters = new ArrayList<>();

    public DelimiterGen() {
        Delimiter delimiter1 = new Delimiter();
        delimiter1.setRepresentation("{");
        delimiters.add(delimiter1);

        Delimiter delimiter2 = new Delimiter();
        delimiter2.setRepresentation("}");
        delimiters.add(delimiter2);

        Delimiter delimiter3 = new Delimiter();
        delimiter3.setRepresentation("[");
        delimiters.add(delimiter3);

        Delimiter delimiter4 = new Delimiter();
        delimiter4.setRepresentation("]");
        delimiters.add(delimiter4);

        Delimiter delimiter5 = new Delimiter();
        delimiter5.setRepresentation("(");
        delimiters.add(delimiter5);

        Delimiter delimiter6 = new Delimiter();
        delimiter6.setRepresentation(")");
        delimiters.add(delimiter6);
    }

    public DelimiterGen(List<Delimiter> delimiters) {
        this.delimiters = delimiters;
    }

    public List<Delimiter> getDelimiters() {
        return delimiters;
    }

    public void setReservedWords(List<Delimiter> delimiters) {
        this.delimiters = delimiters;
    }
}
