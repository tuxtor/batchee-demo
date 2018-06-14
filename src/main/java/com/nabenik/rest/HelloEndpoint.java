package com.nabenik.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloEndpoint {

    @GET
    @Produces("text/plain")
    public String getHello() {
        // Return some cliched textual content
        return "Hello v0.1.0";
    }

}
