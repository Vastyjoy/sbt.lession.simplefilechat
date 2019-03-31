package MessagingSystemException;

public class ServiceStarted extends Exception {
    public ServiceStarted() {
    }

    public ServiceStarted(String message) {
        super(message);
    }

    public ServiceStarted(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceStarted(Throwable cause) {
        super(cause);
    }

    public ServiceStarted(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
