package ar.edu.itba.paw.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import ar.edu.itba.paw.constraints.EmailInUse;

public class AdminUserEditForm {

	@Size(min = 3, max = 15)
	@Pattern(regexp = "[0-9]+")
	private String telf;

	@EmailInUse
	@Size(min = 5, max = 40)
	@Email(message = "Email.error")
	private String email;

	@Size(min = 3, max = 40)
	@Pattern(regexp = "[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+")
	private String city;

	@Size(min = 3, max = 40)
	@Pattern(regexp = "[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+")
	private String country;

	@Size(min = 3, max = 40)
	@Pattern(regexp = "[a-zA-Z0-9]+")
	private String code;

	@Size(min = 3, max = 150)
	// @Pattern(regexp = "[a-zA-Z0-9]+")
	private String direccion;

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelf() {
		return telf;
	}

	public void setTelf(String telf) {
		this.telf = telf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
