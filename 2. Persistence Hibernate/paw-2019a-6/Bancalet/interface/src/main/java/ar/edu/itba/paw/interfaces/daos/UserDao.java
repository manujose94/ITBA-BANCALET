package ar.edu.itba.paw.interfaces.daos;

import java.util.Collection;

import ar.edu.itba.paw.models.User;

public interface UserDao {

	/**
	 * Encuentra un usuario a partir de su id de usuario. La id de usuario es la
	 * clave primaria.
	 *
	 * @param userId El id del usuario que queremos buscar.
	 * @return El usuario cuyo id corresponda con el userId dado.
	 */
	User findById(long userId);

	/**
	 * Encuentra un usuario a partir de su nombre de usuario. El nombre de usuario
	 * es unico.
	 *
	 * @param username El nombre de usuario del usuario que queremos buscar.
	 * @return El usuario cuyo username corresponda con el username dado.
	 */
	User findByUsername(String username);

	/**
	 * Encuentra todos los usuarios.
	 *
	 * @return Todos los usuarios.
	 */
	Collection<User> findAll();

	/**
	 * Crea un nuevo usuario sin imagen de perfil.
	 *
	 * @param telf      El telefono del usuario. Es unico.
	 * @param email     El email del telefono. Es unico.
	 * @param city      La ciudad del usuario.
	 * @param country   El pais del usuario.
	 * @param code      El codigo postal del usuario.
	 * @param username  El nombre de usuario del usuario.
	 * @param password  La contraseña del usuario.
	 * @param role      El rol del usuario. Definido en User model.
	 * @param direccion La direccion del usuario.
	 * @param rate      La puntuacion media de las ventas del usuario.
	 * @param lat       La latitud del usuario.
	 * @param lon       La longitud del usuario.
	 * @param estado    El estado del usuario.
	 * @return El usuario creado.
	 */
	User create(String telf, String email, String city, String country, String code, String username, String password,
			String role, String direccion, double rate, double lat, double lon, int estado);

	/**
	 * Crea un nuevo usuario completo.
	 *
	 * @param telf      El telefono del usuario. Es unico.
	 * @param email     El email del telefono. Es unico.
	 * @param city      La ciudad del usuario.
	 * @param country   El pais del usuario.
	 * @param code      El codigo postal del usuario.
	 * @param username  El nombre de usuario del usuario.
	 * @param password  La contraseña del usuario.
	 * @param role      El rol del usuario. Definido en User model.
	 * @param direccion La direccion del usuario.
	 * @param numImg    El numero de la imagen por default si el usuario decide
	 *                  usarla.
	 * @param urlImg    En el caso de usar una imagen a partir de una url.
	 * @param image     En el caso de que el usuario suba una imagen propia.
	 * @param rate      La puntuacion media de las ventas del usuario.
	 * @param lat       La latitud del usuario.
	 * @param lon       La longitud del usuario.
	 * @param estado    El estado del usuario.
	 * @return El usuario creado.
	 */
	User create(String telf, String email, String city, String country, String code, String username, String password,
			String role, String direccion, int numImg, String urlImg, byte[] image, double rate, double lat, double lon,
			int estado);

	/**
	 * Crea un nuevo usuario administrador. No se usa actualmente, debe recibir mas
	 * parametros.
	 * 
	 * @deprecated
	 * @param username El nombre de usuario del usuario.
	 * @param password La contraseña del usuario.
	 * @return El usuario administrador creado.
	 */
	@Deprecated
	User createAdmin(String username, String password);

	/**
	 * Actualiza todos los campos del usuario dado.
	 * 
	 * @param user El usuario que queremos actualizar.
	 * @return devuelve negativo si hay error, prositivo si la operacion es
	 *         correcta.
	 */
	int update(User user);

	/**
	 * Devuelve el numero total de usuarios que hay en la base de datos.
	 * 
	 * @return El numero total de usuarios.
	 */
	long getTotalUsers();

	/**
	 * Devuelve todos los usuarios que tienen el rol dado.
	 * 
	 * @param role El rol de los usuarios que queremos obtener.
	 * @return Todos los usuarios del rol dado.
	 */
	Collection<User> findByuserRole(String role);

	/**
	 * Devuelve todos los usuarios que tienen el username similar al dado. Es un
	 * filtro.
	 * 
	 * @param name Parte del nombre de los usuarios que queremos obtener.
	 * @return Todos los usuarios que contengan nombre en su username.
	 */
	Collection<User> filterByName(String name);

	/**
	 * Devuelve todos los usuarios que tienen el rol dado y el username similar al
	 * name dado.Es un filtro.
	 * 
	 * @param name Parte del nombre de los usuarios que queremos obtener.
	 * @param role El rol de los usuarios que queremos obtener.
	 * @return Tods los usuarios del rol dado y con parte del username dado.
	 */
	Collection<User> findByFilter(String name, String role);

	/**
	 * Devuelve todos los usuarios que contactaron a un item a partir del id del
	 * item.
	 * 
	 * @param itemId El id de item del que queremos obtener sus usuarios.
	 * @return Todos los usuarios que contactaron al item..
	 */
	Collection<User> findAllUserContactsItem(long itemId);

	/**
	 * Encuentra un usuario a partir de su email. El email es unico.
	 *
	 * @param email El email del usuario que queremos buscar.
	 * @return El usuario cuyo email corresponda con el email dado.
	 */
	User findByEmail(String email);

	/**
	 * Devuelve todos los usuarios que compraron un item al usuario con el
	 * identificador dado.
	 * 
	 * @param idVendedor El id de usuario del que queremos obtener los usuarios que
	 *                   le compraron items.
	 * @return Todos los usuarios que le compraron items.
	 */
	Collection<User> findByidVendedorconHistorial(long idVendedor);

	/**
	 * Devuelve todos los usuarios que cuyo estado sea el dado.
	 * 
	 * @param estado El estado que deben tener los usuarios que queremos obtener.
	 * @return Todos los usuarios que tengan el estado dado.
	 */
	Collection<User> findByEstado(int estado);

	/**
	 * Devuelve todos los usuarios que cuyo estado sea el dado y que su username
	 * contenga el name dado.
	 * 
	 * @param name   El contenido del username del usuario que queremos obtener.
	 * @param estado El estado que deben tener los usuarios que queremos obtener.
	 * @return Todos los usuarios que tengan el estado dado y que contengan name en
	 *         username.
	 */
	Collection<User> findByNameEstado(String name, int estado);

	/**
	 * Devuelve todos los usuarios que cuyo estado sea el dado y cuyo rol sea el
	 * dado.
	 * 
	 * @param estado El estado que deben tener los usuarios que queremos obtener.
	 * @param role   El rol del usuario que queremos obtener.
	 * @return Todos los usuarios que tengan el estado dado y el rol dado.
	 */
	Collection<User> findByuserRoleEstado(String role, int estado);

	/**
	 * Devuelve todos los usuarios que cuyo estado sea el dado, que su username
	 * contenga el name dado y que su rol sea el dado.
	 * 
	 * @param name   El contenido del username del usuario que queremos obtener.
	 * @param estado El estado que deben tener los usuarios que queremos obtener.
	 * @param role   El rol del usuario que queremos obtener.
	 * @return Todos los usuarios que tengan el estado dado, que contengan name en
	 *         username y que el rol sea el dado.
	 */
	Collection<User> findByFilterEstado(String name, String role, int estado);

	/**
	 * Devuelve los usuarios con el mayor numero de ventas
	 * 
	 * @return
	 */
	Collection<User> getMaxVendor();

	/**
	 * Devuelve los usuarios con el mayor numero de compras
	 * 
	 * @return
	 */
	Collection<User> getMaxBuyer();

	/**
	 * Devuelve los usuarios con el mayor valor de ratio
	 * 
	 * @return
	 */
	Collection<User> getMaxRated();

	/**
	 * Devuelve los usuarios ACTIVOS localizados a un rango en Km del punto
	 * (lat-lon). Ordenados por proximidad
	 * 
	 * @param lat
	 * @param lon
	 * @param rangeKm
	 * @return
	 */
	Collection<User> findByUserNear(double lat, double lon, int rangeKm);

	Collection<User> findByidCompradorconHistorial(long idComprador);
}
