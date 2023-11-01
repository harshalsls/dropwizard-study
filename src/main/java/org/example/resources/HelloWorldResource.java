package org.example.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/hello")
public class HelloWorldResource {

    @GET()
    public String get() {
        return "Hello World";
    }

}
