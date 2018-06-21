package com.nabenik.batch;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import com.nabenik.controller.MovieRepository;
import com.nabenik.model.Movie;

@Named
public class MovieItemWriter extends AbstractItemWriter {

	@Inject
    MovieRepository movieService;
	
	@Inject
	Logger logger;

    public void writeItems(List list) throws Exception {
        for (Object obj : list) {
        	logger.log(Level.INFO, "Writing " + obj);
            movieService.save((Movie)obj);
        }
    }
}

