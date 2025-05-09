package Services;


public class MotoException extends Exception{
    public MotoException() {
    }

    public MotoException(String message) {
        super(message);
    }

    public MotoException(String message, Throwable cause) {
        super(message, cause);
    }
}
