package model.exception;

public class UsernameNoExisteixException extends Exception{

	private static final long serialVersionUID = 1L;

	public UsernameNoExisteixException() {
		super();
	}
	
	public UsernameNoExisteixException(String message) {
		super(message);
	}
}
