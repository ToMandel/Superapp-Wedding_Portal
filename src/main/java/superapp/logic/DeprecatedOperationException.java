package superapp.logic;

public class DeprecatedOperationException extends RuntimeException{
    private static final long serialVersionUID = -2174488076635281609L;

	public DeprecatedOperationException() {
    }

    public DeprecatedOperationException(String message) {
        super(message);
    }

    public DeprecatedOperationException(Throwable cause) {
        super(cause);
    }

    public DeprecatedOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
