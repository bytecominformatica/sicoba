package net.servehttp.bytecom.util;

import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.entity.Usuario;
import net.servehttp.bytecom.qualifier.UsuarioLogado;

/**
 * @author Clairton Luz
 */
public class ProducesUtil {

    @Produces @Named @UsuarioLogado
    public Usuario getUsuarioLogado() {
    	ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
        return (Usuario) ec.getSessionMap().get("currentUser");
    }
   
}