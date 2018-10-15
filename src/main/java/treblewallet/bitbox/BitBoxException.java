package treblewallet.bitbox;

public class BitBoxException extends RuntimeException{

	private static final long serialVersionUID = -3959817647990175128L;

	public BitBoxException(String message) {
		super(message);
	}
	public BitBoxException(String message, Throwable cause) {
		super(message, cause);
	}
}
