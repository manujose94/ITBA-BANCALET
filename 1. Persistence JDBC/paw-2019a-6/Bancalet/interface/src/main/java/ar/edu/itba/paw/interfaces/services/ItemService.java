package ar.edu.itba.paw.interfaces.services;

import java.sql.Date;
import java.util.Collection;

import ar.edu.itba.paw.models.Item;

public interface ItemService {

	/**
	 * Finds the Item by the id.
	 *
	 * @param Itemname The id to search.
	 * @return the Item with said id.
	 */
	Item findById(final long id);

	/**
	 * Create a new Item.
	 *
	 * @param Itemname The name of the Item.
	 * @param password The password of the Item.
	 * @param roles    A set of roles.
	 * @return The created Item.
	 */
	Item create(long idvendedor, String name, int tipo, double price, String description, Date fecha_caducidad,
			Date fecha_publicacion, byte[] image, String estado, long numeroVisitas);

	/**
	 * Finds the Item by the Itemname.
	 *
	 * @param Itemname The Itemname to search.
	 * @return the Item with said Itemname.
	 */
	Collection<Item> findByItemname(String Itemname);

	/**
	 * Finds the Item by the Itemname.
	 *
	 * @param Itemname The Itemname to search.
	 * @return the Item with said Itemname.
	 */
	Collection<Item> findByType(int tipo);

	/**
	 * Finds the Item by the Itemname.
	 *
	 * @param Itemname The Itemname to search.
	 * @return the Item with said Itemname.
	 */
	Collection<Item> findByEstado(String estado);

	/**
	 * Updates all the contents of the Item.
	 *
	 * @param Item The Item to be updated.
	 */
	void update(Item Item);

	/**
	 * Returns the number of Items
	 *
	 * @return number of Items.
	 */
	int getTotalItems();

	/**
	 * Returns all the Items paginated.
	 *
	 * @return all the Items.
	 */
	Collection<Item> findAll();

	Collection<Item> findByFilter(int tipo, String estado, double min, double max, String nombre);

	Double getMaxPrice();

	Collection<Item> findByVendedorId(long idvendedor);

	Collection<Item> findByVendedorId(long idvendedor, String estado);

	int delete(long itemid);

	public Collection<Item> findAllAlta();

	int baja(long itemid, long idcomprador, long idvendedor, String valoracion, int estrellas, Date fecha_compra);

	Collection<Item> findByVendedorIdContactados(long idvendedor, boolean isContactado);

	Collection<Item> findByVendedorIdHistorial(long idvendedor, boolean isHistorial);
}
