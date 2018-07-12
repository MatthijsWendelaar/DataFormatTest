package name.wendelaar.jsnow;

public class JSnowException extends Exception {

    public JSnowException() {
        super();
    }

    public JSnowException(String message) {
        super(message);
    }

    public JSnowException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSnowException(Throwable cause) {
        super(cause);
    }
}
