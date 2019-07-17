package ar.edu.itba.paw.service;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public User findById(final long id) {
		User u = userDao.findById(id);

		if (u == null) {
			LOGGER.debug("No existe usuario con ese id " + id);
		} else {
			LOGGER.debug("Recuperado el usuario " + u.getUsername() + " con id " + id);
		}
		return u;
	}

	@Override
	public User findByUsername(String username) {
		User u = userDao.findByUsername(username);

		if (u == null) {
			LOGGER.debug("No existe usuario con username " + username);
		} else {
			LOGGER.debug("Recuperado el usuario " + username + " con id " + u.getId());
		}
		return u;
	}

	@Override
	public Collection<User> findAll() {
		Collection<User> users = userDao.findAll();
		if (users != null) {
			LOGGER.debug("Se han encontrado " + users.size() + " usuarios y se devuelve la lista.");
			return users;
		} else {
			LOGGER.debug("No hay usuarios");
			return new HashSet<User>();
		}
	}

	@Override
	public int getTotalUsers() {
		int total = userDao.getTotalUsers();
		if (total > 0) {
			LOGGER.debug("Se han encontrado " + total + " usuarios.");
		} else {
			LOGGER.debug("No se han encontrado usuarios.");
		}
		return total;
	}

	@Override
	public void update(User user) {
		LOGGER.debug("Se actualiza el usuario " + user.getUsername() + " con id " + user.getId());
		userDao.update(user);
	}

	@Override
	public User create(String telf, String email, String city, String country, String code, String username,
			String password, String role, String direccion) {
		User u = userDao.create(telf, email, city, country, code, username, password, role, direccion);
		LOGGER.debug("Se crea el usuario " + u.getUsername() + " con id " + u.getId() + " con el rol " + u.getRole());
		return u;
	}

	@Override
	public User createAdmin(String username, String password) {
		User u = userDao.createAdmin(username, password);
		LOGGER.debug("Se crea el usuario " + u.getUsername() + " con id " + u.getId() + " con el rol " + u.getRole());
		return u;
	}

	@Override
	public Collection<User> findByuserRole(String role) {
		Collection<User> users = userDao.findByuserRole(role);
		if (users != null) {
			LOGGER.debug(
					"Se han encontrado " + users.size() + " usuarios con role " + role + " y se devuelve la lista.");
			return users;
		} else {
			LOGGER.debug("No hay usuarios con role " + role);
			return new HashSet<User>();
		}
	}

	@Override
	public Collection<User> findByName(String name) {
		Collection<User> users = userDao.findByName(name);
		if (users != null) {
			LOGGER.debug("Se han encontrado " + users.size() + " usuarios con username similar a " + name
					+ " y se devuelve la lista.");
			return users;
		} else {
			LOGGER.debug("No hay usuarios con username similar a " + name);
			return new HashSet<User>();
		}
	}

	@Override
	public Collection<User> findByFilter(String name, String role) {
		Collection<User> users = userDao.findByFilter(name, role);
		if (users != null) {
			LOGGER.debug("Se han encontrado " + users.size() + " usuarios con role " + role + " y username similar a "
					+ name + " y se devuelve la lista.");
			return users;
		} else {
			LOGGER.debug("No hay usuarios con role " + role + " y username similar a " + name);
			return new HashSet<User>();
		}
	}

	@Override
	public Collection<User> findAllUserContactsItem(long idtem) {
		Collection<User> users = userDao.findAllUserContactsItem(idtem);
		if (users != null) {
			LOGGER.debug("Se han encontrado " + users.size() + " usuarios con idtem " + idtem);
			return users;
		} else {
			LOGGER.debug("No hay usuarios con idtem " + idtem);
			return new HashSet<User>();
		}
	}

	@Override
	public User findByEmail(String email) {
		User u = userDao.findByEmail(email);

		if (u == null) {
			LOGGER.debug("No existe usuario con username " + email);
		} else {
			LOGGER.debug("Recuperado el usuario con email:" + email + " y con id " + u.getId());
		}
		return u;
	}

	@Override
	public Collection<User> findByIdVendedorconHistorial(long idvendedor, boolean isHistorial) {
		// TODO Auto-generated method stub
		Collection<User> users = userDao.findByIdVendedorconHistorial(idvendedor, isHistorial);
		if (users != null) {
			LOGGER.debug("Se han encontrado " + users.size() + " usuarios con historial con d  " + idvendedor);
			return users;
		} else {
			LOGGER.debug("No hay usuarioscon historial con d  " + idvendedor);
			return new HashSet<User>();
		}
	}
}
