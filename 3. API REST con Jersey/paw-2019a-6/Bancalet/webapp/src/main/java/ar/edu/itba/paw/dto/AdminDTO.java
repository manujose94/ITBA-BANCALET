package ar.edu.itba.paw.dto;

public class AdminDTO {

	private long totalUsers;
	private long totalAltaItems;
	private long totalItems;
	private long totalAyudas;

	public AdminDTO() {
		super();
	}

	public AdminDTO(long totalUsers, long totalAltaItems, long totalItems, long totalAyudas) {
		super();
		this.totalUsers = totalUsers;
		this.totalAltaItems = totalAltaItems;
		this.totalItems = totalItems;
		this.totalAyudas = totalAyudas;
	}

	public long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public long getTotalAltaItems() {
		return totalAltaItems;
	}

	public void setTotalAltaItems(long totalAltaItems) {
		this.totalAltaItems = totalAltaItems;
	}

	public long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}

	public long getTotalAyudas() {
		return totalAyudas;
	}

	public void setTotalAyudas(long totalAyudas) {
		this.totalAyudas = totalAyudas;
	}

}
