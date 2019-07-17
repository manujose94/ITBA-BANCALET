package ar.edu.itba.paw.dto;

import ar.edu.itba.paw.models.Ayuda;

public class AyudaDTO {

	private Ayuda issue;
	private long ayudaId;

	public AyudaDTO() {
		super();
	}

	public AyudaDTO(Ayuda issue) {
		super();
		this.issue = issue;
		this.ayudaId = issue.getIdAyuda();
	}

	public long getAyudaId() {
		return ayudaId;
	}

	public void setAyudaId(long ayudaId) {
		this.ayudaId = ayudaId;
	}

	public Ayuda getIssue() {
		return issue;
	}

	public void setIssue(Ayuda issue) {
		this.issue = issue;
	}

}
