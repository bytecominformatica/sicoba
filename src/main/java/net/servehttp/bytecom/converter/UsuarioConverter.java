package net.servehttp.bytecom.converter;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Usuario;

@ManagedBean
@RequestScoped
public class UsuarioConverter implements Converter {
  
  
  @Inject
  private GenericoJPA genericoJPA;

  @Override
  public String getAsString(FacesContext context, UIComponent component,
          Object value) {
      if (!(value instanceof Usuario) || ((Usuario) value).getId() == 0) {
          return null;
      }

      return String.valueOf(((Usuario) value).getId());
  }

  @Override
  public Object getAsObject(FacesContext context, UIComponent component,
          String value) {
      if (value == null || !value.matches("\\d+")) {
          return null;
      }

      Usuario usuario = genericoJPA.buscarPorId(Usuario.class, Integer.valueOf(value));

      if (usuario == null) {
          throw new ConverterException(new FacesMessage("Unknown user ID: "
                  + value));
      }

      return usuario;
  }


}
