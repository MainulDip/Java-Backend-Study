package org.websolverpro.quarkusfirst;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class FilmResource {
	@GET
	@Path("/helloworld")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Bismillah";
	}
}

