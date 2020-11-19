package Lesson19.Bai1.Model;

public class MyException extends Throwable {
    private String myError;

    public MyException(String myError) {
        this.myError = myError;
    }

    public String getMyError() {
        return myError;
    }

    public void setMyError(String myError) {
        this.myError = myError;
    }
}
