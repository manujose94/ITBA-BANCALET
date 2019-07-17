package ar.edu.itba.paw.controllers;

import java.util.Calendar;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.config.security.RegisterFormValidator;
import ar.edu.itba.paw.config.security.UserEditPassFormValidator;
import ar.edu.itba.paw.form.ContactAdminForm;
import ar.edu.itba.paw.form.UserEditForm;
import ar.edu.itba.paw.form.UserEditPassForm;
import ar.edu.itba.paw.form.UserLoginForm;
import ar.edu.itba.paw.form.UserRegisterForm;
import ar.edu.itba.paw.interfaces.services.AyudaService;
import ar.edu.itba.paw.interfaces.services.ContactoService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.HistorialService;
import ar.edu.itba.paw.interfaces.services.ItemService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Ayuda;
import ar.edu.itba.paw.models.Contacto;
import ar.edu.itba.paw.models.Historial;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

@Controller
@RequestMapping("/users")
public class UserController {

	private final String ADMIN_MAIL = "bancalet.do.not.reply@gmail.com";
	private final String DO_NOT_REPLAY = "Do not reply this email.\n";
	private final String MENSAJE_HELP = "\nUser: ";
	private final String ASUNTO_HELP = "REPORT NUMBER ";

	@Autowired
	private EmailService emailService;

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

	public UserController() {
		this.userValidator = new RegisterFormValidator();
		this.userPassFormValidator = new UserEditPassFormValidator();
	}

	/*
	 * Pagina de Login
	 */
	@RequestMapping("/login")
	public ModelAndView login(@ModelAttribute("loginForm") final UserLoginForm form, final BindingResult errors) {
		return new ModelAndView("users/login");
	}

	@RequestMapping("/recuperar")
	public ModelAndView recuperar(@Valid @ModelAttribute("loginForm") final UserLoginForm form,
			final BindingResult errors) {
		if (errors.hasErrors()) {
			return login(form, errors);
		}
		User u = us.findByUsername(form.getUsername());

		long idd = u.getId();
		return new ModelAndView("redirect:/users/" + idd);
	}

	/*
	 * Pagina de Registro
	 */
	@RequestMapping("/register")
	public ModelAndView register(@ModelAttribute("registerForm") final UserRegisterForm form) {
		return new ModelAndView("users/register");
	}

	@RequestMapping(value = "/create", method = { RequestMethod.POST })
	public ModelAndView create(@Valid @ModelAttribute("registerForm") final UserRegisterForm form,
			final BindingResult errors) {
		this.userValidator.validate(form, errors);
		if (errors.hasErrors()) {
			return register(form);
		}
		try {
			us.create(form.getTelf(), form.getEmail(), form.getCity(), form.getCountry(), form.getCode(),
					form.getUsername(), passwordEncoder.encode(form.getPassword()), User.USER, form.getDireccion());

		} catch (Exception e) {
			// response.setStatus(403); // TODO use web.xml and ErrorController
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("error.409.title", null, LocaleContextHolder.getLocale()))
					.addObject("title",
							messageSource.getMessage("error.409.body", null, LocaleContextHolder.getLocale()));
		}
		return new ModelAndView("redirect:/users/login");
	}

	/*
	 * Pagina de perfil de usuario
	 */
	@RequestMapping("/{userId}")
	public ModelAndView index(@PathVariable("userId") final long id, HttpServletResponse response) {
		final ModelAndView mav = new ModelAndView("users/index");
		User user = us.findById(id);
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User user2 = us.findByUsername(username);

		if (user != null) {
			if (user2.getRole().equals(User.ADMIN)) {
				mav.addObject("user", user);
				return mav;
			} else {
				if (user.getId() != user2.getId()) {
					response.setStatus(403); // TODO use web.xml and ErrorController
					return (new ModelAndView("error"))
							.addObject("body",
									messageSource.getMessage("error.403.title", null, LocaleContextHolder.getLocale()))
							.addObject("title",
									messageSource.getMessage("error.403.body", null, LocaleContextHolder.getLocale()));
				} else {
					mav.addObject("user", user);
					return mav;
				}
			}

		} else {
			response.setStatus(404); // TODO use web.xml and ErrorController
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("user.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("user.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
	}

	@RequestMapping("/returnIndex")
	public ModelAndView returnIndex(HttpServletRequest request) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User user = us.findByUsername(username);
		return new ModelAndView("redirect:/users/" + user.getId());
	}

	/*
	 * Pagina de editar perfil de usuario
	 */
	@RequestMapping("/editUsr")
	public ModelAndView editUsr(@ModelAttribute("UserEditForm") final UserEditForm form) {
		ModelAndView mav = new ModelAndView("users/edit");
		// mav.addObject("",)
		return mav;
	}

	@RequestMapping(value = "/editAccept", method = { RequestMethod.POST })
	public ModelAndView editAccept(@Valid @ModelAttribute("UserEditForm") final UserEditForm form,
			final BindingResult errors) {
		// this.userPassFormValidator.validate(form, errors);
		if (errors.hasErrors()) {
			return editUsr(form);
		}

		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User user = us.findByUsername(username);
		if (form.getCity() != null) {
			user.setCity(form.getCity());
		}
		if (form.getDireccion() != null) {
			user.setDireccion(form.getDireccion());
		}
		if (form.getCode() != null) {
			user.setCode(form.getCode());
		}
		if (form.getCountry() != null) {
			user.setCountry(form.getCountry());
		}
		if (form.getTelf() != null) {
			user.setTelf(form.getTelf());
		}
		us.update(user);

		return new ModelAndView("redirect:/users/" + user.getId());
	}

	/*
	 * Pagina de editar la pass de usuario
	 */
	@RequestMapping("/editPass")
	public ModelAndView editPass(@ModelAttribute("UserEditPassForm") final UserEditPassForm form) {
		return new ModelAndView("users/changePass");
	}

	@RequestMapping(value = "/editPassAccept", method = { RequestMethod.POST })
	public ModelAndView editAccept(@Valid @ModelAttribute("UserEditPassForm") final UserEditPassForm form,
			final BindingResult errors) {
		this.userPassFormValidator.validate(form, errors);
		if (errors.hasErrors()) {
			return editPass(form);
		}

		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User user = us.findByUsername(username);
		if (form.getPassword() != null) {
			user.setPassword(passwordEncoder.encode(form.getPassword()));
		}

		us.update(user);

		return new ModelAndView("redirect:/users/" + user.getId());
	}

	/*
	 * Pagina del historial del usuario
	 */
	@RequestMapping("/misventas")
	public ModelAndView historialventas(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("historial/mysales");

		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails)
			username = ((UserDetails) principal).getUsername();
		else
			username = principal.toString();

		// vendedor usuario logeado
		User vendedor = us.findByUsername(username);
		Collection<Item> itemsvendidos = is.findByVendedorIdHistorial(vendedor.getId(), true);
		Collection<Historial> listaventas = hi.findByidvendedor(vendedor.getId());
		Collection<User> listcompradores = us.findByIdVendedorconHistorial(vendedor.getId(), true);

		mav.addObject("compradores", listcompradores);
		mav.addObject("items", itemsvendidos);
		mav.addObject("user", vendedor);
		mav.addObject("lista", listaventas);
		mav.addObject("listaSize", listaventas.size());
		mav.addObject("compraOventa", 1);

		return mav;
	}

	@RequestMapping("/miscompras")
	public ModelAndView historialCompras(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("historial/historicList");

		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User user = us.findByUsername(username);

		Collection<Historial> listacompras = hi.findByidcomprador(user.getId());

		mav.addObject("user", user);
		mav.addObject("lista", listacompras);
		mav.addObject("listaSize", listacompras.size());
		mav.addObject("compraOventa", 0);

		return mav;
	}

	@RequestMapping("/miscontactosr")
	public ModelAndView misContactosR(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("users/myreceivecontacts");

		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User user = us.findByUsername(username);
		Collection<Contacto> contactos = co.findByIdVendedor(user.getId());
		Collection<Item> items = is.findByVendedorIdContactados(user.getId(), true);

		mav.addObject("items", items);
		mav.addObject("user", user);
		mav.addObject("lista", contactos);
		mav.addObject("listaSize", contactos.size());

		return mav;
	}

	@RequestMapping("/miscontactose")
	public ModelAndView misContactosE(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("users/mycontacts");

		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User user = us.findByUsername(username);

		Collection<Contacto> contactos = co.findByidcomprador(user.getId());

		mav.addObject("user", user);
		mav.addObject("lista", contactos);
		mav.addObject("listaSize", contactos.size());

		return mav;
	}

	@RequestMapping("/help")
	public ModelAndView contact(@ModelAttribute("ContactAdminForm") final ContactAdminForm form,
			final BindingResult error) {
		ModelAndView mav = new ModelAndView("contact/helpUser");
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User user = us.findByUsername(username);
		mav.addObject("name", user.getUsername());
		mav.addObject("email", user.getEmail());
		return mav;
	}

	@RequestMapping("/contactSucces")
	public ModelAndView contactSucces(HttpServletRequest request,
			@ModelAttribute("ContactAdminForm") final ContactAdminForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return contact(form, errors);
		}
		ModelAndView mav = new ModelAndView("contact/contactSucces");
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User u = us.findByUsername(username);

		String name = u.getUsername();
		String email = u.getEmail();
		String subject = form.getSubject();
		String mensaje = form.getMensaje();

		Calendar calendar = Calendar.getInstance();
		java.sql.Date fecha_contacto = new java.sql.Date(calendar.getTime().getTime());

		long id = u.getId();

		Ayuda ayuda = ay.create(id, subject, name, email, fecha_contacto, mensaje, Ayuda.ALTA, null, "");

		String codigo;

		String asunto = ASUNTO_HELP + ayuda.getIdayuda() + ": " + subject;
		String mensajeAdmin = form.getMensaje() + MENSAJE_HELP + name + ", " + email;
		String mensajeUser = DO_NOT_REPLAY + mensajeAdmin;

		Validation val = emailService.sendSimpleEmail(ADMIN_MAIL, asunto, mensajeAdmin);
		emailService.sendSimpleEmail(email, asunto, mensajeUser);

		int tipo = 0;
		if (val.isOk()) {

			codigo = "" + name + ", tu mensaje se ha enviado correctamente, recibirás tu respuesta a: " + email;

		} else {
			tipo = 1;
			codigo = "Ha habido un error en el envio del mensaje. ERROR: " + val.getMessage();
		}

		mav.addObject("tipo", tipo);
		mav.addObject("mensaje", codigo);
		return mav;
	}

}