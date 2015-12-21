package com.github.justcoke.example.helloworld.resources;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;

import com.github.justcoke.example.helloworld.services.Warehouse;

@Path("/jcr")
public class JcrResource {

	private final Warehouse warehouse;

	@Inject
	public JcrResource(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	@GET
	@Path("{var:.*}")
	@Produces
	public byte[] getContent(@PathParam("var") final String path) {
		byte[] ret = null;
		try {
			ret = warehouse.fetch(path);
		} catch (Exception e) {
			// TODO implement and register javax.ws.rs.ext.ExceptionMapper
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		if (ret == null) {
			throw new WebApplicationException(Status.NO_CONTENT);
		}
		return ret;
	}

	// looks like PUT should be used instead of POST for the repo:
	// http://stackoverflow.com/questions/630453/put-vs-post-in-rest
	@PUT
	@Path("{var:.*}")
	public Response setContent(
			@PathParam("var") final String path /*
												 * , @Context final HttpHeaders
												 * httpHeaders
												 */, @Context final HttpServletRequest request) {
		try {
			warehouse.store(path, IOUtils.toByteArray(request.getInputStream()));
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok().build();
	}
}
