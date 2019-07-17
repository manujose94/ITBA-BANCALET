package ar.edu.itba.paw.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.form.ContactAdminForm;
import ar.edu.itba.paw.interfaces.services.AyudaService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.User;

@Controller
@RequestMapping("/")
public class MainPageController {

	@Autowired
	private UserService us;

	@Autowired
	private AyudaService ay;

	@Autowired
	private MessageSource messageSource;

	/*
	 * Pagina principal
	 */

	@RequestMapping("")
	public ModelAndView onLoginSuccess(HttpServletRequest request) {
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
		if (user.getRole().equals("ADMIN")) {
			return new ModelAndView("redirect:/admin/");
		} else if (user.getRole().equals("USER")) {
			return new ModelAndView("redirect:/users/" + user.getUserId());
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
			@Valid @ModelAttribute("ContactAdminForm") final ContactAdminForm form, final BindingResult errors) {

		if (errors.hasErrors()) {
			return contact(form, errors);
		}
		ModelAndView mav = new ModelAndView("contact/contactSucces");

		String subject = form.getSubject();
		String name = form.getName();
		String email = form.getEmail();
		String mensaje = form.getMensaje();

		Object[] parameterS = { name, email };
		Object[] parameterM = { name };

		Validation val = ay.createAyuda(subject, email, name, mensaje);

		int tipo = 0;
		String codigo;
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
}