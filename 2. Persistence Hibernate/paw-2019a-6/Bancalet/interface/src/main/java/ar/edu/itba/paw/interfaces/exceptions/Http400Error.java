package ar.edu.itba.paw.interfaces.exceptions;

@SuppressWarnings("serial")
public class Http400Error extends HttpError {

	public Http400Error(String title, String body) {
		super(title, body, 400);
	}

}
