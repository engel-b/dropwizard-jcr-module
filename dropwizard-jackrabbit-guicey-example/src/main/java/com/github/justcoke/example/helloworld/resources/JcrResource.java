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

import com.github.justcoke.example.helloworld.services.JcrDAO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("/jcr")
@Path("/jcr")
public class JcrResource {

	@Inject
	private JcrDAO jcrDao;

	@GET
	@Path("/create")
	@ApiOperation("Create sample data")
	public Response create() {
		try {
			final String create = jcrDao.createData();
			return Response.ok().entity(create).build();
		} catch (final Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/getall")
	@ApiOperation("List all entities")
	public Response getAll() {
		try {
			final String value = jcrDao.getData();
			return Response.ok().entity(value).build();
		} catch (final Exception e) {
			return Response.status(404).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("{var:.*}")
	@Produces
	@ApiOperation("Sample endpoint")
	public byte[] getContent(@PathParam("var") final String path) {
		byte[] ret = null;
		try {
			ret = jcrDao.fetch(path);
		} catch (final Exception e) {
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
	@ApiOperation("Sample endpoint 2")
	public Response setContent(@PathParam("var") final String path /*
																	 * , @Context final HttpHeaders httpHeaders
																	 */, @Context final HttpServletRequest request) {
		try {
			jcrDao.store(path, IOUtils.toByteArray(request.getInputStream()));
		} catch (final Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok().build();
	}
}
