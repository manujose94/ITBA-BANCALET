package ar.edu.itba.paw.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.config.security.ItemValidator;
import ar.edu.itba.paw.form.ContactForm;
import ar.edu.itba.paw.form.ItemRateForm;
import ar.edu.itba.paw.form.ItemRegisterForm;
import ar.edu.itba.paw.interfaces.services.ContactoService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.HistorialService;
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
	private MessageSource messageSource;
	private ItemValidator it;

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

	private final String ASUNTO_INTERESADO = "Bancalet Virtual: Tienes una nueva oferta de: ";
	private final String MENSAJE_INTERESADO = "\nEste mensaje no debe ser respondido. Datos de contacto del interesado: ";

	@Autowired
	EmailService emailService;

	public ItemController() {
		// TODO Auto-generated constructor stub
		this.it = new ItemValidator();
	}

	@RequestMapping("")
	public ModelAndView list(HttpServletRequest request) {
		final ModelAndView mav = new ModelAndView("items/list");
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

			// items = item_nombre != null ? is.findByItemname(item_nombre) : is.findAll();

			items = is.findByFilter(tipo, Item.ALTA, minimo, maximo, item_nombre);
			mav.addObject("items", items);
			mav.addObject("total_items", items.size());
			mav.addObject("min", min);
			mav.addObject("max", max);
			mav.addObject("maximo", maximo);
		}
		return mav;
	}

	@SuppressWarnings("unused")
	@RequestMapping("/{id}") // vista en detalle de un item
	public ModelAndView index(@PathVariable("id") long id, HttpServletResponse response) {
		Item item = is.findById(id);

		// OBTENER USUARIO LOGUEADO ACTUAL
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User user = us.findByUsername(username);
		Contacto contactado = co.findContactoByIdCompradorIdItem(user.getId(), id, item.getIdvendedor());
		// Propetario
		boolean propietario = user.getId() == item.getIdvendedor() ? true : false;

		User seller = null;
		if (item != null)
			seller = us.findById(item.getIdvendedor());

		if (item == null) {
			response.setStatus(404); // TODO use web.xml and ErrorController
			return (new ModelAndView("error"))
					.addObject("body",
							messageSource.getMessage("table.error.not.found.body", null,
									LocaleContextHolder.getLocale()))
					.addObject("title", messageSource.getMessage("table.error.not.found.title", null,
							LocaleContextHolder.getLocale()));
		}

		final ModelAndView mav;

		mav = new ModelAndView("items/index");

		// PROPIETARIO?
		mav.addObject("propietario", propietario);
		mav.addObject("seller", seller);
		mav.addObject("contactado", contactado);
		mav.addObject("item", item);

		mav.addObject("numeroVisitas", item.getNumeroVisitas());
		return mav;
	}

	@RequestMapping("/{id}/edit")
	public ModelAndView edit(@PathVariable("id") long id,
			@ModelAttribute("ItemRegisterForm") final ItemRegisterForm form) {
		Item item = is.findById(id);

		final ModelAndView mav = new ModelAndView("items/editItem");
		mav.addObject("item", item);
		return mav;
	}

	@RequestMapping("/contactado")
	public ModelAndView contactado(HttpServletRequest request,
			@ModelAttribute("ItemRegisterForm") final ItemRegisterForm form) {
		ModelAndView mav = new ModelAndView("contact/contactComprador");

		return new ModelAndView("redirect:/users/miscontactose");
	}

	@RequestMapping("/{id}/delete")
	public ModelAndView delete(@PathVariable("id") long id, HttpServletRequest request,
			@ModelAttribute("ItemRegisterForm") final ItemRegisterForm form) {
		is.delete(id);

		return new ModelAndView("redirect:/items/");
	}

	@RequestMapping("/{id}/baja")
	public ModelAndView baja(@PathVariable("id") long id, HttpServletRequest request) {

		Item item = is.findById(id);
		Calendar calendar = Calendar.getInstance();
		java.sql.Date fecha_compra = new java.sql.Date(calendar.getTime().getTime());

		String valoracion = "";
		int estrellas = 0;
		String item_tipo = request.getParameter("comprador");
		long idcomprador = Long.valueOf(item_tipo);
		// crea historial automaticamente
		is.baja(id, idcomprador, item.getIdvendedor(), valoracion, estrellas, fecha_compra);

		return new ModelAndView("redirect:/items/{id}");
	}

	@RequestMapping("/{id}/confirmarbaja")
	public ModelAndView confirmarbaja(@PathVariable("id") long id, HttpServletRequest request) {

		Item item = is.findById(id);
		Collection<User> compradores = us.findAllUserContactsItem(id);
		// OBTENER USUARIO LOGUEADO ACTUAL
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User user = us.findByUsername(username);

		// Propetario
		boolean propietario = user.getId() == item.getIdvendedor() ? true : false;

		User seller = null;
		if (item != null)
			seller = us.findById(item.getIdvendedor());

		final ModelAndView mav;

		mav = new ModelAndView("items/confirmarBaja");

		// PROPIETARIO?
		mav.addObject("propietario", propietario);
		mav.addObject("seller", seller);
		mav.addObject("users", compradores);
		mav.addObject("item", item);
		mav.addObject("itemid", item.getItemid());

		return mav;
	}

	@RequestMapping("/register")
	public ModelAndView register(@ModelAttribute("ItemRegisterForm") final ItemRegisterForm form) {
		final ModelAndView mav = new ModelAndView("items/addItem");

		mav.addObject("carne", Item.CARNE);
		mav.addObject("fruta", Item.FRUTA);
		mav.addObject("pescado", Item.PESCADO);
		mav.addObject("verdura", Item.VERDURA);
		mav.addObject("otros", Item.OTROS);
		return mav;
	}

	@RequestMapping(value = "/create", method = { RequestMethod.POST })
	public ModelAndView create(HttpServletRequest request,
			@Valid @ModelAttribute("ItemRegisterForm") final ItemRegisterForm form, final BindingResult errors) {

		this.it.validate(form, errors);
		if (errors.hasErrors()) {
			return register(form);
		}
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User user = us.findByUsername(username);

		Calendar calendar = Calendar.getInstance();
		java.sql.Date fecha_publicacion = new java.sql.Date(calendar.getTime().getTime());

		String fecha = form.getFecha_caducidad();
		Date date = Date.valueOf(fecha);

		String item_tipo = request.getParameter("item_tipo");
		int tipo = Integer.valueOf(item_tipo);
		Item it = is.create(user.getId(), form.getName(), tipo, form.getPrice(), form.getDescription(), date,
				fecha_publicacion, form.getImage(), Item.ALTA, 0);

		return new ModelAndView("redirect:/items/" + it.getItemid());
	}

	@RequestMapping(value = "/{id}/update", method = { RequestMethod.POST })
	public ModelAndView update(@PathVariable("id") long id, HttpServletRequest request,
			@Valid @ModelAttribute("ItemRegisterForm") final ItemRegisterForm form, final BindingResult errors) {
		this.it.validate(form, errors);
		if (errors.hasErrors()) {
			return edit(id, form);
		}
		Item item = is.findById(id);
		long idvendedor = item.getIdvendedor();

		Calendar calendar = Calendar.getInstance();
		java.sql.Date fecha_publicacion = new java.sql.Date(calendar.getTime().getTime());

		String fecha = form.getFecha_caducidad();
		Date date = Date.valueOf(fecha);

		String item_tipo = request.getParameter("item_tipo");
		int tipo = Integer.valueOf(item_tipo);

		Item it = new Item(id, idvendedor, form.getName(), tipo, form.getPrice(), form.getDescription(), date,
				fecha_publicacion, form.getImage(), Item.ALTA, 0);

		is.update(it);

		return new ModelAndView("redirect:/items/" + it.getItemid());
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		// binder.setValidator(new ItemValidator());

	}

	@RequestMapping("/myitems")
	public ModelAndView myitems(HttpServletRequest request) {
		final ModelAndView mav = new ModelAndView("users/myitems");

		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User user = us.findByUsername(username);

		long idUser = user.getId();

		Collection<Item> items = is.findByVendedorId(idUser, Item.ALTA);

		mav.addObject("items", items);
		mav.addObject("total_items", items.size());

		return mav;
	}

	@RequestMapping("/contactar/{itemId}")
	public ModelAndView contactar(@PathVariable("itemId") final long id,
			@ModelAttribute("ContactForm") final ContactForm form, final BindingResult error) {
		ModelAndView mav = new ModelAndView("contact/contactComprador");
		Item item = is.findById(id);
		long idvendedor = item.getIdvendedor();
		User vendedor = us.findById(idvendedor);

		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User comprador = us.findByUsername(username);

		mav.addObject("comprador", comprador);
		mav.addObject("vendedor", vendedor);
		mav.addObject("itemId", id);
		return mav;
	}

	@RequestMapping(value = "/sendto/{itemId}", method = { RequestMethod.POST })
	public ModelAndView send(@PathVariable("itemId") final long id, HttpServletRequest request,
			@ModelAttribute("ContactForm") final ContactForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return contactar(id, form, errors);
		}
		Item item = is.findById(id);
		long idvendedor = item.getIdvendedor();
		User vendedor = us.findById(idvendedor);

		ModelAndView mav = new ModelAndView("contact/contactSucces");

		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User comprador = us.findByUsername(username);

		String codigo;
		String asunto = ASUNTO_INTERESADO + username + " para " + item.getName();
		String mensaje = form.getMensaje() + MENSAJE_INTERESADO + comprador.getEmail() + "\n" + comprador.getTelf();

		Validation val = emailService.sendSimpleEmail(vendedor.getEmail(), asunto, mensaje);
		Calendar calendar = Calendar.getInstance();
		java.sql.Date fecha_contacto = new java.sql.Date(calendar.getTime().getTime());

		Contacto contacto = co.create(comprador.getId(), item.getIdvendedor(), id, fecha_contacto, form.getMensaje());
		int tipo = 0;
		if (val.isOk()) {

			codigo = "Mensaje enviado correctamente, el vendedor se pondrá en contacto contigo a traves de: "
					+ comprador.getEmail() + " o " + comprador.getTelf();

		} else {
			tipo = 1;
			codigo = "Ha habido un error en el envio del mensaje a: " + vendedor.getEmail() + " ERROR: "
					+ val.getMessage();
		}

		LOGGER.debug(codigo);
		mav.addObject("tipo", tipo);
		mav.addObject("mensaje", codigo);
		mav.addObject("vendedor", vendedor);
		return mav;
	}

	@RequestMapping(value = "/{id}/ratehistoric/")
	public ModelAndView rate(@PathVariable("id") long id, HttpServletRequest request,
			@Valid @ModelAttribute("ItemRateForm") final ItemRateForm form, final BindingResult errors) {
		ModelAndView mav = new ModelAndView("items/rate");

		Historial historial = hi.findByIdItem(id);

		User vendedor = us.findById(historial.getIdvendedor());

		mav.addObject("id", id);

		mav.addObject("vendedor", vendedor);
		return mav;
	}

	@RequestMapping(value = "/{id}/rateSucces/", method = { RequestMethod.POST })
	public ModelAndView rateSucces(@PathVariable("id") long id, HttpServletRequest request,
			@Valid @ModelAttribute("ItemRateForm") final ItemRateForm form, final BindingResult errors) {

		Historial historial = hi.findByIdItem(id);
		int stars = form.getEstrellas();

		if (stars < 0) {
			stars = 0;
		} else if (stars > 5) {
			stars = 5;
		}

		historial.setEstrellas(stars);
		historial.setValoracion(form.getValoracion());
		hi.update(historial);

		ModelAndView mav = new ModelAndView("redirect:/users/miscompras");
		return mav;
	}

}
