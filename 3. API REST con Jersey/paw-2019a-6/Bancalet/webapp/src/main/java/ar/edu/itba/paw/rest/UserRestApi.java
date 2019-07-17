package ar.edu.itba.paw.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ar.edu.itba.paw.dto.BooleanDTO;
import ar.edu.itba.paw.dto.ItemListDTO;
import ar.edu.itba.paw.dto.ListUsersItemsDTO;
import ar.edu.itba.paw.dto.LongDTO;
import ar.edu.itba.paw.dto.MensajeDTO;
import ar.edu.itba.paw.dto.UserComprasDTO;
import ar.edu.itba.paw.dto.UserContactosDTO;
import ar.edu.itba.paw.dto.UserDTO;
import ar.edu.itba.paw.dto.UserListDTO;
import ar.edu.itba.paw.dto.UserVentasDTO;
import ar.edu.itba.paw.form.ContactAdminForm;
import ar.edu.itba.paw.form.UserEditForm;
import ar.edu.itba.paw.form.UserEditPassForm;
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

@Path("/users")
@Component
public class UserRestApi extends ApiRest {

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

	@Context
	private UriInfo uriInfo;

	@GET
	@Path("/sizeMsg/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response sizeMsg(@PathParam("id") long id) {
		User userIndex = us.getUserById(id);
		if (userIndex != null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		if (user.equals(userIndex)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		return Response.ok(new LongDTO(co.getUserContacts(id))).build();
	}

	@GET
	@Path("/sizeRate/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response sizeRate(@PathParam("id") long id) {
		return Response.ok(new LongDTO(hi.getUserHistorialNoRate(id))).build();
	}

	@GET
	@Path("/listUsers")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response listUsers() {
		final Collection<User> allUsers = us.findByuserRole(User.USER);
		if (allUsers != null)
			return Response.ok(new UserListDTO(allUsers)).build();
		else
			return Response.status(Status.NO_CONTENT).build();
	}

	@GET
	@Path("/getMaxVendor/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getMaxVendor(@PathParam("id") long userId) {
		return Response.ok(new BooleanDTO(us.getMaxVendor(us.findById(userId)))).build();
	}

	@GET
	@Path("/getMaxBuyer/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getMaxBuyer(@PathParam("id") long userId) {
		return Response.ok(new BooleanDTO(us.getMaxBuyer(us.findById(userId)))).build();
	}

	@GET
	@Path("/getMaxRated/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getMaxRated(@PathParam("id") long userId) {
		return Response.ok(new BooleanDTO(us.getMaxRated(us.findById(userId)))).build();
	}

	@GET
	@Path("/getUserTotalSales/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getUserTotalSales(@PathParam("id") long userId) {
		return Response.ok(new LongDTO(us.getUserTotalSales(us.findById(userId)))).build();
	}

	@GET
	@Path("/getRate/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getRate(@PathParam("id") long userId) {
		return Response.ok(new LongDTO((long) us.findById(userId).getRate())).build();
	}

	@GET
	@Path("/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response index(@PathParam("id") final long id) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		User userIndex = us.getUserById(id);
		if (userIndex != null) {
			if (userIndex.getRole().equals(User.ADMIN)) {
				return Response.ok(new UserDTO(userIndex)).build();
			} else {
				if (userIndex.getUserId() != user.getUserId()) {
					return Response.status(Status.FORBIDDEN).build();
				}
			}
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(new UserDTO(userIndex)).build();
	}

	@GET
	@Path("/login/{username}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getUser(@PathParam("username") final String username) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		User userIndex = us.findByUsername(username);
		if (user != null) {
			if (userIndex != null) {
				if (userIndex.getUserId() != user.getUserId()) {
					return Response.status(Status.FORBIDDEN).build();
				}
				return Response.ok(new UserDTO(userIndex)).build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		}
		return Response.status(Status.FORBIDDEN).build();
	}

	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/listUsersItems")
	public Response listUsersItems(@QueryParam("rangeKmSlider") final String rangeKmSlider) {

		Integer rangeKm = rangeKmSlider == null ? 1287 : Integer.valueOf(rangeKmSlider);
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		Collection<User> listUser = us.findByUserNear(user.getLat(), user.getLon(), rangeKm);
		if (listUser == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		TreeMap<Long, List<Item>> treeMap = is.getMapfindByItemNear(user.getLat(), user.getLon(), rangeKm, listUser);
		TreeMap<Long, TreeMap<Long, Item>> treeMapListaItemsIds = new TreeMap<Long, TreeMap<Long, Item>>();
		for (Entry<Long, List<Item>> entry : treeMap.entrySet()) {
			Long key = entry.getKey();
			List<Item> value = entry.getValue();
			TreeMap<Long, Item> res = new TreeMap<Long, Item>();
			for (Iterator<Item> iterator = value.iterator(); iterator.hasNext();) {
				Item item = iterator.next();
				res.put(item.getItemId(), item);
			}
			treeMapListaItemsIds.put(key, res);

		}
		return Response.ok(new ListUsersItemsDTO(treeMapListaItemsIds, listUser, rangeKm == null ? 1187 : rangeKm))
				.build();
	}

	@PUT
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/edit")
	public Response edit(@Valid final UserEditForm form
	/* @QueryParam("fileUpload") final CommonsMultipartFile[] fileUpload */) {
		CommonsMultipartFile[] fileUpload = null;
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		us.updateUser(user, form.getTelf(), form.getCity(), form.getCountry(), form.getCode(), form.getDireccion(),
				form.getTipo(), "", form.getLat(), form.getLon(), fileUpload);
		return Response.ok(new UserDTO(user)).build();
	}

	@PUT
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/editPass")
	public Response editPass(@Valid final UserEditPassForm form) {
		// GET ACTUAL SESSION USER
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		if (!form.getPassword().equals(form.getRepeatPassword()))
			return Response.status(Response.Status.BAD_REQUEST).build();
		us.updateUserPass(user, passwordEncoder.encode(form.getPassword()));
		return Response.status(Status.ACCEPTED).build();
	}

	/*
	 * Pagina del historial del usuario
	 */
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/misventas")
	public Response historialventas() {
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);

		Collection<Item> itemsvendidos = is.findByVendedorId(user.getUserId(), Item.BAJA);
		Collection<Historial> listaventas = hi.findByidVendedor(user.getUserId());
		Collection<User> listcompradores = us.findByidVendedorconHistorial(user.getUserId());

		return Response.ok(new UserVentasDTO(user, itemsvendidos, listaventas, listcompradores)).build();
	}

	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/miscompras")
	public Response historialCompras() {
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);

		Collection<Historial> listacompras = hi.findByidComprador(user.getUserId());
		Collection<User> listvendedores = us.findByidCompradorconHistorial(user.getUserId());
		Collection<Item> itemscomprados = is.findByCompradorId(user.getUserId(), Item.BAJA);
		return Response.ok(new UserComprasDTO(user, listacompras, listvendedores, itemscomprados)).build();
	}

	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/miscontactosr")
	public Response misContactosR() {
		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);

		co.readMensajes(user.getUserId());
		Collection<Contacto> contactos = co.findByidVendedor(user.getUserId());
		List<User> users = new ArrayList<User>();
		for (Contacto contact : contactos) {
			users.add(us.findById(contact.getIdComprador()));
		}
		Collection<User> listcontactados = users;
		Collection<Item> items = is.findByVendedorIdContactados(user.getUserId(), true);
		int size = 0;
		if (contactos != null) {
			size = contactos.size();
		}

		return Response.ok(new UserContactosDTO(user, listcontactados, contactos, size, items)).build();
	}

	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/miscontactose")
	public Response misContactosE() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		Collection<Contacto> contactos = co.findByidComprador(user.getUserId());
		List<User> users = new ArrayList<User>();
		for (Contacto contact : contactos) {
			users.add(us.findById(contact.getIdVendedor()));
		}
		Collection<User> listcontactados = users;
		Collection<Item> items = is.findByCompradorIdContactados(user.getUserId(), true);

		int size = 0;
		if (contactos != null) {

			size = contactos.size();
		}

		return Response.ok(new UserContactosDTO(user, listcontactados, contactos, size, items)).build();

	}

	@POST
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/help")
	public Response contactSucces(@Valid final ContactAdminForm form) {

		// OBTENER USUARIO LOGUEADO ACTUAL
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);
		String name = user.getUsername();
		String email = user.getEmail();
		Validation val = ay.createAyuda(form.getSubject(), email, name, form.getMensaje());
		Object[] parameterS = { name, email };
		Object[] parameterM = { name };
		String codigo;
		if (val.isOk()) {
			codigo = messageSource.getMessage("admin.succes_send", parameterS, LocaleContextHolder.getLocale());
		} else {
			codigo = messageSource.getMessage("admin.error_send", parameterM, LocaleContextHolder.getLocale());
		}

		return Response.ok(new MensajeDTO(codigo)).build();
	}

	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/missugerencias")
	public Response misSugerencias() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);

		Collection<Item> items = us.getSugerencias(user.getUserId());

		if (items == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(new ItemListDTO(items)).build();
	}

}
