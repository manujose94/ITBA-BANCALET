package ar.edu.itba.paw.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemRegisterForm {

	@Size(min = 6, max = 40, message = "El nombre no puede estar vacío")
	// @Pattern(regexp = "[a-zñÑA-Z0-9]+")
	private String name;

	@Size(min = 6, max = 500, message = "La descripción no puede estar vacía")
	// @Pattern(regexp = "[a-zA-Z0-9]+")
	private String description;

	@NotNull(message = "Please enter id")
	private int tipo;

	@NotNull(message = "Please enter id")
	private double price;

	@NotEmpty(message = "La fecha de caducidad no puede estar vacía")
	private String fecha_caducidad;

	private byte[] image;

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getFecha_caducidad() {
		return fecha_caducidad;
	}

	public void setFecha_caducidad(String fecha_caducidad) {
		this.fecha_caducidad = fecha_caducidad;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
