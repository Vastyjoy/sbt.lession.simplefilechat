package Core.Exception;

public class CreateFileMessageFallException extends CoreIOException {
    public CreateFileMessageFallException() {
    }

    public CreateFileMessageFallException(String message) {
        super(message);
    }

    public CreateFileMessageFallException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateFileMessageFallException(Throwable cause) {
        super(cause);
    }

    public CreateFileMessageFallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
