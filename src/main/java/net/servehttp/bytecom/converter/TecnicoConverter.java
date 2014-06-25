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
import net.servehttp.bytecom.persistence.entity.Tecnico;

@ManagedBean
@RequestScoped
public class TecnicoConverter implements Converter {
  
  @Inject
  private GenericoJPA genericoJPA;

  @Override
  public String getAsString(FacesContext context, UIComponent component,
          Object value) {
      if (!(value instanceof Tecnico) || ((Tecnico) value).getId() == 0) {
          return null;
      }

      return String.valueOf(((Tecnico) value).getId());
  }

  @Override
  public Object getAsObject(FacesContext context, UIComponent component,
          String value) {
      if (value == null || !value.matches("\\d+")) {
          return null;
      }

      Tecnico tecnico = genericoJPA.buscarPorId(Tecnico.class, Integer.valueOf(value));

      if (tecnico == null) {
          throw new ConverterException(new FacesMessage("Unknown user ID: "
                  + value));
      }

      return tecnico;
  }

}
