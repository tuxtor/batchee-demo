package com.nabenik.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UploadStatusServlet
 */
@WebServlet("/UploadStatusServlet")
public class UploadStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		
		String batchJobId = request.getParameter("batchid");
		
		if(batchJobId == null) {
			writer.println("Job not found ");
			return;
		}
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		JobExecution jobExec = jobOperator.getJobExecution(Long.valueOf(batchJobId));
		String status = jobExec.getBatchStatus().toString();
		writer.println(status);
		writer.println("Served at: ");
		writer.println(request.getContextPath());
	}


}
