package Core.Exception;

public class ReadFileMessageFallException extends CoreIOException {
    public ReadFileMessageFallException() {
    }

    public ReadFileMessageFallException(String message) {
        super(message);
    }

    public ReadFileMessageFallException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadFileMessageFallException(Throwable cause) {
        super(cause);
    }

    public ReadFileMessageFallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
