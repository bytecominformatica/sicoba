package net.servehttp.bytecom.extra.rest;

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