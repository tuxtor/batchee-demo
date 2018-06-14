package com.nabenik.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.nabenik.model.Movie;

@Named
public class MovieItemProcessor implements ItemProcessor {

	@Inject
    private JobContext jobContext;

	@Override
    public Object processItem(Object obj) 
        		throws Exception {
        Movie inputRecord =
                (Movie) obj;
        
        //"Complex processing"
        inputRecord.setName(inputRecord.getName().toUpperCase());
        Thread.sleep(500);
         
        return inputRecord;
    } 
}