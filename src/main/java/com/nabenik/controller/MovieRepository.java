package com.nabenik.controller;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import com.nabenik.model.Movie;

@Repository(forEntity = Movie.class)
public abstract class MovieRepository extends AbstractEntityRepository<Movie, Long> {
	
	@Inject
    public EntityManager em;
}
