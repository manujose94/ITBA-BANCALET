package ar.edu.itba.paw.dto;

import ar.edu.itba.paw.models.Contacto;

public class ContactoDTO {
	private Contacto contacto;
	private long idContacto;
	
	public ContactoDTO() {
		super();
	}

	public ContactoDTO(Contacto contacto) {
		super();
		this.contacto = contacto;
		this.idContacto = contacto.getIdContacto();
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public long getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(long idContacto) {
		this.idContacto = idContacto;
	}

}
