package ar.edu.itba.paw.controllers;

import java.sql.Date;
import java.util.Calendar;
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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.config.security.validators.ItemValidator;
import ar.edu.itba.paw.form.ContactForm;
import ar.edu.itba.paw.form.ItemRateForm;
import ar.edu.itba.paw.form.ItemRegisterForm;
import ar.edu.itba.paw.interfaces.services.AdminService;
import ar.edu.itba.paw.interfaces.services.AyudaService;
import ar.edu.itba.paw.interfaces.services.ContactoService;
import ar.edu.itba.paw.interfaces.services.HistorialService;
import ar.edu.itba.paw.interfaces.services.ItemImageService;
import ar.edu.itba.paw.interfaces.services.ItemService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Contacto;
import ar.edu.itba.paw.models.Historial;
import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

@Controller
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private UserService us;

	@Autowired
	private ItemService is;

	@Autowired
	private HistorialService hi;

	@Autowired
	private ContactoService co;

	@Autowired
	private AdminService ad;

	@Autowired
	private AyudaService ay;

	@Autowired
	private ItemImageService imageItemService;

	@Autowired
	private MessageSource messageSource;

	private ItemValidator it;

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

	public ItemController() {
		this.it = new ItemValidator();
	}

	@RequestMapping("")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("items/list");
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		// -------------------------------------------------------
		if (user != null) {
			mav.addObject("user", user);
			// comprobar mensajes
			long sizeMsg = co.getUserContacts(user.getUserId());
			mav.addObject("listaSizeMsg", sizeMsg);
			// fin comprobar mensajes
			// comprobar norate
			long sizeRate = hi.getUserHistorialNoRate(user.getUserId());
			mav.addObject("listaSizeRate", sizeRate);
			// fin comprobar norate
			if (user.getRole().contentEquals(User.ADMIN)) {
				long totalAyudas = ay.getTotalAyudas();
				mav.addObject("totalAyudas", totalAyudas);
			}

		}
		String name = request.getParameter("item_nombre") != null ? request.getParameter("item_nombre") : "";
		String itemTipo = request.getParameter("item_tipo") != null ? request.getParameter("item_tipo") : "";
		String slider = request.getParameter("slider");
		String minSlaider = request.getParameter("minimoSlaider");
		String maxSlaider = request.getParameter("maximoSlaider");
		String itemTipoCaducidad = request.getParameter("item_tipo_caducidad") != null
				? request.getParameter("item_tipo_caducidad")
				: "";
		String fecha = request.getParameter("fecha_caducidad") != null ? request.getParameter("fecha_caducidad") : "";
		Integer tipo = null;
		Integer tipoCaducidad = null;
		double min = 0.0;
		double max = 0.0;
		double maximoSeleccionado = 0.0;
		double minimoSeleccionado = 0.0;
		// PRECIO MAXIMO
		max = is.getMaxPrice();

		// por si editan el get y ponen un caracter
		try {
			tipo = itemTipo != "" ? Integer.valueOf(itemTipo) : 0;
		} catch (Exception e) {
			tipo = 0;
		}
		try {
			tipoCaducidad = itemTipoCaducidad != "" ? Integer.valueOf(itemTipoCaducidad) : 0;
		} catch (Exception e) {
			tipoCaducidad = 0;
		}
		// tipo entre 0 y 5
		if (tipo < 0) {
			tipo = 0;
		} else if (tipo > 5) {
			tipo = 5;
		}
		// tipoCaducidad entre 0 y 2
		if (tipoCaducidad < 0) {
			tipoCaducidad = 0;
		} else if (tipoCaducidad > 2) {
			tipoCaducidad = 2;
		}
		/*
		 * Obtener maximo y minimo
		 */
		if (maxSlaider != null) {
			try {// por si editan el get y ponen un caracter
				maximoSeleccionado = maxSlaider != null ? Double.valueOf(maxSlaider) : maximoSeleccionado;
			} catch (Exception e) {
				maximoSeleccionado = is.getMaxPrice();
			}
		}
		if (minSlaider != null) {
			try {// por si editan el get y ponen un caracter
				minimoSeleccionado = minSlaider != null ? Double.valueOf(minSlaider) : 0.0;
			} catch (Exception e) {
				minimoSeleccionado = 0.0;
			}
		}

		/*
		 * MIN Y MAX SELECCIONADOS
		 */
		if (maximoSeleccionado == 0.0)
			maximoSeleccionado = max;
		mav.addObject("min", minimoSeleccionado);
		mav.addObject("max", maximoSeleccionado);
		/*
		 * MIN Y MAX TOTALES
		 */
		mav.addObject("minimo", min);
		max = max == min ? 1.0 : max;

		mav.addObject("maximo", max);

		/**
		 * OBTENER ITEMS
		 */
		Date date;
		Collection<Item> items;
		try {
			// DateFormat.
			date = Date.valueOf(fecha);
			items = is.getItems(slider, tipo, name, minimoSeleccionado, maximoSeleccionado, date, tipoCaducidad);
			mav.addObject("fecha_caducidadCheck", 1);
			mav.addObject("fecha_caducidad", date);
		} catch (Exception e) {
			Calendar calendar = Calendar.getInstance();
			date = new java.sql.Date(calendar.getTime().getTime());
			items = is.getItems(slider, tipo, name, minimoSeleccionado, maximoSeleccionado);
			mav.addObject("fecha_caducidad", date);
			mav.addObject("fecha_caducidadCheck", 0);
		}
		long size = 0;
		if (items != null) {
			size = items.size();
		}
		mav.addObject("item_nombre", name);
		mav.addObject("tipo", tipo);
		mav.addObject("tipoCad", tipoCaducidad);
		mav.addObject("items", items);
		mav.addObject("total_items", size);

		return mav;
	}

	@RequestMapping("/{id}") // vista en detalle de un item
	public ModelAndView index(@PathVariable("id") long id, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("items/index");
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		if (user != null) {
			mav.addObject("user", user);
			// comprobar mensajes
			long sizeMsg = co.getUserContacts(user.getUserId());
			mav.addObject("listaSizeMsg", sizeMsg);
			// fin comprobar mensajes
			// comprobar norate
			long sizeRate = hi.getUserHistorialNoRate(user.getUserId());
			mav.addObject("listaSizeRate", sizeRate);
			// fin comprobar norate
			if (user.getRole().contentEquals(User.ADMIN)) {
				long totalAyudas = ay.getTotalAyudas();
				mav.addObject("totalAyudas", totalAyudas);
			}
		}
		Item item = is.getItem(id);
		if (item == null) {
			response.setStatus(404);
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		boolean propietario = false;
		if (user != null) {
			Contacto contactado = co.findContactoByidCompradorIdItem(user.getUserId(), id, item.getIdVendedor());
			mav.addObject("contactado", contactado);
			mav.addObject("user", user);
			// Propetario
			propietario = user.getUserId() == item.getIdVendedor() ? true : false;
		}
		User seller = us.findById(item.getIdVendedor());
		int rate = (int) seller.getRate();

		mav.addObject("imagesItem", imageItemService.getImagesItemsEncode(imageItemService.findAllImagesItem(id)));
		mav.addObject("rateo", rate);
		mav.addObject("propietario", propietario);
		mav.addObject("item", item);
		mav.addObject("seller", seller);
		mav.addObject("item", item);
		return mav;
	}

	@RequestMapping("/{id}/edit")
	public ModelAndView edit(@PathVariable("id") long id,
			@ModelAttribute("ItemRegisterForm") final ItemRegisterForm form) {
		ModelAndView mav = new ModelAndView("items/editItem");
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		Item item = is.findById(id);
		User seller = us.findById(item.getIdVendedor());
		if (!seller.equals(user)) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		// comprobar mensajes
		long sizeMsg = co.getUserContacts(user.getUserId());
		mav.addObject("listaSizeMsg", sizeMsg);
		// fin comprobar mensajes
		// comprobar norate
		long sizeRate = hi.getUserHistorialNoRate(user.getUserId());
		mav.addObject("listaSizeRate", sizeRate);
		// fin comprobar norate
		mav.addObject("user", user);
		mav.addObject("item", item);
		return mav;
	}

	@RequestMapping("/contactado")
	public ModelAndView contactado(HttpServletRequest request,
			@ModelAttribute("ItemRegisterForm") final ItemRegisterForm form) {
		return new ModelAndView("redirect:/users/miscontactose");
	}

	@RequestMapping("/{id}/delete")
	public ModelAndView delete(@PathVariable("id") long id, HttpServletRequest request,
			@ModelAttribute("ItemRegisterForm") final ItemRegisterForm form) {
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		Item item = is.findById(id);
		if (item != null) {
			if (user != null) {
				if (user.getRole().equals(User.ADMIN)) {
					ad.deleteItem(id);
				} else {
					User seller = us.findById(item.getIdVendedor());
					if (!seller.equals(user)) {
						return (new ModelAndView("error"))
								.addObject("body",
										messageSource.getMessage("items.error.not.found.body", null,
												LocaleContextHolder.getLocale()))
								.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
										LocaleContextHolder.getLocale()));
					}
					is.delete(id);
				}
			} else {
				return (new ModelAndView("error"))
						.addObject("body",
								messageSource.getMessage("items.error.not.found.body", null,
										LocaleContextHolder.getLocale()))
						.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
								LocaleContextHolder.getLocale()));
			}
		} else {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}

		return new ModelAndView("redirect:/items/");
	}

	@RequestMapping("/{id}/baja")
	public ModelAndView baja(@PathVariable("id") long id, HttpServletRequest request) {
		String sellerId = request.getParameter("comprador");
		long idcomprador = -1;
		try {
			idcomprador = Long.valueOf(sellerId);
		} catch (Exception e) {
			return (new ModelAndView("error"))
					.addObject("title",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("body", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		Item item = is.findById(id);
		if (item != null) {
			if (user != null) {
				User seller = us.findById(item.getIdVendedor());
				if (!seller.equals(user)) {
					return (new ModelAndView("error"))
							.addObject("body",
									messageSource.getMessage("items.error.not.found.body", null,
											LocaleContextHolder.getLocale()))
							.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
									LocaleContextHolder.getLocale()));
				}
			} else {
				return (new ModelAndView("error"))
						.addObject("body",
								messageSource.getMessage("items.error.not.found.body", null,
										LocaleContextHolder.getLocale()))
						.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
								LocaleContextHolder.getLocale()));
			}
		} else {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		is.bajaItem(id, idcomprador);

		return new ModelAndView("redirect:/items/" + id);
	}

	@RequestMapping("/{id}/confirmarbaja")
	public ModelAndView confirmarbaja(@PathVariable("id") long id, HttpServletRequest request) {

		final ModelAndView mav = new ModelAndView("items/confirmarBaja");

		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);
		User seller = null;
		Item item = is.findById(id);
		if (item != null) {
			if (user != null) {
				seller = us.findById(item.getIdVendedor());
				if (!seller.equals(user)) {
					return (new ModelAndView("error"))
							.addObject("body",
									messageSource.getMessage("items.error.not.found.body", null,
											LocaleContextHolder.getLocale()))
							.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
									LocaleContextHolder.getLocale()));
				}
			} else {
				return (new ModelAndView("error"))
						.addObject("body",
								messageSource.getMessage("items.error.not.found.body", null,
										LocaleContextHolder.getLocale()))
						.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
								LocaleContextHolder.getLocale()));
			}
		} else {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		// comprobar mensajes
		long sizeMsg = co.getUserContacts(user.getUserId());
		mav.addObject("listaSizeMsg", sizeMsg);
		// fin comprobar mensajes
		// comprobar norate
		long sizeRate = hi.getUserHistorialNoRate(user.getUserId());
		mav.addObject("listaSizeRate", sizeRate);
		// fin comprobar norate
		Collection<User> compradores = us.findAllUserContactsItem(id);
		// Propetario
		boolean propietario = user.getUserId() == item.getIdVendedor() ? true : false;
		// PROPIETARIO?
		mav.addObject("propietario", propietario);
		mav.addObject("seller", seller);
		mav.addObject("users", compradores);
		mav.addObject("item", item);
		mav.addObject("itemid", item.getItemId());
		return mav;
	}

	@RequestMapping("/register")
	public ModelAndView register(@ModelAttribute("ItemRegisterForm") final ItemRegisterForm form) {
		ModelAndView mav = new ModelAndView("items/addItem");
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		if (user == null) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		// comprobar mensajes
		long sizeMsg = co.getUserContacts(user.getUserId());
		mav.addObject("listaSizeMsg", sizeMsg);
		// fin comprobar mensajes
		// comprobar norate
		long sizeRate = hi.getUserHistorialNoRate(user.getUserId());
		mav.addObject("listaSizeRate", sizeRate);
		// fin comprobar norate
		mav.addObject("user", user);
		mav.addObject("carne", Item.CARNE);
		mav.addObject("fruta", Item.FRUTA);
		mav.addObject("pescado", Item.PESCADO);
		mav.addObject("verdura", Item.VERDURA);
		mav.addObject("otros", Item.OTROS);
		return mav;
	}

	@RequestMapping(value = "/create", method = { RequestMethod.POST })
	public ModelAndView create(HttpServletRequest request,
			@Valid @ModelAttribute("ItemRegisterForm") final ItemRegisterForm form, final BindingResult errors,
			final @RequestParam CommonsMultipartFile[] fileUploadMultiple) {
		this.it.validate(form, errors);
		if (errors.hasErrors()) {
			return register(form);
		}
		// USER ACTUAL SESSION
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		if (user == null) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		int tipo = 0;
		try {
			String item_tipo = request.getParameter("item_tipo");
			tipo = Integer.valueOf(item_tipo);
		} catch (Exception e) {
		}
		String fecha = form.getFecha_caducidad();
		Date date = Date.valueOf(fecha);

		Item item = is.createItem(user, user.getUserId(), form.getName(), tipo, form.getPrice(), form.getDescription(),
				date, fileUploadMultiple);
		return new ModelAndView("redirect:/items/" + item.getItemId());
	}

	@RequestMapping(value = "/{id}/update", method = { RequestMethod.POST })
	public ModelAndView update(@PathVariable("id") long id, HttpServletRequest request,
			@Valid @ModelAttribute("ItemRegisterForm") final ItemRegisterForm form, final BindingResult errors,
			final @RequestParam CommonsMultipartFile[] fileUploadMultiple) {
		this.it.validate(form, errors);
		if (errors.hasErrors()) {
			return edit(id, form);
		}
		// USER ACTUAL SESSION
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		if (user == null) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		Item item = is.findById(id);
		if (item != null) {
			User seller = us.findById(item.getIdVendedor());
			if (!seller.equals(user)) {
				return (new ModelAndView("error"))
						.addObject("body",
								messageSource.getMessage("items.error.not.found.body", null,
										LocaleContextHolder.getLocale()))
						.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
								LocaleContextHolder.getLocale()));
			}
		} else {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}

		String fecha = form.getFecha_caducidad();
		Date date = Date.valueOf(fecha);
		int tipo = 0;
		try {
			String item_tipo = request.getParameter("item_tipo");
			tipo = Integer.valueOf(item_tipo);
		} catch (Exception e) {
		}
		is.updateItem(id, form.getName(), tipo, form.getPrice(), form.getDescription(), date, fileUploadMultiple);
		return new ModelAndView("redirect:/items/" + id);
	}

	@RequestMapping(value = "/{itemId}/delitemimages", method = { RequestMethod.POST })
	public ModelAndView delitemimages(@PathVariable("itemId") final long itemId, HttpServletRequest request) {
		Item item = is.findById(itemId);
		User propietario = us.findById(item.getIdVendedor());
		// USER ACTUAL SESSION
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		if (user == null) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		if (!propietario.equals(user)) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		is.deleteItemImageTrans(itemId);
		return new ModelAndView("redirect:/items/" + itemId);
	}

	@RequestMapping("/myitems")
	public ModelAndView myitems(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("users/myitems");
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		mav.addObject("user", user);
		if (user == null) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		// comprobar mensajes
		long sizeMsg = co.getUserContacts(user.getUserId());
		mav.addObject("listaSizeMsg", sizeMsg);
		// fin comprobar mensajes
		// comprobar norate
		long sizeRate = hi.getUserHistorialNoRate(user.getUserId());
		mav.addObject("listaSizeRate", sizeRate);
		// fin comprobar norate
		long userid = user.getUserId();
		int size = 0;
		Collection<Item> items = is.findByVendedorId(userid, Item.ALTA);
		if (items != null) {
			size = items.size();
		}
		mav.addObject("items", items);
		mav.addObject("total_items", size);

		return mav;
	}

	@RequestMapping("/contactar/{itemId}")
	public ModelAndView contactar(@PathVariable("itemId") final long id,
			@ModelAttribute("ContactForm") final ContactForm form, final BindingResult error) {
		ModelAndView mav = new ModelAndView("contact/contactComprador");
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		if (user == null) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		Item item = is.findById(id);
		if (item == null) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		long idvendedor = item.getIdVendedor();
		User vendedor = us.findById(idvendedor);
		if (user.equals(vendedor)) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("user.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("user.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		mav.addObject("user", user);
		mav.addObject("comprador", user);
		mav.addObject("vendedor", vendedor);
		mav.addObject("itemId", id);
		return mav;
	}

	@RequestMapping(value = "/sendto/{itemId}", method = { RequestMethod.POST })
	public ModelAndView send(@PathVariable("itemId") final long id, HttpServletRequest request,
			@Valid @ModelAttribute("ContactForm") final ContactForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return contactar(id, form, errors);
		}
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User comprador = us.findUserSessionByUsername(principal);
		if (comprador == null) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("user.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("user.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		ModelAndView mav = new ModelAndView("contact/contactSucces");
		Item item = is.findById(id);
		if (item == null) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}

		long idvendedor = item.getIdVendedor();
		User vendedor = us.findById(idvendedor);
		if (comprador.equals(vendedor)) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("user.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("user.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		String codigo;
		Validation val = co.createContact(item, comprador, vendedor, form.getMensaje());
		int tipo = 0;
		if (val.isOk()) {
			codigo = messageSource.getMessage("items.succes_send", null, LocaleContextHolder.getLocale())
					+ comprador.getEmail() + " | " + comprador.getTelf();
		} else {
			tipo = 1;
			codigo = messageSource.getMessage("items.error_send", null, LocaleContextHolder.getLocale()) + " "
					+ vendedor.getEmail() + " , ERROR: " + val.getMessage();
		}
		LOGGER.debug(codigo);
		mav.addObject("tipo", tipo);
		mav.addObject("mensaje", codigo);
		mav.addObject("vendedor", vendedor);
		return mav;
	}

	@RequestMapping(value = "/{id}/ratehistoric/")
	public ModelAndView rate(@PathVariable("id") long id, HttpServletRequest request,
			@ModelAttribute("ItemRateForm") final ItemRateForm form, final BindingResult errors) {
		ModelAndView mav = new ModelAndView("items/rate");
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User comprador = us.findUserSessionByUsername(principal);
		if (comprador == null) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("user.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("user.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		Historial historial = hi.findByIdItem(id);
		if (historial == null) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		User vendedor = us.findById(historial.getIdVendedor());
		if (comprador.equals(vendedor)) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("user.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("user.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}

		mav.addObject("id", id);
		mav.addObject("vendedor", vendedor);
		return mav;
	}

	@RequestMapping(value = "/{id}/rateSucces/", method = { RequestMethod.POST })
	public ModelAndView rateSucces(@PathVariable("id") long id, HttpServletRequest request,
			@Valid @ModelAttribute("ItemRateForm") final ItemRateForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return rate(id, request, form, errors);
		}
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User comprador = us.findUserSessionByUsername(principal);
		if (comprador == null) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("user.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("user.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		int stars = form.getEstrellas();
		if (stars < 0) {
			stars = 0;
		} else if (stars > 5) {
			stars = 5;
		}
		Historial historial = hi.findByIdItem(id);
		if (historial == null) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("items.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("items.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		User vendedor = us.findById(historial.getIdVendedor());
		if (comprador.equals(vendedor)) {
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("user.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("user.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}
		is.rateItem(id, stars, form.getValoracion());
		ModelAndView mav = new ModelAndView("redirect:/users/miscompras");
		return mav;
	}
}
