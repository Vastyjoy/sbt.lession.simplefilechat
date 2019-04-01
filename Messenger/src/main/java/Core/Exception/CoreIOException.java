package Core.Exception;

public class CoreIOException extends CoreException {
    public CoreIOException() {
    }

    public CoreIOException(String message) {
        super(message);
    }

    public CoreIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreIOException(Throwable cause) {
        super(cause);
    }

    public CoreIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
