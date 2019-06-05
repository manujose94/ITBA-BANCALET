package ar.edu.itba.paw.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class ContactAdminForm {

	@Size(min = 3, max = 30)
	private String name;

	@Size(min = 3, max = 20)
	private String subject;

	@Size(min = 10, max = 500)
	private String mensaje;

	@Size(min = 5, max = 40, message = "Size")
	@Email(message = "Email.error")
	private String email;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
