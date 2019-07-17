package ar.edu.itba.paw.interfaces.daos;

import java.sql.Date;
import java.util.Collection;

import ar.edu.itba.paw.models.Historial;

public interface HistorialDao {

	/**
	 * Finds the Item by the id.
	 *
	 * @param Itemname The id to search.
	 * @return the Item with said id.
	 */
	Historial findByIds(final long idcomprador, final long idvendedor, final long iditem);

	/**
	 * Finds the Item by the Itemname.
	 *
	 * @param Itemname The Itemname to search.
	 * @return the Item with said Itemname.
	 */
	Historial findByIdItem(long iditem);

	/**
	 * Finds the Item by the Itemname.
	 *
	 * @param Itemname The Itemname to search.
	 * @return the Item with said Itemname.
	 */
	Historial findByIdhistorico(long idhistorico);

	/**
	 * Create a new Item.
	 *
	 * @param Itemname The name of the Item.
	 * @param password The password of the Item.
	 * @param roles    A set of roles.
	 * @return The created Item.
	 */
	Historial create(long idcomprador, long idvendedor, long iditem, String valoracion, int estrellas,
			Date fecha_compra);

	/**
	 * Finds the Item by the Itemname.
	 *
	 * @param Itemname The Itemname to search.
	 * @return the Item with said Itemname.
	 */
	Collection<Historial> findByidvendedor(long idvendedor);

	/**
	 * Finds the Item by the Itemname.
	 *
	 * @param Itemname The Itemname to search.
	 * @return the Item with said Itemname.
	 */
	Collection<Historial> findByidcomprador(long idcomprador);

	/**
	 * Updates all the contents of the Item.
	 *
	 * @param Item The Item to be updated.
	 */
	void update(Historial hist);

	/**
	 * Returns the number of Items
	 *
	 * @return number of Items.
	 */
	int getTotalHistorials();

	/**
	 * Returns the number of Items
	 *
	 * @return number of Items.
	 */
	int getTotalHistorialsFromVendedor(long idvendedor);

	/**
	 * Returns the number of Items
	 *
	 * @return number of Items.
	 */
	int getTotalHistorialsFromComprador(long idcomprador);

	/**
	 * Returns all the Items paginated.
	 *
	 * @return all the Items.
	 */
	Collection<Historial> findAll();
}
