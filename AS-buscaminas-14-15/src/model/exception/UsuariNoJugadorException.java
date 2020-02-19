package model.exception;

public class UsuariNoJugadorException extends Exception{

	private static final long serialVersionUID = 1L;

	public UsuariNoJugadorException() {
		super();
	}
	
	public UsuariNoJugadorException(String message) {
		super(message);
	}
}
