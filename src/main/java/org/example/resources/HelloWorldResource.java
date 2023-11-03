package org.example.resources;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

    @GET()
    @Metered(name = "helloWorld")
    @Path("/world/{user}")
    @Timed
    public String get(@PathParam("user") String user) {
        return "Hello " + user;
    }

}
