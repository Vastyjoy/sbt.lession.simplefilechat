package Core.Exception;

public class FileIsNotDirectoryException extends BadDirectoryException {
    public FileIsNotDirectoryException() {
    }

    public FileIsNotDirectoryException(String message) {
        super(message);
    }

    public FileIsNotDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileIsNotDirectoryException(Throwable cause) {
        super(cause);
    }

    public FileIsNotDirectoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
