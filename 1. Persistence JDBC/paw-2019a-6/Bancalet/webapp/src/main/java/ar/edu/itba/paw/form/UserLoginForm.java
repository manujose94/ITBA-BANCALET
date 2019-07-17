package ar.edu.itba.paw.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserLoginForm {

	@Size(min = 6, max = 15)
	@NotNull(message = "El usuario es obligatorio")
	@Pattern(regexp = "[a-zñÑA-Z0-9]+")
	private String username;

	@Size(min = 6, max = 40)
	@NotNull(message = "La contraseña es obligatoria")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
