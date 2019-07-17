package ar.edu.itba.paw.dto;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import ar.edu.itba.paw.models.Contacto;

public class ContactoListDTO {

	private TreeMap<Long, Contacto> contactos;
	private long size;

	public ContactoListDTO() {
		super();
	}

	public ContactoListDTO(Collection<Contacto> list) {
		super();
		this.size = list.size();
		contactos = new TreeMap<Long, Contacto>();
		for (Iterator<Contacto> iterator = list.iterator(); iterator.hasNext();) {
			Contacto contact = iterator.next();
			if (contact != null)
				this.contactos.put(contact.getItemId(), contact);
		}
	}

	public TreeMap<Long, Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(TreeMap<Long, Contacto> contactos) {
		this.contactos = contactos;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
