package net.servehttp.bytecom.extra.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * 
 * @author Felipe W. M Martins>
 *
 */
public class RegisterServices extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	
    public RegisterServices() {
		this.singletons.add(new HelloWorld());
		this.singletons.add(new ClientGeoRefServices());
	}
 
    public Set<Object> getSingletons()
    {
        return this.singletons;
    }
}
