package ar.edu.itba.paw.service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ar.edu.itba.paw.interfaces.services.AdminService;
import ar.edu.itba.paw.interfaces.services.AyudaService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ItemService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Ayuda;
import ar.edu.itba.paw.models.User;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private ItemService is;

	@Autowired
	private UserService us;

	@Autowired
	private AyudaService ay;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Transactional
	@Override
	public User createUser(String telf, String email, String city, String country, String code, String username,
			String password, String role, String direccion, int numImg, String urlImg,
			CommonsMultipartFile[] fileUpload, double lat, double lon) {
		/*
		 * Imagenes
		 */
		byte[] image = null;
		if (fileUpload != null && fileUpload.length > 0) {
			for (CommonsMultipartFile aFile : fileUpload) {
				image = aFile.getBytes();
			}
		}

		User user = us.create(telf, email, city, country, code, username, password, role, direccion, numImg, urlImg,
				image, User.INITIAL_RATE, lat, lon, User.ALTA);
		emailService.registerUserMail(user);
		return user;

	}

	@Override
	public Collection<User> findUsers(String name, Integer tipoEstado, String role) {
		Collection<User> list = null;

		if (role.equals("")) {
			if (name.equals("")) {
				if (tipoEstado == 2) {// todos
					list = us.findAll();
				} else {// alta o baja
					list = us.findByEstado(tipoEstado);
				}

			} else {
				if (tipoEstado == 2) {// todos
					list = us.filterByName(name);
				} else {// alta o baja
					list = us.findByNameEstado(name, tipoEstado);
				}
			}
		} else {
			if (name.equals("")) {
				if (role.equals(User.ADMIN)) {
					if (tipoEstado == 2) {// todos
						list = us.findByuserRole(User.ADMIN);
					} else {// alta o baja
						list = us.findByuserRoleEstado(User.ADMIN, tipoEstado);
					}
				} else {
					if (tipoEstado == 2) {// todos
						list = us.findByuserRole(User.USER);
					} else {// alta o baja
						list = us.findByuserRoleEstado(User.USER, tipoEstado);
					}
				}
			} else {
				if (tipoEstado == 2) {// todos
					list = us.findByFilter(name, role);
				} else {// alta o baja
					list = us.findByFilterEstado(name, role, tipoEstado);
				}
			}
		}
		if (list != null) {
			for (User us : list) {
				byte imagen[] = us.getImage();
				String base64Encoded = null;
				try {
					base64Encoded = us != null ? new String(Base64.encodeBase64(imagen), "UTF-8") : null;
				} catch (UnsupportedEncodingException e) {
					LOGGER.debug("[AdminController] userList: Not convert imagen User: " + us.getUsername()
							+ " in base64Encoded");
				}
				us.setUrlImg(base64Encoded);
			}
		}
		return list;
	}

	@Transactional
	@Override
	public User bajaUser(long userId) {
		User userIndex = us.findById(userId);
		userIndex.setEstado(User.BAJA);
		us.update(userIndex);
		is.bajaAdmin(userIndex.getUserId());
		emailService.bajaUserMail(userIndex);
		return userIndex;
	}

	@Transactional
	@Override
	public User altaUser(long userId) {
		User userIndex = us.findById(userId);
		userIndex.setEstado(User.ALTA);
		us.update(userIndex);
		is.altaAdmin(userIndex.getUserId());
		emailService.altaUserMail(userIndex);
		return userIndex;
	}

	@Override
	public User getUserById(long userId) {
		User user = us.findById(userId);
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
					LOGGER.debug("[AdminController] userList: Not convert imagen User: " + user.getUsername()
							+ " in base64Encoded");
				}
			}
		}
		return user;
	}

	@Transactional
	@Override
	public void updateUser(long userId, String telf, String city, String country, String code, String direccion,
			int numImg, String urlImg, CommonsMultipartFile[] fileUpload, double lat, double lon) {
		User user = getUserById(userId);
		if (user != null) {
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
			user.setImage(imagen);
			user.setTelf(telf);
			user.setCity(city);
			user.setCountry(country);
			user.setCode(code);
			user.setDireccion(direccion);
			user.setNumImg(numImg);
			user.setUrlImg(urlImg);
			user.setLat(lat);
			user.setLon(lon);
			us.update(user);
		}
	}

	@Transactional
	@Override
	public User updateUserPass(User userIndex, String password, boolean equals) {
		emailService.changePassMail(equals, userIndex, password);
		userIndex.setPassword(passwordEncoder.encode(password));
		us.update(userIndex);
		return userIndex;
	}

	@Override
	public Collection<Ayuda> findAyudas(String asunto, int tipo, int estado) {
		Collection<Ayuda> list = null;
		if (asunto.equals("")) {
			if (tipo == 2) {
				list = ay.findByOutsideEstado(estado);
			} else if (tipo == 1) {
				list = ay.findByInsideEstado(estado);
			} else {// todos los tipos
				list = ay.findByEstado(estado);
			}
		} else {
			if (tipo == 2) {
				list = ay.findByOutsideEstado(estado, asunto);
			} else if (tipo == 1) {
				list = ay.findByInsideEstado(estado, asunto);
			} else {// todos los tipos
				list = ay.findByAsunto(asunto);
			}
		}
		return list;
	}

	@Transactional
	@Override
	public Validation archiveIssue(Ayuda issue, String informe) {
		Calendar calendar = Calendar.getInstance();
		java.sql.Date fechaResolucion = new java.sql.Date(calendar.getTime().getTime());
		issue.setEstado(Ayuda.BAJA);
		issue.setFechaResolucion(fechaResolucion);
		issue.setInforme(informe);
		ay.update(issue);
		Validation val = emailService.archiveIssueMail(issue, informe);

		return val;
	}

	@Transactional
	@Override
	public void deleteItem(long itemId) {
		emailService.adminDeleteItemMail(itemId);
		is.delete(itemId);
	}

}
