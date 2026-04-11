package semchishin.rememberprocessingservice.exception;

public class RemindNotFoundException extends RuntimeException {

    public RemindNotFoundException() {
        super();
    }

    public RemindNotFoundException(String message) {
        super(message);
    }

    public RemindNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemindNotFoundException(Throwable cause) {
        super(cause);
    }

}
