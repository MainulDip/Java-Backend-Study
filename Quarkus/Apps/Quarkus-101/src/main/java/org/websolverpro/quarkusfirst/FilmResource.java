package org.websolverpro.quarkusfirst;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.websolverpro.quarkusfirst.repository.FilmRepository;

import java.util.Optional;
import java.util.stream.Collectors;

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
	
	@GET
	@Path("/pagedFilms/{page}/{minLength}")
	@Produces(MediaType.TEXT_PLAIN)
	public String paged(long page, short minLength) {
		return filmRepository.paged(page, minLength)
				.map(f -> String.format("%s (%d min)", f.getTitle(), f.getLength()))
				.collect(Collectors.joining("\n"));
	}


	@GET
	@Path("/pagedFilms/actors/{startsWith}")
	@Produces(MediaType.TEXT_PLAIN)
	public String actors1(String startsWith) {
		return filmRepository.actors1(startsWith)
				.map(f -> String.format("%s (%d min): %s", f.getTitle(), f.getLength(), f.getActors().stream().map(a -> String.format("%s %s", a.getFirstName(), a.getLastName())).collect(Collectors.joining(", "))))
				.collect(Collectors.joining("\n"));
	}

	@GET
	@Path("/pagedFilms/actors/{startsWith}/{minLength}")
	@Produces(MediaType.TEXT_PLAIN)
	public String actors2(String startsWith, short minLength) {
		return filmRepository.actors2(startsWith, minLength)
				.map(f -> String.format("%s (%d min): %s", f.getTitle(), f.getLength(), f.getActors().stream().map(a -> String.format("%s %s", a.getFirstName(), a.getLastName())).collect(Collectors.joining(", "))))
				.collect(Collectors.joining("\n"));
	}

	@GET
	@Path("/update/{minLength}/{rentalRate}")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateRentalRate(short minLength, Float rentalRate) {
		filmRepository.updateRentalRate(minLength, rentalRate);
		return filmRepository.getFilms(minLength)
				.map(f -> String.format("%s (%d min): $%f", f.getTitle(), f.getLength(), f.getRentalRate()))
				.collect(Collectors.joining("\n"));
	}
}

