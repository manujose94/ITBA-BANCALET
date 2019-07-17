package ar.edu.itba.paw.rest;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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

import ar.edu.itba.paw.dto.MensajeDTO;
import ar.edu.itba.paw.dto.UserDTO;
import ar.edu.itba.paw.form.ContactAdminForm;
import ar.edu.itba.paw.form.UserRegisterForm;
import ar.edu.itba.paw.interfaces.services.AyudaService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.User;

@Path("")
@Component
public class MainPageRestApi extends ApiRest {

	@Autowired
	private UserService us;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AyudaService ay;

	@Autowired
	private MessageSource messageSource;

	@Context
	private UriInfo uriInfo;

	/*
	 * Pagina principal
	 */
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response onLoginSuccess() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);

		if (user == null) {
			return Response.status(Status.FORBIDDEN).build();
		}
		return Response.ok(new UserDTO(user)).build();

	}

	@PUT
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/confirmregister/{id}")
	public Response confirmregister(@PathParam("id") final long id) {
		User user = us.findById(id);
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (user.getEstado() == User.ALTA) {
			return Response.status(Status.FORBIDDEN).build();
		}
		us.confirmEmail(user);
		return Response.status(Status.ACCEPTED).build();
	}

	@POST
	@Path("/register")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(@Valid final UserRegisterForm userForm) {
		CommonsMultipartFile[] fileUpload = null;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userloged = us.findUserSessionByUsername(principal);

		try {
			userloged.getUsername();
			return Response.status(Status.FORBIDDEN).build();
		} catch (Exception e) {

		}

		if (userForm == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		if (!userForm.getPassword().equals(userForm.getRepeatPassword()))
			return Response.status(Response.Status.BAD_REQUEST).build();

		User existe = us.findByEmail(userForm.getEmail());
		if (existe != null) {
			return Response.status(Status.CONFLICT).build();
		}
		User existe2 = us.findByUsername(userForm.getUsername());
		if (existe2 != null) {
			return Response.status(Status.CONFLICT).build();
		}
		if (existe2 == null && existe == null) {
			final User user = us.createUser(userForm.getTelf(), userForm.getEmail(), userForm.getCity(),
					userForm.getCountry(), userForm.getCode(), userForm.getUsername(),
					passwordEncoder.encode(userForm.getPassword()), userForm.getDireccion(), userForm.getNumImg(),
					userForm.getUrlImg(), fileUpload, userForm.getLat(), userForm.getLon());
			Object[] parametersM = { user.getUsername() };
			String mensaje = messageSource.getMessage("user.register_message_mail_popup", parametersM,
					LocaleContextHolder.getLocale());
			return Response.ok(new MensajeDTO(mensaje)).build();
		} else {
			return Response.status(Status.CONFLICT).build();
		}
	}

	@POST
	@Path("/contact")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response contactSucces(@Valid final ContactAdminForm form) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = us.findUserSessionByUsername(principal);

		if (user != null) {
			return Response.status(Status.FORBIDDEN).build();
		}

		if (form == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		String subject = form.getSubject();
		String name = form.getName();
		String email = form.getEmail();
		String mensaje = form.getMensaje();

		Object[] parameterS = { name, email };
		Object[] parameterM = { name };

		Validation val = ay.createAyuda(subject, email, name, mensaje);

		String codigo;
		if (val.isOk()) {
			codigo = messageSource.getMessage("admin.succes_send", parameterS, LocaleContextHolder.getLocale());

		} else {
			codigo = messageSource.getMessage("admin.error_send", parameterM, LocaleContextHolder.getLocale());
		}
		return Response.ok(new MensajeDTO(codigo)).build();
	}

	@GET
	@Path("/error/403")
	@Produces(value = { MediaType.APPLICATION_JSON, })
	public Response error403() {

		return Response.status(Status.FORBIDDEN).build();
	}
}
