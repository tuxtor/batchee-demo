package com.nabenik.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@WebServlet(name = "FileUploadServlet", urlPatterns = "/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
    @Inject
    @ConfigProperty(name = "file.destination", defaultValue = "/tmp/")
    private String destinationPath;

    @Inject
    private Logger logger;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = request.getParameter("description");
        Part filePart = request.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();


        //Save using buffered streams to avoid memory consumption
        try(InputStream fin = new BufferedInputStream(filePart.getInputStream());
                OutputStream fout = new BufferedOutputStream(new FileOutputStream(destinationPath.concat(fileName)))){

            byte[] buffer = new byte[1024*100];//100kb per chunk
            int lengthRead;
            while ((lengthRead = fin.read(buffer)) > 0) {
                fout.write(buffer,0,lengthRead);
                fout.flush();
            }

            response.getWriter().write("File written: " + fileName);

            //Fire batch Job after file upload :)
            JobOperator jobOperator = BatchRuntime.getJobOperator();
            Properties props = new Properties();
            props.setProperty("csvFileName", destinationPath.concat(fileName));
            response.getWriter().write("Batch job " + jobOperator.start("csvJob", props));
            logger.log(Level.WARNING, "Firing csv bulk load job - " + description );

        }catch (IOException ex){
            logger.log(Level.SEVERE, ex.toString());

            response.getWriter().write("Le error");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }


    }

}
