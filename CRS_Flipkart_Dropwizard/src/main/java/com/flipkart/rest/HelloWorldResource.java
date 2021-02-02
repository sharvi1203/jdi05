package com.flipkart.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/hello/{name}")
public class HelloWorldResource {

    @GET
    public String HelloWorld(@PathParam("name") String name) {
        return "Hello " + name + "!";
    }
}
