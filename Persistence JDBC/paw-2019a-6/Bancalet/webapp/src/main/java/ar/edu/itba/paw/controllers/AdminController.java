package ar.edu.itba.paw.controllers;

import java.util.ArrayList;
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

import ar.edu.itba.paw.config.security.AdminRegisterFormValidator;
import ar.edu.itba.paw.config.security.UserEditPassFormValidator;
import ar.edu.itba.paw.form.AdminRegisterForm;
import ar.edu.itba.paw.form.AdminUserEditForm;
import ar.edu.itba.paw.form.UserEditPassForm;
import ar.edu.itba.paw.interfaces.services.AyudaService;
import ar.edu.itba.paw.interfaces.services.HistorialService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Ayuda;
import ar.edu.itba.paw.models.Historial;
import ar.edu.itba.paw.models.User;

@RequestMapping("/admin")
@Controller
public class AdminController {

	@Autowired
	private UserService us;

	@Autowired
	private HistorialService hi;

	@Autowired
	private AyudaService ay;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource messageSource;

	private UserEditPassFormValidator userPassFormValidator;
	private AdminRegisterFormValidator adminRegisterFormValidator;
	// private AdminEditFormValidator adminEditFormValidator;

	public AdminController() {
		this.userPassFormValidator = new UserEditPassFormValidator();
		this.adminRegisterFormValidator = new AdminRegisterFormValidator();
		// this.adminEditFormValidator = new AdminEditFormValidator();
	}

	@RequestMapping("")
	public ModelAndView index() {
		final ModelAndView mav = new ModelAndView("admin/dashboard");
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User u = us.findByUsername(username);
		mav.addObject("admin", u);
		return mav;
	}

	@RequestMapping("/registUser")
	public ModelAndView registUser(@ModelAttribute("AdminRegisterForm") final AdminRegisterForm form) {
		final ModelAndView mav = new ModelAndView("admin/registUser");

		ArrayList<String> roleList = new ArrayList<String>();
		roleList.add(User.ADMIN);
		roleList.add(User.USER);

		mav.addObject("roleList", roleList);
		return mav;
	}

	@RequestMapping(value = "/createUser", method = { RequestMethod.POST })
	public ModelAndView createUser(@Valid @ModelAttribute("AdminRegisterForm") final AdminRegisterForm form,
			final BindingResult errors) {
		this.adminRegisterFormValidator.validate(form, errors);
		if (errors.hasErrors()) {
			return registUser(form);
		}
		try {
			us.create(form.getTelf(), form.getEmail(), form.getCity(), form.getCountry(), form.getCode(),
					form.getUsername(), passwordEncoder.encode(form.getPassword()), form.getRole(),
					form.getDireccion());
		} catch (Exception e) {
			// response.setStatus(403); // TODO use web.xml and ErrorController
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("error.409.title", null, LocaleContextHolder.getLocale()))
					.addObject("title",
							messageSource.getMessage("error.409.body", null, LocaleContextHolder.getLocale()));
		}
		return new ModelAndView("redirect:/admin/userList");
	}

	@RequestMapping("/userList")
	public ModelAndView userList(HttpServletRequest request) {
		final ModelAndView mav = new ModelAndView("admin/userList");
		String tipoS = request.getParameter("user_roles") != null ? request.getParameter("user_roles") : "";
		String name = request.getParameter("item_nombre") != null ? request.getParameter("item_nombre") : "";

		Integer tipo = tipoS != "" ? Integer.valueOf(tipoS) : 0;

		Collection<User> list = null;
		list = us.findAll();
		String role = "";
		switch (tipo) {
		case 0:
			role = "";
			break;
		case 1:
			role = User.ADMIN;
			break;
		case 2:
			role = User.USER;
			break;
		}

		if (role.equals("")) {
			if (name.equals("")) {
				list = us.findAll();
			} else {
				list = us.findByName(name);
			}
		} else {
			if (name.equals("")) {
				if (role.equals(User.ADMIN)) {
					list = us.findByuserRole(User.ADMIN);
				} else {
					list = us.findByuserRole(User.USER);
				}
			} else {
				list = us.findByFilter(name, role);
			}
		}
		if (list.isEmpty()) {
			mav.addObject("user_roles", tipo);
			mav.addObject("item_nombre", name);
			mav.addObject("tipo", list);
			mav.addObject("total_users", list.size());
			return mav;
		}
		mav.addObject("user_roles", tipo);
		mav.addObject("item_nombre", name);
		mav.addObject("users", list);
		mav.addObject("total_users", list.size());
		return mav;
	}

	/*
	 * Pagina de perfil de usuario
	 */
	@RequestMapping("/{userId}")
	public ModelAndView index(@PathVariable("userId") final long id, HttpServletResponse response) {
		final ModelAndView mav = new ModelAndView("users/index");
		User user = us.findById(id);
		if (user != null) {
			mav.addObject("user", user);
			return mav;

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

	/*
	 * Pagina de editar perfil de usuario
	 */
	@RequestMapping("/editUsr/{userId}")
	public ModelAndView editUsr(@PathVariable("userId") final long id,
			@ModelAttribute("AdminUserEditForm") final AdminUserEditForm form, final BindingResult error) {
		ModelAndView mav = new ModelAndView("users/edit");
		mav.addObject("userId", id);
		return mav;
	}

	@RequestMapping(value = "/editAccept/{userId}", method = { RequestMethod.POST })
	public ModelAndView editAccept(@PathVariable("userId") final long id,
			@Valid @ModelAttribute("AdminUserEditForm") final AdminUserEditForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return editUsr(id, form, errors);
		}

		User user = us.findById(id);

		if (form.getCity() != null) {
			user.setCity(form.getCity());
		}
		if (form.getEmail() != null) {
			user.setEmail(form.getEmail());
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

		return new ModelAndView("redirect:/admin/" + id);
	}

	/*
	 * Pagina de editar la pass de usuario
	 */
	@RequestMapping("/editPass/{userId}")
	public ModelAndView editPass(@PathVariable("userId") final long id,
			@ModelAttribute("UserEditPassForm") final UserEditPassForm form, final BindingResult error) {
		ModelAndView mav = new ModelAndView("users/changePass");
		mav.addObject("userId", id);
		return mav;
	}

	@RequestMapping(value = "/editPassAccept/{userId}", method = { RequestMethod.POST })
	public ModelAndView editPassAccept(@PathVariable("userId") long id,
			@Valid @ModelAttribute("UserEditPassForm") final UserEditPassForm form, final BindingResult errors) {
		this.userPassFormValidator.validate(form, errors);
		if (errors.hasErrors()) {
			return editPass(id, form, errors);
		}

		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User u = us.findByUsername(username);

		User user = us.findById(id);

		if (form.getPassword() != null) {
			user.setPassword(passwordEncoder.encode(form.getPassword()));
		}

		us.update(user);
		if (u.equals(user)) {
			return new ModelAndView("redirect:/admin/");
		} else
			return new ModelAndView("redirect:/admin/" + user.getId());
	}

	/*
	 * Pagina del historial del usuario
	 */
	@RequestMapping("/ventas/{userId}")
	public ModelAndView historialventas(@PathVariable("userId") final long id, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("historial/historicList");
		User user = us.findById(id);

		Collection<Historial> listaventas = hi.findByidvendedor(user.getId());

		mav.addObject("user", user);
		mav.addObject("lista", listaventas);
		mav.addObject("listaSize", listaventas.size());
		mav.addObject("compraOventa", 1);

		return mav;
	}

	@RequestMapping("/compras/{userId}")
	public ModelAndView historialCompras(@PathVariable("userId") final long id, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("historial/historicList");
		User user = us.findById(id);

		Collection<Historial> listacompras = hi.findByidcomprador(user.getId());

		mav.addObject("user", user);
		mav.addObject("lista", listacompras);
		mav.addObject("listaSize", listacompras.size());
		mav.addObject("compraOventa", 0);

		return mav;
	}

	@RequestMapping("/issues")
	public ModelAndView issues(HttpServletRequest request) {
		final ModelAndView mav = new ModelAndView("admin/issues");
		String tipoS = request.getParameter("issue_tipo") != null ? request.getParameter("issue_tipo") : "";
		String asunto = request.getParameter("issue_nombre") != null ? request.getParameter("issue_nombre") : "";
		Integer tipo = 0;
		try {
			tipo = tipoS != "" ? Integer.valueOf(tipoS) : 0;
		} catch (Exception e) {
			tipo = 0;
		}
		Collection<Ayuda> list = null;

		if (asunto.equals("")) {
			if (tipo == 1) {
				list = ay.findByOutsideEstado(Ayuda.ALTA);
			} else if (tipo == 2) {
				list = ay.findByInsideEstado(Ayuda.ALTA);
			} else {
				list = ay.findByEstado(Ayuda.ALTA);
			}
		} else {
			if (tipo == 1) {
				list = ay.findByOutsideEstado(Ayuda.ALTA, asunto);
			} else if (tipo == 2) {
				list = ay.findByInsideEstado(Ayuda.ALTA, asunto);
			} else {
				list = ay.findByAsunto(asunto);
			}
		}

		if (list.isEmpty()) {
			mav.addObject("issue_tipo", tipo);
			mav.addObject("issue_nombre", asunto);
			mav.addObject("issues", list);
			mav.addObject("total_issues", 0);
			return mav;
		}
		mav.addObject("issue_tipo", tipo);
		mav.addObject("issue_nombre", asunto);
		mav.addObject("issues", list);
		mav.addObject("total_issues", list.size());
		return mav;
	}

	@RequestMapping("/archivedissues")
	public ModelAndView archivedissues(HttpServletRequest request) {
		final ModelAndView mav = new ModelAndView("admin/issues");
		String tipoS = request.getParameter("issue_tipo") != null ? request.getParameter("issue_tipo") : "";
		String asunto = request.getParameter("issue_nombre") != null ? request.getParameter("issue_nombre") : "";
		Integer tipo = 0;
		try {
			tipo = tipoS != "" ? Integer.valueOf(tipoS) : 0;
		} catch (Exception e) {
			tipo = 0;
		}
		Collection<Ayuda> list = null;

		if (asunto.equals("")) {
			if (tipo == 1) {
				list = ay.findByOutsideEstado(Ayuda.BAJA);
			} else if (tipo == 2) {
				list = ay.findByInsideEstado(Ayuda.BAJA);
			} else {
				list = ay.findByEstado(Ayuda.BAJA);
			}
		} else {
			if (tipo == 1) {
				list = ay.findByOutsideEstado(Ayuda.BAJA, asunto);
			} else if (tipo == 2) {
				list = ay.findByInsideEstado(Ayuda.BAJA, asunto);
			} else {
				list = ay.findByAsunto(asunto);
			}
		}

		if (list.isEmpty()) {
			mav.addObject("issue_tipo", tipo);
			mav.addObject("issue_nombre", asunto);
			mav.addObject("issues", list);
			mav.addObject("total_issues", 0);
			return mav;
		}
		mav.addObject("issue_tipo", tipo);
		mav.addObject("issue_nombre", asunto);
		mav.addObject("issues", list);
		mav.addObject("total_issues", list.size());
		return mav;
	}

}