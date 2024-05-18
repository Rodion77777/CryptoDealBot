package CryptoDealBot.demo.business.exceptions;

public class TemporaryDataUnavailableException extends RuntimeException {
    public TemporaryDataUnavailableException(String message) {
        super(message);
    }

    public TemporaryDataUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
