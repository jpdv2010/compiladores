package Java.Entitys;

public class Response {
    private boolean correct;
    private String error;

    public Response(boolean correct, String error) {
        this.correct = correct;
        this.error = error;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
