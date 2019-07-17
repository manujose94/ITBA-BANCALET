package ar.edu.itba.paw.dto;

public class MensajeDTO {
	private String mensaje;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public MensajeDTO(String mensaje) {
		super();
		this.mensaje = mensaje;
	}

	public MensajeDTO() {
		super();
	}

}
