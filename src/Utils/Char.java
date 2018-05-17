package Utils;

import Java.Entitys.Cls;

/**
 * Created by joaop on 10/04/2018.
 */
public class Char {
    public String symbol;
    private boolean isCls = false;
    private Cls cls;
    public boolean isRemovible = false;

    public void EnableRemotion(){
        this.isRemovible = true;
    }

    public Cls getCls() {
        return cls;
    }

    public void setCls(Cls cls) {
        this.cls = cls;
    }

    public void isCls() {
    }

    public void setCls(boolean cls) {
        isCls = cls;
    }

}
