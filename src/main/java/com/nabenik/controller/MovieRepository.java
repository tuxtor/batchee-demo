package com.nabenik.controller;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import com.nabenik.model.Movie;

@Repository(forEntity = Movie.class)
public interface MovieRepository extends EntityRepository<Movie, Long> {
}
