package org.websolverpro.quarkusfirst.repository;

import java.util.Optional;
import java.util.stream.Stream;

import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.websolverpro.quarkusfirst.Film;
import org.websolverpro.quarkusfirst.Film$;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;

import jakarta.inject.Inject;

@ApplicationScoped
public class FilmRepository {
	@Inject
	JPAStreamer jpaStreamer;
	
	private static final int PAGE_SIZE = 20;
	
	public Optional<Film> getFilm(short filmId) {
        return jpaStreamer.stream(Film.class)
				.filter(Film$.filmId.equal(filmId))
				.findFirst();
	}
	
//	public Stream<Film> paged(long page, short minLength) {
//		Stream<Film> films = jpaStreamer.stream(Film.class)
//				.filter(Film$.length.greaterThan(minLength))
//				.sorted(Film$.length)
//				.skip(page * PAGE_SIZE)
//				.limit(PAGE_SIZE);
//		
//		return films;
//	}
	
	// making query more specific without asking for all fields to the database by Hibernate
	// also, for this to work, we need a constructor with these fields inside our entity (Film Entity)
	public Stream<Film> paged(long page, short minLength) {
		Stream<Film> films = jpaStreamer.stream(Projection.select(Film$.filmId, Film$.title, Film$.length))
				.filter(Film$.length.greaterThan(minLength))
				.sorted(Film$.length)
				.skip(page * PAGE_SIZE)
				.limit(PAGE_SIZE);
		
		return films;
	}

	/**
	 * Return Those Films, of which Rental Rate is Updated
	 */
	public Stream<Film> getFilms(short minLength) {

        return jpaStreamer.stream(Film.class)
				.filter(Film$.length.greaterThan(minLength))
				.sorted(Film$.length);
	}


	/**
	 * Join
	 */
	public Stream<Film> actors1(String startsWith) {
		final StreamConfiguration<Film> sc = StreamConfiguration.of(Film.class).joining(Film$.actors);
		return jpaStreamer.stream(sc)
				.filter(Film$.title.startsWith(startsWith))
				.sorted(Film$.length.reversed());
	}

	/**
	 * Join with multiple predicates
	 */
	public Stream<Film> actors2(String startsWith, short minLength) {
		final StreamConfiguration<Film> sc = StreamConfiguration.of(Film.class).joining(Film$.actors);
		return jpaStreamer.stream(sc)
				.filter(Film$.title.startsWith(startsWith).and(Film$.length.greaterThan(minLength)))
				.sorted(Film$.length.reversed());
	}

	/**
	 * Updating DB With New Rental Rates
	 */
	@Transactional
	public void updateRentalRate(short minLength, Float rentalRate) {
		jpaStreamer.stream(Film.class)
				.filter(Film$.length.greaterThan(minLength))
				.forEach( f -> f.setRentalRate(rentalRate));
	}
}

