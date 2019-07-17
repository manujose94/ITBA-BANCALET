package ar.edu.itba.paw.interfaces.daos;

import java.util.Date;
import java.util.Collection;

import ar.edu.itba.paw.models.Item;

public interface ItemDao {

	/**
	 * Encuentra un item a partir de su id. La id es la clave primaria.
	 *
	 * @param itemId La id del item que queremos.
	 * @return El item cuyo id coincida con el dado.
	 */
	Item findById(final long itemId);

	/**
	 * Crea un nuevo item.
	 *
	 * @param idVendedor       El id del usuario vendedor del item.
	 * @param name             El nombre del item.
	 * @param tipo             El tipo de item que es, definido en Item model.
	 * @param price            El precio del item.
	 * @param description      La descripcion del item.
	 * @param fechaCaducidad   La fecha en la que expira el item.
	 * @param fechaPublicacion La fecha en la que se publica/crea el item.
	 * @param image            La imagen del item.
	 * @param estado           El estado del item, definido en Item model.
	 * @param numeroVisitas    El numero de visitas que tiene el item.
	 * @return El nuevo item creado.
	 */
	Item create(long idVendedor, String name, int tipo, double price, String description, Date fechaCaducidad,
			Date fechaPublicacion, byte[] image, int estado, long numeroVisitas);

	/**
	 * Encuentra un item por su nombre, puede estar contenido, es un filtro.
	 *
	 * @param name El nombre que debe contener.
	 * @return Todos los items que contienen el nombre.
	 */
	Collection<Item> findByItemname(String name);

	/**
	 * Encuentra un item por su tipo.
	 *
	 * @param tipo El tipo del item.
	 * @return Todos los items que correspondan con el tipo dado.
	 */
	Collection<Item> findByType(int tipo);

	/**
	 * Encuentra un item por su estado.
	 *
	 * @param estado El estado del item, definido en Item model.
	 * @return Todos los items que correspondan con el estado dado.
	 */
	Collection<Item> findByEstado(int estado);

	/**
	 * Actualiza todos los campos del item dado.
	 * 
	 * @param item El item que queremos actualizar.
	 * @return devuelve negativo si hay error, prositivo si la operacion es
	 *         correcta.
	 */
	int update(Item item);

	/**
	 * Devuelve el numero total de items que hay en la base de datos.
	 * 
	 * @return El numero total de items.
	 */
	long getTotalItems();

	/**
	 * Devuelve todos los items que hay en la base de datos.
	 * 
	 * @return Todos los items que hay.
	 */
	Collection<Item> findAll();

	/**
	 * Filtro que devuelve aquellos items que sean de un tipo dado, de un estado
	 * dado, de un nombre contenido y de precio entre min y max.
	 * 
	 * @param tipo   El tipo de los items que queremos.
	 * @param estado El estado de los items que queremos.
	 * @param min    El precio minimo de los items que queremos.
	 * @param max    El precio maximo de los items que queremos.
	 * @param nombre El nombre de los items debe contener este campo.
	 * @return Todos los items que correspondan con los filtros dados.
	 */
	Collection<Item> findByFilter(int tipo, int estado, double min, double max, String nombre);

	/**
	 * Devuelve el precio del item con mayor precio de la base de datos.
	 * 
	 * @return El precio del item con maximo precio.
	 */
	Double getMaxPrice();

	/**
	 * Devuelve todos los items del usuario vendedor con id dado. Todos los items
	 * que vende un usuario.
	 * 
	 * @param idVendedor Es la id del usuaio que vende el item.
	 * @return Todos los items que hay en estado ALTA.
	 */
	Collection<Item> findByVendedorId(long idVendedor);

	/**
	 * Elimina el item con iditem dado.
	 * 
	 * @param itemId El id del item a eliminar los items.
	 * @return El numero de items eliminados (debe ser siempre 1 porque es clave
	 *         primaria).
	 */
	int delete(long itemId);

	/**
	 * Devuelve todos los items que hay en la base de datos en estado ALTA.
	 * 
	 * @return Todos los items que hay en estado ALTA.
	 */
	Collection<Item> findAllAlta();

	/**
	 * Actualiza un item de estado ALTA a estado de BAJA. Definido en Item model.
	 * 
	 * @param itemId El id del item a modificar los contactos.
	 * @return El numero de items modificados (debe ser siempre 1 porque es clave
	 *         primaria).
	 */
	int baja(long itemId);

	/**
	 * Actualiza todos los item de estado BAJA a estado de ALTAde un usuario dado su
	 * id. Definido en Item model. Da de baja los contactos de este item.No crea el
	 * historial para este item. SOLO USAR PARA CUANDO EL ADMIN DA DE BAJA UN
	 * USUARIO, DA DE BAJA SUS ITEMS Y CONTACTOS.
	 * 
	 * @param userId El id del usuario.
	 * @return El numero de items modificados (puede ser mayor a 1, todos los items
	 *         ALTA del user).
	 */
	int bajaAdmin(long userId);

	/**
	 * Actualiza todos los items de estado BAJA a estado de ALTAde un usuario dado
	 * su id. Definido en Contacto model. Da de baja los contactos del usuario. SOLO
	 * USAR PARA CUANDO EL ADMIN DA DE BAJA UN USUARIO, DA DE BAJA SUS ITEMS Y
	 * CONTACTOS.
	 * 
	 * @param userId El id del usuario.
	 * @return El numero de contactos modificados (puede ser mayor a 1, todos los
	 *         contactos ALTA del user).
	 */
	int altaAdmin(long userId);

	/**
	 * Devuelve todos los items del usuario vendedor con id dado y que esten el
	 * estado dado.
	 * 
	 * @param idVendedor El id del usuario que vende el item.
	 * @param estado     El estado de los items que queremos.
	 * @return Todos los items en el estado dado y cuyo idVendedor corresponda con
	 *         la id dada.
	 */
	Collection<Item> findByVendedorId(long idVendedor, int estado);

	/**
	 * Devuelve todos los items del usuario vendedor con id dado y filtramos si han
	 * sido contactados o no.
	 * 
	 * @param idVendedor   El id del usuario que vende el item.
	 * @param isContactado Si queremos items contactados o no.
	 * @return Todos los items en el cuyo idVendedor corresponda con la id dada y
	 *         que corresponda con nuestro criterio.
	 */
	Collection<Item> findByVendedorIdContactados(long idVendedor, boolean isContactado);

	/**
	 * Devuelve todos los items del usuario vendedor con id dado y filtramos si
	 * tienen historial o no.
	 * 
	 * @param idVendedor   El id del usuario que vende el item.
	 * @param isContactado Si queremos items con historial o no.
	 * @return Todos los items en el cuyo idVendedor corresponda con la id dada y
	 *         que corresponda con nuestro criterio.
	 */
	Collection<Item> findByVendedorIdHistorial(long idVendedor, boolean isHistorial);

	/**
	 * Devuelve los items ACTIVOS localizados a un rango en Km del punto (lat-lon).
	 * Ordenados por proximidad
	 * 
	 * @param lat
	 * @param lon
	 * @param rangeKm
	 * @return
	 */
	Collection<Item> findByItemNear(double lat, double lon, int rangeKm);

	Collection<Item> findByFilterDate(int tipo, int estado, double min, double max, String name, Date date,
			int antesDespues);

	Collection<Item> findByTipoEstado(int tipo, int estado);

	Collection<Item> findByRecomendacion(int tipo, int estado, long userId);

	Collection<Item> findExpirados();

	Collection<Item> findByCompradorId(long idComprador, int estado);

	Collection<Item> findByCompradorIdContactados(long idComprador, boolean isContactado);
}
