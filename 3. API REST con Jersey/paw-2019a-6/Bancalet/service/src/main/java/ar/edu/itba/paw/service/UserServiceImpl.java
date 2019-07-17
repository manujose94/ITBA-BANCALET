package ar.edu.itba.paw.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.HistorialService;
import ar.edu.itba.paw.interfaces.services.ItemService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Historial;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private HistorialService hi;

	@Autowired
	private ItemService is;

	@Override
	public User findById(long userId) {
		User user = userDao.findById(userId);

		if (user == null) {
			LOGGER.debug("No existe usuario con ese id " + userId);
		} else {
			LOGGER.debug("Recuperado el usuario " + user.getUsername() + " con id " + userId);
		}
		return user;
	}

	@Override
	public User findByUsername(String username) {
		User user = userDao.findByUsername(username);

		if (user == null) {
			LOGGER.debug("No existe usuario con username " + username);
		} else {
			LOGGER.debug("Recuperado el usuario " + username + " con id " + user.getUserId());
		}
		return user;
	}

	@Override
	public Collection<User> findAll() {
		Collection<User> users = userDao.findAll();
		if (users != null) {
			LOGGER.debug("Se han encontrado " + users.size() + " usuarios y se devuelve la lista.");
		} else {
			LOGGER.debug("No hay usuarios");
		}
		return users;
	}

	@Override
	public long getTotalUsers() {
		long total = userDao.getTotalUsers();
		if (total > 0) {
			LOGGER.debug("Se han encontrado " + total + " usuarios.");
		} else {
			LOGGER.debug("No se han encontrado usuarios.");
		}
		return total;
	}

	@Override
	@Transactional
	public int update(User user) {
		int result = userDao.update(user);
		String succes = result > 0 ? "[EXITO]" : "[SIN EXITO]";
		LOGGER.debug(succes + ": actualizar user con id =" + user.getUserId());
		return result;
	}

	@Override
	@Transactional
	public User create(String telf, String email, String city, String country, String code, String username,
			String password, String role, String direccion, int numImg, String urlImg, byte[] image, double rate,
			double lat, double lon, int estado) {
		User user = userDao.create(telf, email, city, country, code, username, password, role, direccion, numImg,
				urlImg, image, rate, lat, lon, estado);
		LOGGER.debug("Se crea el usuario " + user.getUsername() + " con id " + user.getUserId() + " con el rol "
				+ user.getRole());
		return user;
	}

	@Override
	@Transactional
	public User create(String telf, String email, String city, String country, String code, String username,
			String password, String role, String direccion, double rate, double lat, double lon, int estado) {
		User user = userDao.create(telf, email, city, country, code, username, password, role, direccion, rate, lat,
				lon, estado);
		LOGGER.debug("Se crea el usuario " + user.getUsername() + " con id " + user.getUserId() + " con el rol "
				+ user.getRole());
		return user;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public User createAdmin(String username, String password) {
		User user = userDao.createAdmin(username, password);
		LOGGER.debug("Se crea el usuario " + user.getUsername() + " con id " + user.getUserId() + " con el rol "
				+ user.getRole());
		return user;
	}

	@Override
	public Collection<User> findByuserRole(String role) {
		Collection<User> users = userDao.findByuserRole(role);
		if (users != null) {
			LOGGER.debug(
					"Se han encontrado " + users.size() + " usuarios con role " + role + " y se devuelve la lista.");
		} else {
			LOGGER.debug("No hay usuarios con role " + role);
		}
		return users;
	}

	@Override
	public Collection<User> filterByName(String name) {
		Collection<User> users = userDao.filterByName(name);
		if (users != null) {
			LOGGER.debug("Se han encontrado " + users.size() + " usuarios con username similar a " + name
					+ " y se devuelve la lista.");
		} else {
			LOGGER.debug("No hay usuarios con username similar a " + name);
		}
		return users;
	}

	@Override
	public Collection<User> findByFilter(String name, String role) {
		Collection<User> users = userDao.findByFilter(name, role);
		if (users != null) {
			LOGGER.debug("Se han encontrado " + users.size() + " usuarios con role " + role + " y username similar a "
					+ name + " y se devuelve la lista.");
		} else {
			LOGGER.debug("No hay usuarios con role " + role + " y username similar a " + name);
		}
		return users;
	}

	@Override
	public Collection<User> findAllUserContactsItem(long itemId) {
		Collection<User> users = userDao.findAllUserContactsItem(itemId);
		if (users != null) {
			LOGGER.debug("Se han encontrado " + users.size() + " usuarios con itemId " + itemId);
		} else {
			LOGGER.debug("No hay usuarios con itemId " + itemId);
		}
		return users;
	}

	@Override
	public User findByEmail(String email) {
		User u = userDao.findByEmail(email);
		if (u == null) {
			LOGGER.debug("No existe usuario con email " + email);
		} else {
			LOGGER.debug("Recuperado el usuario con email:" + email + " y con id " + u.getUserId());
		}
		return u;
	}

	@Override
	public Collection<User> findByidVendedorconHistorial(long idVendedor) {
		Collection<User> users = userDao.findByidVendedorconHistorial(idVendedor);
		if (users != null) {
			LOGGER.debug("Se han encontrado " + users.size() + " usuarios con historial con idVendedor  " + idVendedor);
		} else {
			LOGGER.debug("No hay usuarios con historial con idVendedor  " + idVendedor);
		}
		return users;
	}

	@Override
	public Collection<User> findByidCompradorconHistorial(long idComprador) {
		Collection<User> users = userDao.findByidCompradorconHistorial(idComprador);
		if (users != null) {
			LOGGER.debug(
					"Se han encontrado " + users.size() + " usuarios con historial con idComprador  " + idComprador);
		} else {
			LOGGER.debug("No hay usuarios con historial con idComprador  " + idComprador);
		}
		return users;
	}

	@Override
	public User findUserSessionByUsername(Object principal) {
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		if (username == null)
			return null;

		User u = userDao.findByUsername(username);

		if (u == null) {
			LOGGER.debug("No existe usuario con username " + username);
		} else {
			u.setUrlImg(getImageUsertoBase64encoded(u.getImage()));
			LOGGER.debug("Recuperado el usuario " + username + " con id " + u.getUserId());
		}
		return u;
	}

	@Override
	public String getImageUsertoBase64encoded(byte[] imagen) {
		/*
		 * Imagen
		 */

		String base64Encoded = null;
		try {
			if (imagen != null)
				base64Encoded = new String(Base64.encodeBase64(imagen), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.debug("[UserService] userList: Not convert imagen  getImageUsertoBase64encoded in base64Encoded");
		}
		return base64Encoded;
	}

	@Override
	public Collection<User> findByEstado(int estado) {
		Collection<User> users = userDao.findByEstado(estado);
		if (users != null) {
			LOGGER.debug("Se han encontrado " + users.size() + " usuarios con estado  " + estado);
		} else {
			LOGGER.debug("No hay usuarios con estado  " + estado);
		}
		return users;
	}

	@Override
	public Collection<User> findByNameEstado(String name, int estado) {
		Collection<User> users = userDao.findByNameEstado(name, estado);
		if (users != null) {
			LOGGER.debug("Se han encontrado " + users.size() + " usuarios con name " + name + " con estado " + estado);
		} else {
			LOGGER.debug("No hay usuarios con name " + name + " con estado " + estado);
		}
		return users;
	}

	@Override
	public Collection<User> findByuserRoleEstado(String role, int estado) {
		Collection<User> users = userDao.findByuserRoleEstado(role, estado);
		if (users != null) {
			LOGGER.debug("Se han encontrado " + users.size() + " usuarios con role " + role + " con estado " + estado);
		} else {
			LOGGER.debug("No hay usuarios con role " + role + " con estado " + estado);
		}
		return users;
	}

	@Override
	public Collection<User> findByFilterEstado(String name, String role, int estado) {
		Collection<User> users = userDao.findByFilterEstado(name, role, estado);
		if (users != null) {
			LOGGER.debug("Se han encontrado " + users.size() + " usuarios con role " + role + " con estado " + estado
					+ " con name " + name);
		} else {
			LOGGER.debug("No hay usuarios con role " + role + " con estado " + estado + " con name " + name);
		}
		return users;
	}

	@Transactional
	@Override
	public User createUser(String telf, String email, String city, String country, String code, String username,
			String password, String direccion, int numImg, String urlImg, CommonsMultipartFile[] fileUpload, double lat,
			double lon) {
		byte[] image = null;
		if (fileUpload != null && fileUpload.length > 0) {
			for (CommonsMultipartFile aFile : fileUpload) {
				image = aFile.getBytes();
			}
		}

		User userIndex = create(telf, email, city, country, code, username, password, User.USER, direccion, numImg,
				urlImg, image, User.INITIAL_RATE, lat, lon, User.BAJA);
		emailService.registerUserMail(userIndex);
		return userIndex;
	}

	@Override
	public User getUserById(long userId) {
		User user = findById(userId);
		if (user != null) {
			/*
			 * Imagen
			 */
			byte imagen[] = user.getImage();
			String base64Encoded = null;
			if (imagen != null) {
				try {
					base64Encoded = new String(Base64.encodeBase64(imagen), "UTF-8");
					user.setUrlImg(base64Encoded);
				} catch (UnsupportedEncodingException e) {
					LOGGER.debug("[UserService] userList: Not convert imagen User: " + user.getUsername()
							+ " in base64Encoded");
				}
			}
		}
		return user;
	}

	@Transactional
	@Override
	public User updateUser(User user, String telf, String city, String country, String code, String direccion,
			int numImg, String urlImg, double lat, double lon, CommonsMultipartFile[] fileUpload) {
		/*
		 * Imagenes
		 */
		byte[] imagen = null;
		if (fileUpload != null && fileUpload.length > 0) {
			for (CommonsMultipartFile aFile : fileUpload) {
				imagen = aFile.getBytes();
			}
		}
		/*
		 * Avatar
		 */

		if (numImg < 0) {
			numImg = 0;
		} else if (numImg > 6) {
			numImg = 6;
		}
		user.setUrlImg("");
		user.setNumImg(numImg);
		user.setLat(lat);
		user.setLon(lon);
		user.setCity(city);
		user.setDireccion(direccion);
		user.setCode(code);
		user.setCountry(country);
		user.setTelf(telf);
		user.setImage(imagen);
		update(user);
		return user;
	}

	@Transactional
	@Override
	public User updateUserPass(User userIndex, String password) {
		userIndex.setPassword(password);
		update(userIndex);
		emailService.changePassMail(true, userIndex, password);
		return userIndex;
	}

	@Override
	public long getUserTotalSales(User user) {
		Collection<Item> items = is.findByVendedorId(user.getUserId(), Item.BAJA);
		long sales = items.size();
		return sales;
	}

	@Override
	public Collection<Item> getSugerencias(long userId) {
		// sacamos el pronostico de cual es el item que más compra el usuario
		Collection<Historial> itemsUser = hi.findByidComprador(userId);
		Collection<Item> itemsHistorialCompraprador = new ArrayList<Item>();
		for (Historial historial : itemsUser) {
			itemsHistorialCompraprador
					.addAll(is.findByTipoEstado(is.findById(historial.getItemId()).getTipo(), Item.ALTA));
		}
		int tipoMasRepetido = -1;
		if (!itemsHistorialCompraprador.isEmpty()) {
			tipoMasRepetido = mayorRepetido(itemsHistorialCompraprador.toArray());
		}
		// final, ya tenemos el pronostico
		Collection<Item> items = null;
		if (tipoMasRepetido != -1) {
			items = is.findByRecomendacion(tipoMasRepetido, Item.ALTA, userId);
		}
		if (items != null) {
			for (Item item : items) {
				byte imagen[] = item.getImage();
				if (imagen != null) {
					String base64Encoded = null;
					try {
						base64Encoded = new String(Base64.encodeBase64(imagen), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						base64Encoded = "";
					}
					item.setUrlImg(base64Encoded);
				}
			}
		}
		return items;
	}

	private int mayorRepetido(Object[] objects) {
		int mayor = ((Item) objects[0]).getTipo();
		for (int i = 1; i < objects.length; i++) {
			if (repetidos(mayor, objects) < repetidos(((Item) objects[i]).getTipo(), objects))
				mayor = ((Item) objects[i]).getTipo();
		}
		return mayor;
	}

	private int repetidos(int n, Object[] arr) {
		int cuantos = 0;// contador, neutro del +
		for (int i = 0; i < arr.length; i++) {
			if (n == ((Item) arr[i]).getTipo()) // si n es igual al elemento i
				cuantos++; // el contador icrementa en 1.
		}
		return cuantos;// devuelve al contador.
	}

	@Override
	public boolean getMaxVendor(User user) {
		Collection<User> users = userDao.getMaxVendor();
		boolean isMax = false;
		if (users != null) {
			for (User u : users) {
				if (user.getUserId() == u.getUserId()) {
					isMax = true;
				}
			}
		}
		return isMax;
	}

	@Override
	public boolean getMaxBuyer(User user) {
		Collection<User> users = userDao.getMaxBuyer();
		boolean isMax = false;
		if (users != null) {
			for (User u : users) {
				if (user.getUserId() == u.getUserId()) {
					isMax = true;
				}
			}
		}
		return isMax;
	}

	@Override
	public boolean getMaxRated(User user) {
		Collection<User> users = userDao.getMaxRated();
		boolean isMax = false;
		if (users != null) {
			for (User u : users) {
				if (user.getUserId() == u.getUserId()) {
					isMax = true;
				}
			}
		}
		return isMax;
	}

	@Override
	public Collection<User> findByUserNear(double lat, double lon, int rangeKm) {
		Collection<User> users = userDao.findByUserNear(lat, lon, rangeKm);

		if (users == null) {
			LOGGER.debug("0 user");
		} else {
			for (User user : users) {
				/*
				 * Imagen
				 */
				byte imagen[] = user.getImage();
				String base64Encoded = null;
				if (imagen != null) {
					try {
						base64Encoded = new String(Base64.encodeBase64(imagen), "UTF-8");
						user.setUrlImg(base64Encoded);
					} catch (UnsupportedEncodingException e) {
						LOGGER.debug("[UserService] userList: Not convert imagen User: " + user.getUsername()
								+ " in base64Encoded");
					}
				}
			}
			LOGGER.debug("users cercanos");
		}
		return users;
	}

	@Transactional
	@Override
	public Validation confirmEmail(User user) {
		user.setEstado(User.ALTA);
		update(user);
		return emailService.confirmUserMail(user);
	}
}
