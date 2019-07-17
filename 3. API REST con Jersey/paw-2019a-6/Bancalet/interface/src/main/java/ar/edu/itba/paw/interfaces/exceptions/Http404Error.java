package ar.edu.itba.paw.interfaces.exceptions;

@SuppressWarnings("serial")
public class Http404Error extends HttpError {
	public Http404Error(String title, String body) {
		super(title, body, 404);
	}
}
