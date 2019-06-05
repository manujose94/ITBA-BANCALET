package ar.edu.itba.paw.interfaces.exceptions;

public class UsuarioExistente extends HttpError {
	public UsuarioExistente(String title, String body) {
		super(title, body, 409);
	}
}
