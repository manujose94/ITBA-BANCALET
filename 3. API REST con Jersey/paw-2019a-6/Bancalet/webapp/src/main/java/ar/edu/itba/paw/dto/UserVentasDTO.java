package ar.edu.itba.paw.dto;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import ar.edu.itba.paw.models.Historial;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

public class UserVentasDTO {
	private TreeMap<Long, Item> itemsvendidos;
	private TreeMap<Long, Historial> ventas;
	private TreeMap<Long, User> compradores;
	private User user;
	private long userid;

	public UserVentasDTO() {
		super();
	}

	public UserVentasDTO(User user, Collection<Item> itemsvendidos, Collection<Historial> ventas,
			Collection<User> compradores) {
		super();
		this.setUser(user);
		this.setUserid(user.getUserId());
		
		this.itemsvendidos = new TreeMap<Long, Item>();
		for (Iterator<Item> iterator = itemsvendidos.iterator(); iterator.hasNext();) {
			Item item = iterator.next();
			this.itemsvendidos.put(item.getItemId(), item);
		}

		this.ventas = new TreeMap<Long, Historial>();
		for (Iterator<Historial> iterator = ventas.iterator(); iterator.hasNext();) {
			Historial his = iterator.next();
			this.ventas.put(his.getIdHistorico(), his);
		}

		this.compradores = new TreeMap<Long, User>();
		for (Iterator<User> iterator = compradores.iterator(); iterator.hasNext();) {
			User user2 = iterator.next();
			this.compradores.put(user2.getUserId(), user2);
		}

	}

	public TreeMap<Long, Item> getItemsvendidos() {
		return itemsvendidos;
	}

	public void setItemsvendidos(TreeMap<Long, Item> itemsvendidos) {
		this.itemsvendidos = itemsvendidos;
	}

	public TreeMap<Long, Historial> getListaventas() {
		return ventas;
	}

	public void setListaventas(TreeMap<Long, Historial> ventas) {
		this.ventas = ventas;
	}

	public TreeMap<Long, User> getListcompradores() {
		return compradores;
	}

	public void setListcompradores(TreeMap<Long, User> compradores) {
		this.compradores = compradores;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

}
