package uk.me.williammartin.illuminator;

public class IlluminatorException extends RuntimeException {

    private static final long serialVersionUID = -5553063235577138853L;

    public IlluminatorException() {}

    public IlluminatorException(String message) {
        super(message);
    }

    public IlluminatorException(Throwable cause) {
        super(cause);
    }

    public IlluminatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
