package org.websolverpro.quarkusfirst;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.websolverpro.quarkusfirst.repository.FilmRepository;

import java.util.Optional;

@Path("/")
public class FilmResource {

	@Inject
	FilmRepository filmRepository;
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String intro() {
		return "Bismillah, This is the home page";
	}
	
	@GET
	@Path("/helloworld")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Bismillah";
	}
	
	@GET
	@Path("/film/{filmId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFilm(short filmId) {
		Optional<Film> film = filmRepository.getFilm(filmId);
		return film.isPresent() ? film.get().getTitle() : "No fil was found" ;
	}
}

