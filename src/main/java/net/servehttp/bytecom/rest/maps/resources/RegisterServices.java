package net.servehttp.bytecom.rest.maps.resources;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * 
 * @author Felipe W. M Martins>
 *
 */
@ApplicationPath(value = "/rest")
public class RegisterServices extends Application {
	private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();
	
    public RegisterServices() {
		this.singletons.add(new HelloWorld());
		this.singletons.add(new ClientGeoRefServices());
	}
    
    public Set<Class<?>> getClasses()
    {
        return this.empty;
    }
 
    public Set<Object> getSingletons()
    {
        return this.singletons;
    }
}
