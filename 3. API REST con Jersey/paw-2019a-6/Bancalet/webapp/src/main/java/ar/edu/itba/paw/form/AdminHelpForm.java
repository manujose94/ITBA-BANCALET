package ar.edu.itba.paw.form;

import javax.validation.constraints.Size;

public class AdminHelpForm {

	@Size(min = 3, max = 500)
	private String informe;

	public String getInforme() {
		return informe;
	}

	public void setInforme(String informe) {
		this.informe = informe;
	}
}
