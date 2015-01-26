package net.servehttp.bytecom.rest.maps.resources;

import javax.ws.rs.*;
 
@Path("tutorial")
public class HelloWorld
{
 
    @GET
    @Path("helloworld")
    @Produces("application/json")
    public String helloworld() {
        return "Hello World!";
    }
}