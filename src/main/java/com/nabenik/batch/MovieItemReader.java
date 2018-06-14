package com.nabenik.batch;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.nabenik.model.Movie;

@Named
public class MovieItemReader extends AbstractItemReader {

	@Inject
	private JobContext jobContext;

	@Inject
	private Logger logger;

	private FileInputStream is;
	private BufferedReader br;
	private Long recordNumber;

	@Override
	public void open(Serializable prevCheckpointInfo) throws Exception {
		recordNumber = 1L;
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties jobParameters = jobOperator.getParameters(jobContext.getExecutionId());
		String resourceName = (String) jobParameters.get("csvFileName");
		is = new FileInputStream(resourceName);
		br = new BufferedReader(new InputStreamReader(is));

		if (prevCheckpointInfo != null)
			recordNumber = (Long) prevCheckpointInfo;
		for (int i = 0; i < recordNumber; i++) { // Skip until recordNumber
			br.readLine();
		}
		logger.log(Level.WARNING, "Reading started on record " + recordNumber);
	}

	@Override
	public Object readItem() throws Exception {

		String line = br.readLine();

		if (line != null) {
			String[] movieValues = line.split(",");
			Movie movie = new Movie();
			movie.setName(movieValues[0]);
			movie.setReleaseYear(movieValues[1]);
			
			// Now that we could successfully read, Increment the record number
			recordNumber++;
			return movie;
		}
		return null;
	}

	@Override
	public Serializable checkpointInfo() throws Exception {
	        return recordNumber;
	}
}
