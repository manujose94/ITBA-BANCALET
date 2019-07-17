package ar.edu.itba.paw.dto;

public class AdminAyudasDTO {

	private long totalAyudas;

	public AdminAyudasDTO() {
		super();
	}

	public AdminAyudasDTO(long totalAyudas) {
		super();
		this.totalAyudas = totalAyudas;
	}

	public long getTotalAyudas() {
		return totalAyudas;
	}

	public void setTotalAyudas(long totalAyudas) {
		this.totalAyudas = totalAyudas;
	}

}
