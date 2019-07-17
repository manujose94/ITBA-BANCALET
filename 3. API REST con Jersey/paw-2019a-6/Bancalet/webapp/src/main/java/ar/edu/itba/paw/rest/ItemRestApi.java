package ar.edu.itba.paw.rest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ar.edu.itba.paw.dto.ContactoDTO;
import ar.edu.itba.paw.dto.ContactoListDTO;
import ar.edu.itba.paw.dto.ItemCreateDTO;
import ar.edu.itba.paw.dto.ItemDTO;
import ar.edu.itba.paw.dto.ItemListDTO;
import ar.edu.itba.paw.dto.ItemListFiltroDTO;
import ar.edu.itba.paw.dto.MensajeDTO;
import ar.edu.itba.paw.form.ContactForm;
import ar.edu.itba.paw.form.ItemRateForm;
import ar.edu.itba.paw.form.ItemRegisterForm;
import ar.edu.itba.paw.interfaces.services.AdminService;
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

@Path("/items")
@Component
public class ItemRestApi extends ApiRest {
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
	private ItemImageService imageItemService;

	@Autowired
	private MessageSource messageSource;

	@Context
	private UriInfo uriInfo;

	@GET
	@Path("/contactosItem/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response contactosItem(@PathParam("id") long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Item item = is.findById(id);
		if (item == null)
			return Response.status(Status.NOT_FOUND).build();
		User seller = us.findById(item.getIdVendedor());
		if (!seller.equals(user)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Contacto contactado = co.findContactoByidCompradorIdItem(user.getUserId(), id, item.getIdVendedor());
		if (contactado == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(new ContactoDTO(contactado)).build();
	}

	@GET
	@Path("/contactosMyItem/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response contactosMyItem(@PathParam("id") long id) {
		Item item = is.findById(id);
		if (item == null)
			return Response.status(Status.NOT_FOUND).build();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		User seller = us.findById(item.getIdVendedor());
		if (!seller.equals(user)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Collection<Contacto> contactos = co.findByIditem(id);
		if (contactos == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(new ContactoListDTO(contactos)).build();
	}

	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response list(@QueryParam("name") @DefaultValue("") final String name,
			@QueryParam("tipo") @DefaultValue("") final String itemTipo, @QueryParam("slider") final String slider,
			@QueryParam("minSlaider") final String minSlaider, @QueryParam("maxSlaider") final String maxSlaider,
			@QueryParam("itemTipoCaducidad") @DefaultValue("") final String itemTipoCaducidad,
			@QueryParam("fecha_caducidad") @DefaultValue("") final String fecha) {

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

		/*
		 * MIN Y MAX TOTALES
		 */
		max = max == min ? 1.0 : max;

		/**
		 * OBTENER ITEMS
		 */
		Exception ex = null;
		Date date;
		Collection<Item> items;
		try {
			// DateFormat.
			date = Date.valueOf(fecha);
			items = is.getItems(slider, tipo, name, minimoSeleccionado, maximoSeleccionado, date, tipoCaducidad);
		} catch (Exception e) {
			ex = e;
			Calendar calendar = Calendar.getInstance();
			date = new java.sql.Date(calendar.getTime().getTime());
			items = is.getItems(slider, tipo, name, minimoSeleccionado, maximoSeleccionado);
		}

		if (ex == null) {
			return Response.ok(new ItemListFiltroDTO(minimoSeleccionado, maximoSeleccionado, min, max, date, 0, name,
					tipo, tipoCaducidad, items)).build();
		} else {
			return Response.ok(new ItemListFiltroDTO(minimoSeleccionado, maximoSeleccionado, min, max, date, 1, name,
					tipo, tipoCaducidad, items)).build();
		}
	}

	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response index(@PathParam("id") final long id) {// depurar
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		Item item = is.getItem(id);
		if (item == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		boolean propietario = false;
		if (user != null) {
			propietario = user.getUserId() == item.getIdVendedor() ? true : false;
		}
		User seller = us.findById(item.getIdVendedor());
		int rate = (int) seller.getRate();

		List<String> imagenes = imageItemService.getImagesItemsEncode(imageItemService.findAllImagesItem(id));

		return Response
				.ok(new ItemDTO(imagenes == null ? new ArrayList<String>() : imagenes, rate, propietario, item, seller))
				.build();
	}

	@DELETE
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/delete/{id}")
	public Response delete(@PathParam("id") final long id) {
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
						return Response.status(Status.FORBIDDEN).build();
					}
					is.delete(id);
				}
				return Response.status(Status.ACCEPTED).build();
			} else {
				return Response.status(Status.FORBIDDEN).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();

	}

	@PUT
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/baja/{id}")
	public Response baja(@PathParam("id") final long id, @QueryParam("comprador") String purchaserId) {

		long idcomprador = -1;
		try {
			idcomprador = Long.valueOf(purchaserId);
		} catch (Exception e) {
			return Response.status(Status.FORBIDDEN).build();
		}
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		Item item = is.findById(id);
		if (item != null) {
			if (user != null) {
				User seller = us.findById(item.getIdVendedor());
				if (!seller.equals(user)) {
					return Response.status(Status.FORBIDDEN).build();
				}
			} else {
				return Response.status(Status.FORBIDDEN).build();
			}
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
		is.bajaItem(id, idcomprador);

		return Response.status(Status.ACCEPTED).build();
	}

	@POST
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/additem")
	public Response additem(@Valid final ItemRegisterForm form
	/* final @RequestParam CommonsMultipartFile[] fileUploadMultiple */) {
		CommonsMultipartFile[] fileUploadMultiple = null;
		// USER ACTUAL SESSION
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		int tipo = form.getTipo();

		String fecha = form.getFecha_caducidad();
		Date date = Date.valueOf(fecha);

		Item item = is.createItem(user, user.getUserId(), form.getName(), tipo, form.getPrice(), form.getDescription(),
				date, fileUploadMultiple);
		return Response.ok(new ItemCreateDTO(item)).build();
	}

	@PUT
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Path("/update/{id}")
	public Response update(@PathParam("id") final long id,
			@Valid final ItemRegisterForm form /*
												 * final @RequestParam CommonsMultipartFile[] fileUploadMultiple
												 */) {
		CommonsMultipartFile[] fileUploadMultiple = null;
		// USER ACTUAL SESSION
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		User seller = null;
		Item item = is.findById(id);
		if (item != null) {
			seller = us.findById(item.getIdVendedor());
			if (!seller.equals(user)) {
				return Response.status(Status.FORBIDDEN).build();
			}
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}

		String fecha = form.getFecha_caducidad();
		Date date = Date.valueOf(fecha);
		int tipo = form.getTipo();
		is.updateItem(id, form.getName(), tipo, form.getPrice(), form.getDescription(), date, fileUploadMultiple);

		Item itemUpdated = is.findById(id);

		return Response
				.accepted(new ItemDTO(imageItemService.getImagesItemsEncode(imageItemService.findAllImagesItem(id)),
						(int) user.getRate(), true, itemUpdated, seller))
				.build();
	}

	@DELETE
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/delitemimages/{id}")
	public Response delitemimages(@PathParam("id") final long itemId) {
		Item item = is.findById(itemId);
		if (item == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		// USER ACTUAL SESSION
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		User propietario = us.findById(item.getIdVendedor());
		if (!propietario.equals(user)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		is.deleteItemImageTrans(itemId);
		return Response.status(Status.ACCEPTED).build();
	}

	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/myitems")
	public Response myitems() {
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		long userid = user.getUserId();
		Collection<Item> items = is.findByVendedorId(userid, Item.ALTA);
		return Response.ok(new ItemListDTO(items)).build();
	}

	@PUT
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Path("/sendto/{id}")
	public Response send(@PathParam("id") final long id, @Valid final ContactForm form) {

		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User comprador = us.findUserSessionByUsername(principal);
		if (comprador == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Item item = is.findById(id);
		if (item == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		long idvendedor = item.getIdVendedor();
		User vendedor = us.findById(idvendedor);
		if (comprador.equals(vendedor)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String codigo;
		Contacto contactado = co.findContactoByidCompradorIdItem(comprador.getUserId(), id, item.getIdVendedor());
		if (contactado == null) {
			Validation val = co.createContact(item, comprador, vendedor, form.getMensaje());
			if (val.isOk()) {
				codigo = messageSource.getMessage("items.succes_send", null, LocaleContextHolder.getLocale())
						+ comprador.getEmail() + " | " + comprador.getTelf();
			} else {
				codigo = messageSource.getMessage("items.error_send", null, LocaleContextHolder.getLocale()) + " "
						+ vendedor.getEmail() + " , ERROR: " + val.getMessage();
			}
		} else {
			codigo = messageSource.getMessage("items.allready_contacted", null, LocaleContextHolder.getLocale())
					+ comprador.getEmail() + " | " + comprador.getTelf();
		}
		return Response.ok(new MensajeDTO(codigo)).build();
	}

	@PUT
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Path("/rate/{id}")
	public Response rateSucces(@PathParam("id") long id, @Valid final ItemRateForm form) {

		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User comprador = us.findUserSessionByUsername(principal);
		if (comprador == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		int stars = 0;
		String valoracion = "";
		try {
			valoracion = form.getValoracion();
			stars = form.getEstrellas();
		} catch (Exception e) {
			return Response.status(Status.CONFLICT).build();
		}

		if (valoracion.equals("")) {
			return Response.status(Status.CONFLICT).build();
		}

		if (stars < 0) {
			stars = 0;
		} else if (stars > 5) {
			stars = 5;
		}
		Historial historial = hi.findByIdItem(id);
		if (historial == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		User vendedor = us.findById(historial.getIdVendedor());
		if (comprador.equals(vendedor)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		if (comprador.getUserId() != historial.getIdComprador()) {
			return Response.status(Status.FORBIDDEN).build();
		}
		is.rateItem(id, stars, valoracion);
		return Response.status(Status.ACCEPTED).build();
	}
}
