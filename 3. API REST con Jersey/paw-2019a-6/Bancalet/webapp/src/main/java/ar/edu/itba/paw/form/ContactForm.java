package ar.edu.itba.paw.form;

import javax.validation.constraints.Size;

public class ContactForm {

	@Size(min = 10, max = 500, message = "size.error")
	private String mensaje;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
