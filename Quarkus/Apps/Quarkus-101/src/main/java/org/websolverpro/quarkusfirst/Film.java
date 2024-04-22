package org.websolverpro.quarkusfirst;

import java.io.Serializable;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Timestamp;
import java.sql.Types;


/**
 * The persistent class for the film database table.
 * 
 */
@Entity
@Table(name="film", schema = "sakila")
@NamedQuery(name="Film.findAll", query="SELECT f FROM Film f")
public class Film implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="film_id")
	private Short filmId;

	@Lob
	@JdbcTypeCode(Types.LONGVARCHAR)
	private String description;

	@Column(name="language_id")
	private Short languageId;

	@Column(name="last_update")
	private Timestamp lastUpdate;

	@JdbcTypeCode(Types.SMALLINT)
	private int length;

	@Column(name="original_language_id")
	@JdbcTypeCode(Types.SMALLINT)
	private int originalLanguageId;

	@Column(name="rating", columnDefinition = "enum('G', 'PG', 'PG-13', 'R', 'NC-17')")
	private Object rating;

	@Temporal(TemporalType.DATE)
	@Column(name="release_year")
	private Date releaseYear;

	@Column(name="rental_duration")
	@JdbcTypeCode(Types.SMALLINT)
	private int rentalDuration;

	@Column(name="rental_rate")
	private BigDecimal rentalRate;

	@Column(name="replacement_cost")
	private BigDecimal replacementCost;

	@Column(name="special_features", columnDefinition = "set('Trailers','Commentaries','Deleted Scenes','Behind the Scenes')")
	private Object specialFeatures;

	private String title;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "film_actor",
				joinColumns = { @JoinColumn(name = "film_id")}, 
				inverseJoinColumns = { @JoinColumn(name="actor_id")}
	)
	private List<Actor> actors = new ArrayList<>();

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	public Film() {
	}

	public int getFilmId() {
		return this.filmId;
	}

	public void setFilmId(Short filmId) {
		this.filmId = filmId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(Short languageId) {
		this.languageId = languageId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getOriginalLanguageId() {
		return this.originalLanguageId;
	}

	public void setOriginalLanguageId(int originalLanguageId) {
		this.originalLanguageId = originalLanguageId;
	}

	public Object getRating() {
		return this.rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Date getReleaseYear() {
		return this.releaseYear;
	}

	public void setReleaseYear(Date releaseYear) {
		this.releaseYear = releaseYear;
	}

	public int getRentalDuration() {
		return this.rentalDuration;
	}

	public void setRentalDuration(int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public BigDecimal getRentalRate() {
		return this.rentalRate;
	}

	public void setRentalRate(BigDecimal rentalRate) {
		this.rentalRate = rentalRate;
	}

	public BigDecimal getReplacementCost() {
		return this.replacementCost;
	}

	public void setReplacementCost(BigDecimal replacementCost) {
		this.replacementCost = replacementCost;
	}

	public Object getSpecialFeatures() {
		return this.specialFeatures;
	}

	public void setSpecialFeatures(Object specialFeatures) {
		this.specialFeatures = specialFeatures;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}