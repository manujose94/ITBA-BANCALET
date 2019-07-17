package ar.edu.itba.paw.dto;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import ar.edu.itba.paw.models.Historial;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

public class UserComprasDTO {

	private TreeMap<Long, Historial> compras;
	private TreeMap<Long, User> compradores;
	private TreeMap<Long, Item> itemscomprados;
	private int compradoresSize;
	private int comprasSize;
	private int itemsSize;
	private User user;
	private long userId;

	public UserComprasDTO() {
		super();
	}

	public UserComprasDTO(User user, Collection<Historial> listacompras, Collection<User> listvendedores,
			Collection<Item> itemscomprados) {
		super();
		this.user = user;
		this.userId = user.getUserId();
		this.comprasSize = listacompras.size();
		this.compradoresSize = listvendedores.size();
		this.itemsSize = itemscomprados.size();
		this.itemscomprados = new TreeMap<Long, Item>();
		for (Iterator<Item> iterator = itemscomprados.iterator(); iterator.hasNext();) {
			Item item = iterator.next();
			if (item != null)
				this.itemscomprados.put(item.getItemId(), item);
		}
		compras = new TreeMap<Long, Historial>();
		for (Iterator<Historial> iterator = listacompras.iterator(); iterator.hasNext();) {
			Historial his = iterator.next();
			if (his != null)
				this.compras.put(his.getIdHistorico(), his);
		}
		compradores = new TreeMap<Long, User>();
		for (Iterator<User> iterator = listvendedores.iterator(); iterator.hasNext();) {
			User vendedor = iterator.next();
			if (vendedor != null)
				this.compradores.put(vendedor.getUserId(), vendedor);
		}

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public TreeMap<Long, Historial> getCompras() {
		return compras;
	}

	public void setCompras(TreeMap<Long, Historial> compras) {
		this.compras = compras;
	}

	public TreeMap<Long, User> getVendedores() {
		return compradores;
	}

	public void setVendedores(TreeMap<Long, User> vendedores) {
		this.compradores = vendedores;
	}

	public TreeMap<Long, Item> getItems() {
		return itemscomprados;
	}

	public void setItems(TreeMap<Long, Item> items) {
		this.itemscomprados = items;
	}

	public int getVendedoresSize() {
		return compradoresSize;
	}

	public void setVendedoresSize(int vendedoresSize) {
		this.compradoresSize = vendedoresSize;
	}

	public int getComprasSize() {
		return comprasSize;
	}

	public void setComprasSize(int comprasSize) {
		this.comprasSize = comprasSize;
	}

	public int getItemsSize() {
		return itemsSize;
	}

	public void setItemsSize(int itemsSize) {
		this.itemsSize = itemsSize;
	}

}
