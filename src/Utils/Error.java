package Utils;

/**
 * Created by joaop on 10/04/2018.
 */
public class Error {
    private String message;
    public Error(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
