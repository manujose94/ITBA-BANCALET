package ar.edu.itba.paw.interfaces.daos;

import java.util.Collection;

import ar.edu.itba.paw.models.User;

public interface UserDao {

	/**
	 * Finds the User by the id.
	 *
	 * @param username The id to search.
	 * @return the user with said id.
	 */
	User findById(final long id);

	/**
	 * Finds the User by the username.
	 *
	 * @param username The username to search.
	 * @return the user with said username.
	 */
	User findByUsername(String username);

	/**
	 * Returns all the users.
	 *
	 * @param maxResults Max results allowed. Alias page size
	 * @param offset     First result start. Alias first result
	 * @return all the users.
	 */
	Collection<User> findAll();

	/**
	 * Create a new user.
	 *
	 * @param username The name of the user.
	 * @param password The password of the user.
	 * @param roles    A set of roles.
	 * @return The created user.
	 */
	User create(String telf, String email, String city, String country, String code, String username, String password,
			String userRoles, String direccion);

	/**
	 * Creates a user with admin privileges.
	 *
	 * @param username the admins username.
	 * @param password the admins password.
	 * @return the created user.
	 */
	User createAdmin(String username, String password);

	/**
	 * Updates all the contents of the user.
	 *
	 * @param user The user to be updated.
	 */
	void update(User user);

	/**
	 * Returns the number of users
	 *
	 * @return number of users.
	 */
	int getTotalUsers();

	Collection<User> findByuserRole(String role);

	Collection<User> findByName(String name);

	Collection<User> findByFilter(String name, String role);

	Collection<User> findAllUserContactsItem(long idtem);

	User findByEmail(String email);

	Collection<User> findByIdVendedorconHistorial(long idvendedor, boolean isHistorial);
}
