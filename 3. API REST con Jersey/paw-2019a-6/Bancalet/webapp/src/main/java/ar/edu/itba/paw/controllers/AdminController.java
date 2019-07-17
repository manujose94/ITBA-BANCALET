package ar.edu.itba.paw.controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.config.security.validators.AdminRegisterFormValidator;
import ar.edu.itba.paw.config.security.validators.UserEditPassFormValidator;
import ar.edu.itba.paw.form.AdminHelpForm;
import ar.edu.itba.paw.form.AdminRegisterForm;
import ar.edu.itba.paw.form.AdminUserEditForm;
import ar.edu.itba.paw.form.UserEditPassForm;
import ar.edu.itba.paw.interfaces.services.AdminService;
import ar.edu.itba.paw.interfaces.services.AyudaService;
import ar.edu.itba.paw.interfaces.services.HistorialService;
import ar.edu.itba.paw.interfaces.services.ItemService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Ayuda;
import ar.edu.itba.paw.models.Historial;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

@RequestMapping("/admin")
@Controller
public class AdminController {

	@Autowired
	private UserService us;

	@Autowired
	private ItemService is;

	@Autowired
	private HistorialService hi;

	@Autowired
	private AyudaService ay;

	@Autowired
	private AdminService ad;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource messageSource;

	private UserEditPassFormValidator userPassFormValidator;
	private AdminRegisterFormValidator adminRegisterFormValidator;

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	public AdminController() {
		this.userPassFormValidator = new UserEditPassFormValidator();
		this.adminRegisterFormValidator = new AdminRegisterFormValidator();
	}

	@RequestMapping("")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("admin/dashboard");
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		long totalUsers = us.getTotalUsers();
		mav.addObject("totalUsers", totalUsers);
		long totalAltaItems = is.findAllAlta().size();
		mav.addObject("totalAltaItems", totalAltaItems);
		long totalItems = is.getTotalItems();
		mav.addObject("totalItems", totalItems);
		long totalAyudas = ay.getTotalAyudas();
		mav.addObject("totalAyudas", totalAyudas);
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping("/registUser")
	public ModelAndView registUser(@ModelAttribute("AdminRegisterForm") final AdminRegisterForm form) {
		ModelAndView mav = new ModelAndView("admin/registUser");
		ArrayList<String> roleList = new ArrayList<String>();
		roleList.add(User.ADMIN);
		roleList.add(User.USER);
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		long totalAyudas = ay.getTotalAyudas();
		mav.addObject("totalAyudas", totalAyudas);
		mav.addObject("user", user);
		mav.addObject("roleList", roleList);
		return mav;
	}

	@RequestMapping(value = "/createUser", method = { RequestMethod.POST })
	public ModelAndView createUser(HttpServletRequest request,
			@Valid @ModelAttribute("AdminRegisterForm") final AdminRegisterForm form, final BindingResult errors,
			final @RequestParam CommonsMultipartFile[] fileUpload) {
		/*
		 * Comprovar el formulario
		 */
		this.adminRegisterFormValidator.validate(form, errors);
		if (errors.hasErrors()) {
			return registUser(form);
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
		ad.createUser(form.getTelf(), form.getEmail(), form.getCity(), form.getCountry(), form.getCode(),
				form.getUsername(), passwordEncoder.encode(form.getPassword()), form.getRole(), form.getDireccion(),
				idTipo, "", fileUpload, form.getLat(), form.getLon());
		/*
		 * Volver a lista usuarios
		 */
		return new ModelAndView("redirect:/admin/userList");
	}

	@RequestMapping("/userList")
	public ModelAndView userList(HttpServletRequest request) {
		/*
		 * Recoger los datos
		 */
		final ModelAndView mav = new ModelAndView("admin/userList");
		String tipoS = request.getParameter("user_roles") != null ? request.getParameter("user_roles") : "";
		String tipoE = request.getParameter("users_estado") != null ? request.getParameter("users_estado") : "";
		String name = request.getParameter("item_nombre") != null ? request.getParameter("item_nombre") : "";
		Integer tipoRole = null;
		Integer tipoEstado = null;
		// por si editan el get y ponen un caracter
		try {
			tipoRole = tipoS != "" ? Integer.valueOf(tipoS) : 0;
		} catch (Exception e) {
			tipoRole = 0;
		}
		try {
			tipoEstado = tipoE != "" ? Integer.valueOf(tipoE) : 2;
		} catch (Exception e) {
			tipoEstado = 2;
		}
		String role = "";
		switch (tipoRole) {
		case 1:
			role = User.ADMIN;
			break;
		case 2:
			role = User.USER;
			break;
		default:
			role = "";
			break;
		}
		int size = 0;
		Collection<User> list = ad.findUsers(name, tipoEstado, role);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		long totalAyudas = ay.getTotalAyudas();
		mav.addObject("totalAyudas", totalAyudas);
		mav.addObject("users_estado", tipoEstado);
		mav.addObject("user", user);
		mav.addObject("user_roles", tipoRole);
		mav.addObject("item_nombre", name);
		if (list == null) {
			mav.addObject("tipo", list);
			mav.addObject("total_users", size);
			return mav;
		}
		size = list.size();
		mav.addObject("users", list);
		mav.addObject("total_users", size);
		return mav;
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
		long totalAyudas = ay.getTotalAyudas();
		mav.addObject("totalAyudas", totalAyudas);
		User userIndex = ad.getUserById(id);
		if (userIndex != null) {
			long totalSales = us.getUserTotalSales(userIndex);
			mav.addObject("totalSales", totalSales);
			int rate = (int) userIndex.getRate();
			mav.addObject("rateo", rate);
			mav.addObject("user_index", userIndex);
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
			return mav;
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

	/*
	 * Dar de baja un usuario
	 */
	@RequestMapping("/{userId}/bajaUser")
	public ModelAndView bajaUser(@PathVariable("userId") long id) {
		ad.bajaUser(id);
		return new ModelAndView("redirect:/admin/" + id);
	}

	/*
	 * Dar de alta un usuario
	 */
	@RequestMapping("/{userId}/altaUser")
	public ModelAndView altaUser(@PathVariable("userId") long id) {
		ad.altaUser(id);
		return new ModelAndView("redirect:/admin/" + id);
	}

	/*
	 * Pagina de editar perfil de usuario
	 */
	@RequestMapping("/editUsr/{userId}")
	public ModelAndView editUsr(@PathVariable("userId") final long id,
			@ModelAttribute("AdminUserEditForm") final AdminUserEditForm form, final BindingResult error) {
		ModelAndView mav = new ModelAndView("users/edit");
		User userIndex = ad.getUserById(id);
		mav.addObject("user", userIndex);
		mav.addObject("userId", id);
		return mav;
	}

	@RequestMapping(value = "/editAccept/{userId}", method = { RequestMethod.POST })
	public ModelAndView editAccept(HttpServletRequest request, @PathVariable("userId") final long id,
			@Valid @ModelAttribute("AdminUserEditForm") final AdminUserEditForm form, final BindingResult errors,
			final @RequestParam CommonsMultipartFile[] fileUpload) {
		if (errors.hasErrors()) {
			return editUsr(id, form, errors);
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

		ad.updateUser(id, form.getTelf(), form.getCity(), form.getCountry(), form.getCode(), form.getDireccion(),
				idTipo, null, fileUpload, form.getLat(), form.getLon());

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

		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		User userIndex = us.findById(id);
		boolean equals = user.equals(userIndex);
		userIndex = ad.updateUserPass(userIndex, form.getPassword(), equals);
		if (equals) {
			return new ModelAndView("redirect:/admin/");
		} else {
			return new ModelAndView("redirect:/admin/" + userIndex.getUserId());
		}

	}

	/*
	 * Pagina del historial del usuario
	 */
	@RequestMapping("/ventas/{userId}")
	public ModelAndView historialventas(@PathVariable("userId") final long id, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("historial/mysales");
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);
		int size = 0;
		User userIndex = us.findById(id);
		mav.addObject("user_index", userIndex);
		Collection<Item> itemsvendidos = is.findByVendedorId(userIndex.getUserId(), Item.BAJA);
		Collection<Historial> listaventas = hi.findByidVendedor(userIndex.getUserId());
		Collection<User> listcompradores = us.findByidVendedorconHistorial(userIndex.getUserId());
		long totalAyudas = ay.getTotalAyudas();
		mav.addObject("totalAyudas", totalAyudas);
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

	@RequestMapping("/compras/{userId}")
	public ModelAndView historialCompras(@PathVariable("userId") final long id, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("historial/mypurchases");
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);
		long totalAyudas = ay.getTotalAyudas();
		mav.addObject("totalAyudas", totalAyudas);
		User userIndex = us.findById(id);
		mav.addObject("user_index", userIndex);
		int size = 0;
		Collection<Historial> listacompras = hi.findByidComprador(userIndex.getUserId());
		Collection<User> listvendedores = us.findByidCompradorconHistorial(userIndex.getUserId());
		Collection<Item> itemscomprados = is.findByCompradorId(userIndex.getUserId(), Item.BAJA);
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

	@RequestMapping("/issues")
	public ModelAndView issues(HttpServletRequest request) {

		final ModelAndView mav = new ModelAndView("admin/issues");
		String tipoS = request.getParameter("issue_tipo") != null ? request.getParameter("issue_tipo") : "";
		String asunto = request.getParameter("issue_nombre") != null ? request.getParameter("issue_nombre") : "";
		Integer tipo = 0;
		int size = 0;
		// por si editan el get y ponen un caracter
		try {
			tipo = tipoS != "" ? Integer.valueOf(tipoS) : 0;
		} catch (Exception e) {
			tipo = 0;
		}
		// todas tipo = 0
		// dentro tipo = 1
		// fuera tipo = 2
		// si no es ninguno que ponga por defecto 0
		if (tipo < 0 || tipo > 2) {
			tipo = 0;
		}

		Collection<Ayuda> list = ad.findAyudas(asunto, tipo, Ayuda.ALTA);
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		long totalAyudas = ay.getTotalAyudas();
		mav.addObject("totalAyudas", totalAyudas);
		mav.addObject("user", user);
		mav.addObject("issue_tipo", tipo);
		mav.addObject("issue_nombre", asunto);
		if (list == null) {

			mav.addObject("total_issues", size);
			return mav;
		}
		size = list.size();
		mav.addObject("issues", list);
		mav.addObject("total_issues", size);
		return mav;
	}

	@RequestMapping("/archivedissues")
	public ModelAndView archivedissues(HttpServletRequest request) {
		final ModelAndView mav = new ModelAndView("admin/issues");
		String tipoS = request.getParameter("issue_tipo") != null ? request.getParameter("issue_tipo") : "";
		String asunto = request.getParameter("issue_nombre") != null ? request.getParameter("issue_nombre") : "";
		Integer tipo = 0;
		int size = 0;
		long totalAyudas = ay.getTotalAyudas();
		mav.addObject("totalAyudas", totalAyudas);
		try {
			tipo = tipoS != "" ? Integer.valueOf(tipoS) : 0;
		} catch (Exception e) {
			tipo = 0;
		}
		Collection<Ayuda> list = ad.findAyudas(asunto, tipo, Ayuda.BAJA);
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);

		if (list == null) {
			mav.addObject("issue_tipo", tipo);
			mav.addObject("issue_nombre", asunto);
			mav.addObject("total_issues", size);
			return mav;
		}
		size = list.size();
		mav.addObject("issue_tipo", tipo);
		mav.addObject("issue_nombre", asunto);
		mav.addObject("issues", list);
		mav.addObject("total_issues", size);
		return mav;
	}

	/*
	 * Pagina de una issue
	 */
	@RequestMapping("/issues/{issueId}")
	public ModelAndView issueIndex(@PathVariable("issueId") final long id, HttpServletResponse response) {
		final ModelAndView mav = new ModelAndView("admin/issue");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);
		long totalAyudas = ay.getTotalAyudas();
		mav.addObject("totalAyudas", totalAyudas);
		Ayuda issue = ay.findById(id);
		if (issue != null) {
			mav.addObject("issue", issue);
			return mav;
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

	/*
	 * Pagina para eliminar una issue (solo si estado es alta)
	 */
	@RequestMapping("/issues/{issueId}/delete")
	public ModelAndView issueDelete(@PathVariable("issueId") final long id, HttpServletResponse response) {

		Ayuda issue = ay.findById(id);
		if (issue != null) {
			if (issue.getEstado() == Ayuda.ALTA) {
				ay.delete(id);
			}

		} else {
			response.setStatus(404);
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("issue.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("issue.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		return new ModelAndView("redirect:/admin/issues/");
	}

	/*
	 * Pagina para archivar una issue (dar de baja la issue)
	 */
	@RequestMapping("/issues/{issueId}/archive")
	public ModelAndView issueArchive(@ModelAttribute("AdminHelpForm") final AdminHelpForm form,
			@PathVariable("issueId") final long id, HttpServletResponse response, final BindingResult errors) {

		final ModelAndView mav = new ModelAndView("admin/issueForm");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);

		Ayuda issue = ay.findById(id);

		if (issue != null) {
			String username = issue.getName();
			mav.addObject("username", username);
			mav.addObject("issue", issue);
			return mav;
		} else {
			response.setStatus(404);
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("issue.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("issue.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
	}

	/*
	 * Confirmacion de archivacion (solo si estado es alta)
	 */
	@RequestMapping("/issues/{issueId}/confirmArchive")
	public ModelAndView confirmArchive(@Valid @ModelAttribute("AdminHelpForm") final AdminHelpForm form,
			@PathVariable("issueId") final long id, HttpServletResponse response, final BindingResult errors) {
		if (errors.hasErrors()) {
			return issueArchive(form, id, response, errors);
		}
		ModelAndView mav = new ModelAndView("admin/issueFormSucces");
		Ayuda issue = ay.findById(id);

		if (issue != null) {
			if (issue.getEstado() == Ayuda.ALTA) {
				Validation val = ad.archiveIssue(issue, form.getInforme());
				String codigo = "";
				int tipo = 0;
				Object[] parameter = { issue.getEmail() };
				if (val.isOk()) {
					codigo = messageSource.getMessage("admin.report_succes_send", parameter,
							LocaleContextHolder.getLocale());

				} else {
					tipo = 1;
					codigo = messageSource.getMessage("admin.report_error_send", parameter,
							LocaleContextHolder.getLocale());
				}
				mav.addObject("tipo", tipo);
				mav.addObject("mensaje", codigo);
				mav.addObject("issue", issue);
				LOGGER.debug(codigo);
				return mav;
			}
		}
		response.setStatus(404);
		return (new ModelAndView("error"))
				.addObject("body",
						messageSource.getMessage("issue.error.not.found.body", null, LocaleContextHolder.getLocale()))
				.addObject("title",
						messageSource.getMessage("issue.error.not.found.title", null, LocaleContextHolder.getLocale()));
	}

}