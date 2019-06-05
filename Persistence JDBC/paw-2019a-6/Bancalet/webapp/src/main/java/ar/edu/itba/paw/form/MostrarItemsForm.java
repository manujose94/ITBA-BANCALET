package ar.edu.itba.paw.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MostrarItemsForm {

	@Size(min = 4, max = 10)
	@Pattern(regexp = "^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$")
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
