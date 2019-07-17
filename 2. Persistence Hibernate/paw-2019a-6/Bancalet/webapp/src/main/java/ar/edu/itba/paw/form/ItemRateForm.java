package ar.edu.itba.paw.form;

import javax.validation.constraints.Size;

public class ItemRateForm {

	private int estrellas;

	@Size(min = 10, max = 500, message = "size.error")
	private String valoracion;

	public int getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(int estrellas) {
		this.estrellas = estrellas;
	}

	public String getValoracion() {
		return valoracion;
	}

	public void setValoracion(String valoracion) {
		this.valoracion = valoracion;
	}

}
