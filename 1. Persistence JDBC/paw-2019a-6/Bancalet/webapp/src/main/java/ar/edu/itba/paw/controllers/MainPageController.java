package ar.edu.itba.paw.controllers;

import java.util.Calendar;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.form.ContactAdminForm;
import ar.edu.itba.paw.interfaces.services.AyudaService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ItemService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Ayuda;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

@Controller
@RequestMapping("/")
public class MainPageController {

	private final String ADMIN_MAIL = "bancalet.do.not.reply@gmail.com";
	private final String DO_NOT_REPLAY = "Do not reply this email.\n";
	private final String MENSAJE_HELP = "\nUser: ";
	private final String ASUNTO_HELP = "REPORT NUMBER ";

	@Autowired
	private UserService us;

	@Autowired
	private ItemService is;

	@Autowired
	private AyudaService ay;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private EmailService emailService;
	/*
	 * Pagina principal
	 */

	@RequestMapping("")
	public ModelAndView onLoginSuccess(HttpServletRequest request) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User user = null;
		try {
			user = us.findByUsername(username);
		} catch (java.util.NoSuchElementException e) {
			return new ModelAndView("redirect:/index");
		}

		if (user == null) {
			return new ModelAndView("redirect:/index");
		}
		if (user.getRole().equals("ADMIN")) {
			return new ModelAndView("redirect:/admin/");
		} else if (user.getRole().equals("USER")) {
			return new ModelAndView("redirect:/users/" + user.getId());
		} else
			return new ModelAndView("redirect:/index");

	}

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		final ModelAndView mav = new ModelAndView("inicio/index");

		return mav;
	}

	@RequestMapping("/contact")
	public ModelAndView contact(@ModelAttribute("ContactAdminForm") final ContactAdminForm form,
			final BindingResult error) {
		ModelAndView mav = new ModelAndView("contact/help");
		return mav;
	}

	@RequestMapping("/contactSucces")
	public ModelAndView contactSucces(HttpServletRequest request,
			@ModelAttribute("ContactAdminForm") final ContactAdminForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return contact(form, errors);
		}
		ModelAndView mav = new ModelAndView("contact/contactSucces");

		String subject = form.getSubject();
		String name = form.getName();
		String email = form.getEmail();
		String mensaje = form.getMensaje();

		Calendar calendar = Calendar.getInstance();
		java.sql.Date fecha_contacto = new java.sql.Date(calendar.getTime().getTime());

		User u = null;

		try {
			u = us.findByEmail(email);
		} catch (Exception e) {

		}

		long id = Ayuda.OUT;// si esta fuera de la app es -1

		if (u != null) {
			id = u.getId();
			name = u.getUsername();
		}
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

	@SuppressWarnings("unused")
	@RequestMapping("/list/{id}")
	public ModelAndView item(@PathVariable("id") long id, HttpServletResponse response, HttpServletRequest request) {

		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			Collection<? extends GrantedAuthority> auth = ((UserDetails) principal).getAuthorities();
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		// User user = us.findByUsername(username);

		Item item = null;
		try {
			item = is.findById(id);
		} catch (Exception e) {
			response.setStatus(404); // TODO use web.xml and ErrorController
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("table.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("table.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		if (item == null) {
			response.setStatus(404); // TODO use web.xml and ErrorController
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("table.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("table.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}

		User seller = us.findById(item.getIdvendedor());

		final ModelAndView mav = new ModelAndView("inicio/item");

		mav.addObject("seller", seller);
		mav.addObject("item", item);

		mav.addObject("numeroVisitas", item.getNumeroVisitas());
		return mav;
	}

	@RequestMapping("/list")
	public ModelAndView listNologin(HttpServletRequest request) {
		final ModelAndView mav = new ModelAndView("inicio/listNologin");
		String item_nombre = request.getParameter("item_nombre") != null ? request.getParameter("item_nombre") : "";
		String item_tipo = request.getParameter("item_tipo") != null ? request.getParameter("item_tipo") : "";

		item_nombre = item_nombre.toLowerCase();// pasar a minusculas, case sensitive

		Integer tipo = item_tipo != "" ? Integer.valueOf(item_tipo) : 0;

		// tipo entre 0 y 5
		if (tipo < 0) {
			tipo = 0;
		} else if (tipo > 5) {
			tipo = 5;
		}

		Collection<Item> items = is.findAllAlta();

		String slider = request.getParameter("slider");
		String min = null, max = "0";
		Double maximo = 0.0;
		Double minimo = 0.0;

		maximo = is.getMaxPrice();
		if (maximo != null)
			max = String.valueOf(maximo);
		mav.addObject("items", items);

		if (items.size() == 0) {

			mav.addObject("total_items", 0);
			mav.addObject("item_nombre", item_nombre);
			mav.addObject("tipo", item_tipo);
			mav.addObject("min", 0);
			mav.addObject("max", max);
			mav.addObject("maximo", maximo);

			return mav;
		} else {

			if (slider != null && slider.split("-").length > 0) {
				Pattern p = Pattern.compile("\\d+");
				Matcher m = p.matcher(slider);
				while (m.find()) {
					if (min == null)
						min = m.group();
					else
						max = m.group();
				}

				maximo = max != null ? Double.valueOf(max) : maximo;
				minimo = min != null ? Double.valueOf(min) : 0.0;
			}
			// solo test
			mav.addObject("item_nombre", item_nombre);
			mav.addObject("tipo", item_tipo);

			items = is.findByFilter(tipo, Item.ALTA, minimo, maximo, item_nombre);
			mav.addObject("items", items);
			mav.addObject("total_items", items.size());
			mav.addObject("min", min);
			mav.addObject("max", max);
			mav.addObject("maximo", maximo);
		}
		return mav;
	}
}