package Managers;

import Java.Entitys.Delimiter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by joaop on 05/03/2018.
 */
public class DelimiterGen {
    private List<Delimiter> delimiters = Arrays.asList(
            new Delimiter("{"),
            new Delimiter("}"),
            new Delimiter("["),
            new Delimiter("]"),
            new Delimiter("("),
            new Delimiter(")")
    );

    public DelimiterGen() {
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
