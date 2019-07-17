package ar.edu.itba.paw.rest;

import java.net.URI;

import javax.json.Json;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public abstract class ApiRest {
	URI buildBaseURI(UriInfo uriInfo) {
		return URI
				.create(String.valueOf(uriInfo.getBaseUri()) + UriBuilder.fromResource(this.getClass()).build() + "/");
	}

	String errorMessageToJSON(String message) {
		return Json.createObjectBuilder().add("errors", message).build().toString();
	}
}
