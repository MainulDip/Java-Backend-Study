package org.websolverpro.quarkusfirst.repository;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import org.websolverpro.quarkusfirst.Film;
import org.websolverpro.quarkusfirst.Film$;

import com.speedment.jpastreamer.application.JPAStreamer;

import jakarta.inject.Inject;

@ApplicationScoped
public class FilmRepository {
	@Inject
	JPAStreamer jpaStreamer;
	
	public Optional<Film> getFilm(short filmId) {
        return jpaStreamer.stream(Film.class)
				.filter(Film$.filmId.equal(filmId))
				.findFirst();
	}
	
}

// Now, try to place generated-source/annotation into the `src` directory by configuring `jpastreamer plugin`