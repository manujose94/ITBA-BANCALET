package ar.edu.itba.paw.rest;

import java.util.Collection;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ar.edu.itba.paw.dto.AdminAyudasDTO;
import ar.edu.itba.paw.dto.AdminDTO;
import ar.edu.itba.paw.dto.AyudaDTO;
import ar.edu.itba.paw.dto.AyudaListDTO;
import ar.edu.itba.paw.dto.MensajeDTO;
import ar.edu.itba.paw.dto.UserComprasDTO;
import ar.edu.itba.paw.dto.UserDTO;
import ar.edu.itba.paw.dto.UserListDTO;
import ar.edu.itba.paw.dto.UserVentasDTO;
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

@Path("/admin")
@Component
public class AdminRestApi extends ApiRest {

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

	@Context
	private UriInfo uriInfo;

	/*
	 * Página principal del admin
	 */
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response index() {
		long totalUsers = us.getTotalUsers();
		long totalAltaItems = is.findAllAlta().size();
		long totalItems = is.getTotalItems();
		long totalAyudas = ay.getTotalAyudas();
		return Response.ok(new AdminDTO(totalUsers, totalAltaItems, totalItems, totalAyudas)).build();
	}

	/*
	 * Obtener ayudas del admin
	 */
	@GET
	@Path("/totalAyudas")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response totalayudas() {
		long totalAyudas = ay.getTotalAyudas();
		return Response.ok(new AdminAyudasDTO(totalAyudas)).build();

	}

	/*
	 * Crear un usuario
	 */
	@POST
	@Path("/createUser")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(@Valid final AdminRegisterForm form
	/* ,@ModelAttribute final CommonsMultipartFile[] fileUpload */) {
		CommonsMultipartFile[] fileUpload = null;

		if (form == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		if (!form.getPassword().equals(form.getRepeatPassword())) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		User existe = us.findByEmail(form.getEmail());
		if (existe != null) {
			return Response.status(Status.CONFLICT).build();
		}
		existe = us.findByEmail(form.getUsername());
		if (existe != null) {
			return Response.status(Status.CONFLICT).build();
		}

		User user = ad.createUser(form.getTelf(), form.getEmail(), form.getCity(), form.getCountry(), form.getCode(),
				form.getUsername(), passwordEncoder.encode(form.getPassword()), form.getRole(), form.getDireccion(),
				form.getNumImg(), "", fileUpload, form.getLat(), form.getLon());

		return Response.ok(new UserDTO(user)).build();
	}

	/*
	 * Listar los usuarios
	 */
	@GET
	@Path("/userList")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response userList(@QueryParam("userRoles") @DefaultValue("0") final String tipoR,
			@QueryParam("estado") @DefaultValue("2") final String tipoE,
			@QueryParam("userNombre") @DefaultValue("") final String name) {

		Integer tipoRole = null;
		try {
			tipoRole = tipoR != "" ? Integer.valueOf(tipoR) : 0;
		} catch (Exception e) {
			tipoRole = 0;
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

		Integer tipoEstado = null;
		try {
			tipoEstado = tipoE != "" ? Integer.valueOf(tipoE) : 2;
		} catch (Exception e) {
			tipoEstado = 2;
		}

		Collection<User> list = ad.findUsers(name, tipoEstado, role);

		if (list == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		return Response.ok(new UserListDTO(list)).build();
	}

	/*
	 * Pagina de perfil de usuario
	 */
	@GET
	@Path("/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response index(@PathParam("id") final long id) {
		User userIndex = ad.getUserById(id);
		if (userIndex != null) {
			return Response.ok(new UserDTO(userIndex)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}

	}

	/*
	 * Dar de baja un usuario
	 */
	@PUT
	@Path("/{id}/bajaUser")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response bajaUser(@PathParam("id") final long id) {
		User userIndex = ad.bajaUser(id);
		if (userIndex != null) {
			return Response.ok(new UserDTO(userIndex)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	/*
	 * Dar de alta un usuario
	 */
	@PUT
	@Path("/{id}/altaUser")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response altaUser(@PathParam("id") final long id) {
		User userIndex = ad.altaUser(id);
		if (userIndex != null) {
			return Response.ok(new UserDTO(userIndex)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	/*
	 * Pagina de editar perfil de usuario
	 */
	@PUT
	@Path("/{id}/editUser")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editUsr(@PathParam("id") final long id, @Valid final AdminUserEditForm form
	/* ,@ModelAttribute final CommonsMultipartFile[] fileUpload */) {

		if (form == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		CommonsMultipartFile[] fileUpload = null;
		User userIndex = ad.getUserById(id);

		if (userIndex != null) {
			User user = ad.updateUser(id, form.getTelf(), form.getCity(), form.getCountry(), form.getCode(),
					form.getDireccion(), form.getNumImg(), null, fileUpload, form.getLat(), form.getLon());
			return Response.ok(new UserDTO(user)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Path("/{id}/editPass")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response editPassAccept(@PathParam("id") long id, @Valid final UserEditPassForm form) {

		if (form == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		if (form.getPassword().equals(form.getRepeatPassword())) {
			// GET ACTUAL SESSION USER
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = us.findUserSessionByUsername(principal);
			User userIndex = us.findById(id);
			if (userIndex != null) {
				boolean equals = user.equals(userIndex);// si el admin cambia la pass del user
				userIndex = ad.updateUserPass(userIndex, form.getPassword(), equals);
				return Response.status(Status.ACCEPTED).build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		}
		return Response.status(Response.Status.CONFLICT).build();

	}

	/*
	 * Pagina del historial del usuario
	 */
	@GET
	@Path("/{id}/ventas")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response historialventas(@PathParam("id") final long id) {

		User userIndex = us.findById(id);
		if (userIndex == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Collection<Item> itemsvendidos = is.findByVendedorId(userIndex.getUserId(), Item.BAJA);
		Collection<Historial> listaventas = hi.findByidVendedor(userIndex.getUserId());
		Collection<User> listcompradores = us.findByidVendedorconHistorial(userIndex.getUserId());
		if (listaventas == null || listcompradores == null || itemsvendidos == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.ok(new UserVentasDTO(userIndex, itemsvendidos, listaventas, listcompradores)).build();
	}

	@GET
	@Path("/{id}/compras")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response historialCompras(@PathParam("id") final long id) {

		User userIndex = us.findById(id);
		if (userIndex == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Collection<Historial> listacompras = hi.findByidComprador(userIndex.getUserId());
		Collection<User> listvendedores = us.findByidCompradorconHistorial(userIndex.getUserId());
		Collection<Item> itemscomprados = is.findByCompradorId(userIndex.getUserId(), Item.BAJA);
		if (listacompras == null || listvendedores == null || itemscomprados == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.ok(new UserComprasDTO(userIndex, listacompras, listvendedores, itemscomprados)).build();
	}

	@GET
	@Path("/issues")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response issues(@QueryParam("tipo") @DefaultValue("0") final String tipoS,
			@QueryParam("asunto") @DefaultValue("") final String asunto) {

		Integer tipo = 0;
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

		Collection<Ayuda> listayudas = ad.findAyudas(asunto, tipo, Ayuda.ALTA);
		if (listayudas == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		return Response.ok(new AyudaListDTO(listayudas)).build();
	}

	@GET
	@Path("/archivedIssues")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response archivedissues(@QueryParam("tipo") @DefaultValue("0") final String tipoS,
			@QueryParam("asunto") @DefaultValue("") final String asunto) {

		Integer tipo = 0;

		try {
			tipo = tipoS != "" ? Integer.valueOf(tipoS) : 0;
		} catch (Exception e) {
			tipo = 0;
		}
		Collection<Ayuda> listayudas = ad.findAyudas(asunto, tipo, Ayuda.BAJA);

		if (listayudas == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		return Response.ok(new AyudaListDTO(listayudas)).build();
	}

	/*
	 * Pagina de una issue
	 */
	@GET
	@Path("/issues/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response issueIndex(@PathParam("id") final long id) {

		Ayuda issue = ay.findById(id);
		if (issue != null) {
			return Response.ok(new AyudaDTO(issue)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	/*
	 * Pagina para eliminar una issue (solo si estado es alta)
	 */
	@DELETE
	@Path("/issues/{id}/delete")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response issueDelete(@PathParam("id") final long id) {

		Ayuda issue = ay.findById(id);
		if (issue != null) {
			if (issue.getEstado() == Ayuda.ALTA) {
				int res = ay.delete(id);
				if (res > 0) {
					return Response.status(Status.ACCEPTED).build();
				} else {
					// no se ha modificado
					return Response.status(Status.NOT_MODIFIED).build();
				}
			}
			// si esta baja no se puede eliminar
			return Response.status(Status.BAD_REQUEST).build();
		}
		// no existe
		return Response.status(Status.NOT_FOUND).build();
	}

	/*
	 * Confirmacion de archivacion (solo si estado es alta)
	 */
	@PUT
	@Path("/issues/{id}/archive")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	@Consumes(MediaType.APPLICATION_JSON)
	public Response confirmArchive(@Valid final AdminHelpForm form, @PathParam("id") final long id) {

		if (form == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		Ayuda issue = ay.findById(id);

		if (issue != null) {
			if (issue.getEstado() == Ayuda.ALTA) {
				Validation val = ad.archiveIssue(issue, form.getInforme());
				String codigo = "";
				Object[] parameter = { issue.getEmail() };
				if (val.isOk()) {
					codigo = messageSource.getMessage("admin.report_succes_send", parameter,
							LocaleContextHolder.getLocale());

				} else {
					codigo = messageSource.getMessage("admin.report_error_send", parameter,
							LocaleContextHolder.getLocale());
				}
				return Response.ok(new MensajeDTO(codigo)).build();
			}
			// si esta baja no se puede editar
			return Response.status(Status.BAD_REQUEST).build();
		}
		// no existe
		return Response.status(Status.NOT_FOUND).build();
	}

}
