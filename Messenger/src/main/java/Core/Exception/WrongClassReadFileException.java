package Core.Exception;

public class WrongClassReadFileException extends CoreIOException {
    public WrongClassReadFileException() {
    }

    public WrongClassReadFileException(String message) {
        super(message);
    }

    public WrongClassReadFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongClassReadFileException(Throwable cause) {
        super(cause);
    }

    public WrongClassReadFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
