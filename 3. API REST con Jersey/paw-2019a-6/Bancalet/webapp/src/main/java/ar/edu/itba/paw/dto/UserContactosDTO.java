package ar.edu.itba.paw.dto;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import ar.edu.itba.paw.models.Contacto;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

public class UserContactosDTO {

	private TreeMap<Long, User> listcontactados;
	private TreeMap<Long, Contacto> contactos;
	private int size;
	private int tipo;
	private User user;
	private TreeMap<Long, Item> items;
	private long userid;

	public UserContactosDTO() {
		super();
	}

	public UserContactosDTO(User user, Collection<User> listcontactados, Collection<Contacto> contactos, int size,
			Collection<Item> items) {
		super();

		this.listcontactados = new TreeMap<Long, User>();
		for (Iterator<User> iterator = listcontactados.iterator(); iterator.hasNext();) {
			User user2 = iterator.next();
			this.listcontactados.put(user2.getUserId(), user2);
		}

		this.contactos = new TreeMap<Long, Contacto>();
		for (Iterator<Contacto> iterator = contactos.iterator(); iterator.hasNext();) {
			Contacto con = iterator.next();
			this.contactos.put(con.getIdContacto(), con);
		}

		this.size = size;
		this.user = user;
		this.userid = user.getUserId();

		this.items = new TreeMap<Long, Item>();
		for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
			Item item = iterator.next();
			this.items.put(item.getItemId(), item);
		}
	}

	public TreeMap<Long, User> getListcontactados() {
		return listcontactados;
	}

	public void setListcontactados(TreeMap<Long, User> listcontactados) {
		this.listcontactados = listcontactados;
	}

	public TreeMap<Long, Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(TreeMap<Long, Contacto> contactos) {
		this.contactos = contactos;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TreeMap<Long, Item> getItems() {
		return items;
	}

	public void setItems(TreeMap<Long, Item> items) {
		this.items = items;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

}
