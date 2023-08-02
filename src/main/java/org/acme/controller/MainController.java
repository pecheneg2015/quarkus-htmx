package org.acme.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;

@Path("/")
public class MainController {


    @GET
    public Response  get() {
        URI uri = UriBuilder.fromPath("/country").build();
        return Response.temporaryRedirect(uri).build();

    }
}
