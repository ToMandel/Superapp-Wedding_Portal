package superapp.logic;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {
	private static final long serialVersionUID = -845383700707122285L;

	public InternalServerErrorException() {
	}

	public InternalServerErrorException(String message) {
		super(message);
	}

	public InternalServerErrorException(Throwable cause) {
		super(cause);
	}

	public InternalServerErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}
