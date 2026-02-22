package tyrone.exception;

/**
 * Exception thrown when an error occurs during Tyrone application execution.
 *
 * <p>Used for handling invalid commands, missing parameters, incorrect formats,
 * and other application-specific errors.</p>
 */
public class TyroneException extends Exception {

    /**
     * Constructs a TyroneException with no detail message.
     */
    public TyroneException() {
        super();
    }

    /**
     * Constructs a TyroneException with the specified detail message.
     *
     * @param message The detail message explaining the exception
     */
    public TyroneException(String message) {
        super(message);
    }
}