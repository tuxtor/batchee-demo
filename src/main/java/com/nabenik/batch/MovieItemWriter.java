package com.nabenik.batch;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
public class MovieItemWriter extends AbstractItemWriter {

	@PersistenceContext
    EntityManager em;
	
	@Inject
	Logger logger;

    public void writeItems(List list) throws Exception {
        for (Object obj : list) {
        	logger.log(Level.INFO, "Writing " + obj);
            em.persist(obj);
        }
        em.flush();
    }
}
