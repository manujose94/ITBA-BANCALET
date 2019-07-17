package ar.edu.itba.paw.interfaces.services;

import java.sql.Date;
import java.util.Collection;

import ar.edu.itba.paw.models.Historial;

public interface HistorialService {
	/**
	 * Encuentra un historial a partir de su itemId, ide del usuario comprador y la
	 * id del usuario vendedor. El itemId en historial es unico, solo hay una venta
	 * para cada item.
	 *
	 * @param idComprador Es la id del usaurio comprador del item.
	 * @param idVendedor  Es la id del usuario vendedor del item.
	 * @param itemId      El itemId del item que queremos buscar.
	 * @return El historial cuyo itemId corresponda con el itemId dado.
	 */
	Historial findByIds(long idComprador, long idVendedor, long itemId);

	/**
	 * Encuentra un historial a partir de su itemId. El itemId en historial es
	 * unico, solo hay una venta para cada item.
	 *
	 * @param itemId El itemId del historial que queremos buscar.
	 * @return El historial cuyo itemId corresponda con el itemId dado.
	 */
	Historial findByIdItem(long itemId);

	/**
	 * Encuentra un historial a partir de su idHistorico. El idHistorico es la clave
	 * primaria.
	 *
	 * @param idHistorico La id del historial que queremos buscar.
	 * @return El historial cuyo id corresponda con el idHistorico dado.
	 */
	Historial findByidHistorico(long idHistorico);

	/**
	 * Crea un nuevo historial
	 *
	 * @param idComprador El id del usuario que compra el item.
	 * @param idVendedor  El id del usuario que vende el item.
	 * @param itemId      El id del item que se vende.
	 * @param valoracion  La valoracion por parte del comprador, se inicia vacía.
	 * @param estrellas   La puntuacion numerica por parte del comprador, se inicia
	 *                    vacía.
	 * @param fechaCompra La fecha en la que se hizo la venta del item.
	 * @return El nuevo historial creado.
	 */
	Historial create(long idComprador, long idVendedor, long itemId, String valoracion, int estrellas,
			Date fechaCompra);

	/**
	 * Encuentra los historiales para un usuario vendedor a partir de la idVendedor
	 * dada.
	 *
	 * @param idVendedor El id del usaurio vendedor.
	 * @return Todos los historiales para el ususario vendedor dado.
	 */
	Collection<Historial> findByidVendedor(long idVendedor);

	/**
	 * Encuentra los historiales para un usuario comprador a partir de la
	 * idComprador dada.
	 *
	 * @param idComprador El id del usaurio comprador.
	 * @return Todos los historiales para el ususario comprador dado.
	 */
	Collection<Historial> findByidComprador(long idComprador);

	/**
	 * Actualiza todos los campos del historial dado.
	 * 
	 * @param hist El historial que queremos actualizar.
	 * @return devuelve negativo si hay error, prositivo si la operacion es
	 *         correcta.
	 */
	int update(Historial hist);

	/**
	 * Devuelve el numero total de historiales que hay en la base de datos.
	 * 
	 * @return El numero total de historiales.
	 */
	int getTotalHistorials();

	/**
	 * Devuelve el numero total de historiales para un usuario vendedor a partir del
	 * idVendedor.
	 * 
	 * @param idVendedor El id del usuario vendedor
	 * @return El numero total de historiales para el usuario vendedor.
	 */
	int getTotalHistorialsFromVendedor(long idVendedor);

	/**
	 * Devuelve el numero total de historiales para un usuario comprador a partir
	 * del idComprador.
	 * 
	 * @param idComprador El id del usuario comprador
	 * @return El numero total de historiales para el usuario comprador.
	 */
	int getTotalHistorialsFromComprador(long idComprador);

	/**
	 * Devuelve la suma de todas las valoraciones numericas para un usuario vendedor
	 * dado su idVendedor
	 * 
	 * @param idVendedor El id del usuario vendedor
	 * @return La suma de todas las valoraciones del usuario vendedor.
	 */
	long findSumRatesFromUserId(long idVendedor);

	/**
	 * Devuelve el nuemro de valoraciones para un usuario vendedor dado su
	 * idVendedor
	 * 
	 * @param idVendedor El id del usuario vendedor
	 * @return El numero de valoraciones del usuario vendedor.
	 */
	long findNumRatedItemsFromUserId(long idVendedor);

	/**
	 * Devuelve Todos los historiales que hay en la base de datos.
	 * 
	 * @return Todos los historiales de la base de datos.
	 */
	Collection<Historial> findAll();

	long getUserHistorialNoRate(long userId);

	Collection<Historial> findByidCompradorNoRated(long userId);
}
