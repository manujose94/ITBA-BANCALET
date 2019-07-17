package ar.edu.itba.paw.dto;

public class BooleanDTO {
	private boolean value;

	public BooleanDTO(boolean res) {
		super();
		this.setValue(res);
	}

	public BooleanDTO() {
		super();
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

}
