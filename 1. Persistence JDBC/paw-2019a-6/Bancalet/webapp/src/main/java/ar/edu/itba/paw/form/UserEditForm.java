
package ar.edu.itba.paw.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserEditForm {

	@Size(min = 3, max = 15)
	@Pattern(regexp = "[0-9]+")
	private String telf;

	@Size(min = 3, max = 40)
	@Pattern(regexp = "[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+")
	private String city;

	@Size(min = 3, max = 40)
	@Pattern(regexp = "[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+")
	private String country;

	@Size(min = 3, max = 15)
	@Pattern(regexp = "[a-zA-Z0-9]+")
	private String code;

	@Size(min = 3, max = 150)
	// @Pattern(regexp = "[a-zA-Z0-9]+")
	private String direccion;

	public String getTelf() {
		return telf;
	}

	public void setTelf(String telf) {
		this.telf = telf;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
