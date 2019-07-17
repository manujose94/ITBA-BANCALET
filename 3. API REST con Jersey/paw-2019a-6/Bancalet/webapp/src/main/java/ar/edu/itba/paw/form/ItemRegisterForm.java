package ar.edu.itba.paw.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ItemRegisterForm {

	@Size(min = 6, max = 40, message = "Size.name")
	private String name;

	@Size(min = 6, max = 500, message = "size.error")
	private String description;

	@NotNull(message = "not.empty")
	private int tipo;

	@NotNull(message = "not.empty")
	private double price;

	@NotNull(message = "not.empty")
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
