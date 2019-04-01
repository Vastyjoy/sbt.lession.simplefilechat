package Core.Exception;

public class BadDirectoryException extends CoreException {
    public BadDirectoryException() {
    }

    public BadDirectoryException(String message) {
        super(message);
    }

    public BadDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadDirectoryException(Throwable cause) {
        super(cause);
    }

    public BadDirectoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
