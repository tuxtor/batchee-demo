package com.nabenik.web;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        }catch (IOException ex){
            logger.log(Level.SEVERE, ex.toString());

            response.getWriter().write("Le error");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }


    }

}
