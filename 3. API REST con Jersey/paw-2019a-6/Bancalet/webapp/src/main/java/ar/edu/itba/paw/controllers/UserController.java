package ar.edu.itba.paw.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.config.security.validators.RegisterFormValidator;
import ar.edu.itba.paw.config.security.validators.UserEditPassFormValidator;
import ar.edu.itba.paw.form.ContactAdminForm;
import ar.edu.itba.paw.form.UserEditForm;
import ar.edu.itba.paw.form.UserEditPassForm;
import ar.edu.itba.paw.form.UserLoginForm;
import ar.edu.itba.paw.form.UserRegisterForm;
import ar.edu.itba.paw.interfaces.services.AyudaService;
import ar.edu.itba.paw.interfaces.services.ContactoService;
import ar.edu.itba.paw.interfaces.services.HistorialService;
import ar.edu.itba.paw.interfaces.services.ItemService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Contacto;
import ar.edu.itba.paw.models.Historial;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService us;

	@Autowired
	private AyudaService ay;

	@Autowired
	private HistorialService hi;

	@Autowired
	private ContactoService co;

	@Autowired
	private ItemService is;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource messageSource;

	private RegisterFormValidator userValidator;
	private UserEditPassFormValidator userPassFormValidator;

	@Autowired
	private DaoAuthenticationProvider authProvider;

	public UserController() {
		this.userValidator = new RegisterFormValidator();
		this.userPassFormValidator = new UserEditPassFormValidator();
	}

	/*
	 * Pagina de Login
	 */
	@RequestMapping("/login")
	public ModelAndView login(@Valid @ModelAttribute("loginForm") final UserLoginForm form,
			final BindingResult errors) {
		return new ModelAndView("users/login");
	}

	@RequestMapping("/recuperar")
	public ModelAndView recuperar(@Valid @ModelAttribute("loginForm") final UserLoginForm form,
			final BindingResult errors) {
		if (errors.hasErrors()) {
			return login(form, errors);
		}
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = null;
		try {
			user = us.findUserSessionByUsername(principal);

		} catch (java.util.NoSuchElementException e) {
			return new ModelAndView("redirect:/index");
		}
		if (user == null) {
			return new ModelAndView("redirect:/index");
		}
		long idd = user.getUserId();
		return new ModelAndView("redirect:/users/" + idd);
	}

	private boolean autoLogin(String username, String password, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authProvider.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());
		return authentication.isAuthenticated();
	}

	/*
	 * Pagina de Registro
	 */
	@RequestMapping("/register")
	public ModelAndView register(@ModelAttribute("registerForm") final UserRegisterForm form) {
		return new ModelAndView("users/register");
	}

	@RequestMapping(value = "/create", method = { RequestMethod.POST })
	public ModelAndView create(HttpServletRequest request,
			@Valid @ModelAttribute("registerForm") final UserRegisterForm form, final BindingResult errors,
			final @RequestParam CommonsMultipartFile[] fileUpload) {
		/*
		 * Comprovar el formulario
		 */
		this.userValidator.validate(form, errors);
		if (errors.hasErrors()) {
			return register(form);
		}
		/*
		 * Recoger los datos
		 */
		Integer idTipo = null;
		String avatarTipo = "";
		try {
			avatarTipo = request.getParameter("avatar_tipo");
		} catch (Exception e) {
			avatarTipo = "0";
		}
		try {
			idTipo = Integer.valueOf(avatarTipo);
		} catch (NumberFormatException ex) {
			idTipo = 0;
		}
		/*
		 * Crear el usuario
		 */
		User user = us.createUser(form.getTelf(), form.getEmail(), form.getCity(), form.getCountry(), form.getCode(),
				form.getUsername(), passwordEncoder.encode(form.getPassword()), form.getDireccion(), idTipo, "",
				fileUpload, form.getLat(), form.getLon());
		/*
		 * Comprovar el login del usuario
		 */
		boolean login = false;
		try {
			login = autoLogin(form.getUsername(), form.getPassword(), request);
		} catch (Exception e) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("error.403.title", null, LocaleContextHolder.getLocale()))
					.addObject("title",
							messageSource.getMessage("error.403.body", null, LocaleContextHolder.getLocale()));
		}
		if (login) {
			return new ModelAndView("redirect:/users/" + user.getUserId());
		}
		return new ModelAndView("redirect:/users/login");
	}

	/*
	 * Pagina de perfil de usuario
	 */
	@RequestMapping("/{userId}")
	public ModelAndView index(@PathVariable("userId") final long id, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("users/index");
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);// User actual session
		// comprobar mensajes
		long sizeMsg = co.getUserContacts(user.getUserId());
		mav.addObject("listaSizeMsg", sizeMsg);
		// fin comprobar mensajes
		// comprobar norate
		long sizeRate = hi.getUserHistorialNoRate(user.getUserId());
		mav.addObject("listaSizeRate", sizeRate);
		// fin comprobar norate
		User userIndex = us.getUserById(id);

		if (userIndex != null) {
			long totalSales = us.getUserTotalSales(userIndex);
			mav.addObject("totalSales", totalSales);
			int rate = (int) userIndex.getRate();
			mav.addObject("rateo", rate);
			mav.addObject("seller", userIndex);

			if (us.getMaxVendor(userIndex)) {
				mav.addObject("maxSeller", 1);
			} else {
				mav.addObject("maxSeller", 0);
			}
			if (us.getMaxBuyer(userIndex)) {
				mav.addObject("maxBuyer", 1);
			} else {
				mav.addObject("maxBuyer", 0);
			}
			if (us.getMaxRated(userIndex)) {
				mav.addObject("maxRated", 1);
			} else {
				mav.addObject("maxRated", 0);
			}

			if (userIndex.getRole().equals(User.ADMIN)) {
				mav.addObject("user_index", userIndex);
				return mav;
			} else {
				if (userIndex.getUserId() != user.getUserId()) {
					response.setStatus(403);
					return (new ModelAndView("error"))
							.addObject("body",
									messageSource.getMessage("error.403.body", null, LocaleContextHolder.getLocale()))
							.addObject("title",
									messageSource.getMessage("error.403.title", null, LocaleContextHolder.getLocale()));
				} else {
					mav.addObject("user_index", userIndex);
					return mav;
				}
			}

		} else {
			response.setStatus(404);
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("user.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("user.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
	}

	@RequestMapping("/listUsersItems")
	public ModelAndView listUsersItems(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("users/listusersitemsnear");
		String rangeKmSlider = request.getParameter("rangeKmSlider");
		// Default range Km de largo ESP
		Integer rangeKm = rangeKmSlider == null ? 1287 : Integer.valueOf(rangeKmSlider);
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		Collection<User> listUser = us.findByUserNear(user.getLat(), user.getLon(), rangeKm);
		TreeMap<Long, List<Item>> treeMap = is.getMapfindByItemNear(user.getLat(), user.getLon(), rangeKm, listUser);
		// comprobar mensajes
		long sizeMsg = co.getUserContacts(user.getUserId());
		mav.addObject("listaSizeMsg", sizeMsg);
		// fin comprobar mensajes
		// comprobar norate
		long sizeRate = hi.getUserHistorialNoRate(user.getUserId());
		mav.addObject("listaSizeRate", sizeRate);
		// fin comprobar norate
		mav.addObject("user", user);
		mav.addObject("users", listUser);
		mav.addObject("treeMap", treeMap);
		mav.addObject("rangeKm", rangeKm == null ? 1187 : rangeKm);

		return mav;
	}

	@RequestMapping("/returnIndex")
	public ModelAndView returnIndex(HttpServletRequest request) {
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		return new ModelAndView("redirect:/users/" + user.getUserId());
	}

	/*
	 * Pagina de editar perfil de usuario
	 */
	@RequestMapping("/editUsr")
	public ModelAndView editUsr(@ModelAttribute("UserEditForm") final UserEditForm form) {
		ModelAndView mav = new ModelAndView("users/edit");
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/editAccept", method = { RequestMethod.POST })
	public ModelAndView editAccept(@Valid @ModelAttribute("UserEditForm") final UserEditForm form,
			final BindingResult errors, final @RequestParam CommonsMultipartFile[] fileUpload) {
		if (errors.hasErrors()) {
			return editUsr(form);
		}
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		us.updateUser(user, form.getTelf(), form.getCity(), form.getCountry(), form.getCode(), form.getDireccion(),
				form.getTipo(), "", form.getLat(), form.getLon(), fileUpload);
		return new ModelAndView("redirect:/users/" + user.getUserId());
	}

	/*
	 * Pagina de editar la pass de usuario
	 */
	@RequestMapping("/editPass")
	public ModelAndView editPass(@ModelAttribute("UserEditPassForm") final UserEditPassForm form) {
		return new ModelAndView("users/changePass");
	}

	@RequestMapping(value = "/editPassAccept", method = { RequestMethod.POST })
	public ModelAndView editPassAccept(@Valid @ModelAttribute("UserEditPassForm") final UserEditPassForm form,
			final BindingResult errors) {
		this.userPassFormValidator.validate(form, errors);
		if (errors.hasErrors()) {
			return editPass(form);
		}
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		us.updateUserPass(user, passwordEncoder.encode(form.getPassword()));
		return new ModelAndView("redirect:/users/" + user.getUserId());
	}

	/*
	 * Pagina del historial del usuario
	 */
	@RequestMapping("/misventas")
	public ModelAndView historialventas(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("historial/mysales");
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);
		// comprobar mensajes
		long sizeMsg = co.getUserContacts(user.getUserId());
		mav.addObject("listaSizeMsg", sizeMsg);
		// fin comprobar mensajes
		// comprobar norate
		long sizeRate = hi.getUserHistorialNoRate(user.getUserId());
		mav.addObject("listaSizeRate", sizeRate);
		// fin comprobar norate
		int size = 0;
		Collection<Item> itemsvendidos = is.findByVendedorId(user.getUserId(), Item.BAJA);
		Collection<Historial> listaventas = hi.findByidVendedor(user.getUserId());
		Collection<User> listcompradores = us.findByidVendedorconHistorial(user.getUserId());
		if (listaventas != null) {
			size = listaventas.size();
			mav.addObject("lista", listaventas);
		}
		if (itemsvendidos != null) {
			mav.addObject("items", itemsvendidos);
		}
		if (listcompradores != null) {
			mav.addObject("compradores", listcompradores);
		}
		mav.addObject("listaSize", size);
		return mav;
	}

	@RequestMapping("/miscompras")
	public ModelAndView historialCompras(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("historial/mypurchases");
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);
		// comprobar mensajes
		long sizeMsg = co.getUserContacts(user.getUserId());
		mav.addObject("listaSizeMsg", sizeMsg);
		// fin comprobar mensajes
		// comprobar norate
		long sizeRate = hi.getUserHistorialNoRate(user.getUserId());
		mav.addObject("listaSizeRate", sizeRate);
		// fin comprobar norate
		int size = 0;
		Collection<Historial> listacompras = hi.findByidComprador(user.getUserId());
		Collection<User> listvendedores = us.findByidCompradorconHistorial(user.getUserId());
		Collection<Item> itemscomprados = is.findByCompradorId(user.getUserId(), Item.BAJA);
		if (listacompras != null) {
			size = listacompras.size();
			mav.addObject("lista", listacompras);
		}
		if (itemscomprados != null) {
			mav.addObject("items", itemscomprados);
		}
		if (listvendedores != null) {
			mav.addObject("vendedores", listvendedores);
		}
		mav.addObject("listaSize", size);
		return mav;
	}

	@RequestMapping("/miscontactosr")
	public ModelAndView misContactosR(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("users/myreceivecontacts");
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);
		// comprobar mensajes
		long sizeMsg = co.getUserContacts(user.getUserId());
		mav.addObject("listaSizeMsg", sizeMsg);
		// fin comprobar mensajes
		// comprobar norate
		long sizeRate = hi.getUserHistorialNoRate(user.getUserId());
		mav.addObject("listaSizeRate", sizeRate);
		// fin comprobar norate
		co.readMensajes(user.getUserId());
		Collection<Contacto> contactos = co.findByidVendedor(user.getUserId());
		List<User> users = new ArrayList<User>();
		for (Contacto contact : contactos) {
			users.add(us.findById(contact.getIdComprador()));
		}
		Collection<User> listcontactados = users;
		Collection<Item> items = is.findByVendedorIdContactados(user.getUserId(), true);
		mav.addObject("items", items);
		int size = 0;
		if (contactos != null) {
			mav.addObject("users", listcontactados);
			mav.addObject("lista", contactos);
			size = contactos.size();
		}
		mav.addObject("listaSize", size);

		return mav;
	}

	@RequestMapping("/miscontactose")
	public ModelAndView misContactosE(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("users/mycontacts");
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);
		// comprobar mensajes
		long sizeMsg = co.getUserContacts(user.getUserId());
		mav.addObject("listaSizeMsg", sizeMsg);
		// fin comprobar mensajes
		// comprobar norate
		long sizeRate = hi.getUserHistorialNoRate(user.getUserId());
		mav.addObject("listaSizeRate", sizeRate);
		// fin comprobar norate
		Collection<Contacto> contactos = co.findByidComprador(user.getUserId());
		List<User> users = new ArrayList<User>();
		for (Contacto contact : contactos) {
			users.add(us.findById(contact.getIdVendedor()));
		}
		Collection<User> listcontactados = users;
		Collection<Item> items = is.findByCompradorIdContactados(user.getUserId(), true);
		mav.addObject("items", items);
		int size = 0;
		if (contactos != null) {
			mav.addObject("users", listcontactados);
			mav.addObject("lista", contactos);
			size = contactos.size();
		}
		mav.addObject("listaSize", size);

		return mav;

	}

	@RequestMapping("/help")
	public ModelAndView contact(@ModelAttribute("ContactAdminForm") final ContactAdminForm form,
			final BindingResult error) {
		ModelAndView mav = new ModelAndView("contact/helpUser");
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		// comprobar mensajes
		long sizeMsg = co.getUserContacts(user.getUserId());
		mav.addObject("listaSizeMsg", sizeMsg);
		// fin comprobar mensajes
		// comprobar norate
		long sizeRate = hi.getUserHistorialNoRate(user.getUserId());
		mav.addObject("listaSizeRate", sizeRate);
		// fin comprobar norate
		mav.addObject("user", user);
		mav.addObject("name", user.getUsername());
		mav.addObject("email", user.getEmail());
		return mav;
	}

	@RequestMapping("/contactSucces")
	public ModelAndView contactSucces(HttpServletRequest request,
			@Valid @ModelAttribute("ContactAdminForm") final ContactAdminForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return contact(form, errors);
		}
		ModelAndView mav = new ModelAndView("contact/contactSucces");
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);
		String name = user.getUsername();
		String email = user.getEmail();
		Validation val = ay.createAyuda(form.getSubject(), email, name, form.getMensaje());
		Object[] parameterS = { name, email };
		Object[] parameterM = { name };
		String codigo;
		int tipo = 0;
		if (val.isOk()) {
			codigo = messageSource.getMessage("admin.succes_send", parameterS, LocaleContextHolder.getLocale());
		} else {
			tipo = 1;
			codigo = messageSource.getMessage("admin.error_send", parameterM, LocaleContextHolder.getLocale());
		}
		mav.addObject("tipo", tipo);
		mav.addObject("mensaje", codigo);
		return mav;
	}

	@RequestMapping("/missugerencias")
	public ModelAndView misSugerencias(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("users/missugerencias");
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);
		// comprobar mensajes
		long sizeMsg = co.getUserContacts(user.getUserId());
		mav.addObject("listaSizeMsg", sizeMsg);
		// fin comprobar mensajes
		// comprobar norate
		long sizeRate = hi.getUserHistorialNoRate(user.getUserId());
		mav.addObject("listaSizeRate", sizeRate);
		// fin comprobar norate
		int size = 0;
		Collection<Item> items = us.getSugerencias(user.getUserId());
		if (items != null) {
			size = items.size();
		}
		mav.addObject("items", items);
		mav.addObject("total_items", size);

		return mav;
	}

}