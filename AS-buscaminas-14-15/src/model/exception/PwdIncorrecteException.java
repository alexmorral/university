package model.exception;

public class PwdIncorrecteException extends Exception{

	private static final long serialVersionUID = 1L;

	public PwdIncorrecteException() {
		super();
	}
	
	public PwdIncorrecteException(String message) {
		super(message);
	}
}
