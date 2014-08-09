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
import net.servehttp.bytecom.persistence.entity.cadastro.Fornecedor;

@ManagedBean
@RequestScoped
public class FornecedorConverter implements Converter {
  
  @Inject
  private GenericoJPA genericoJPA;

  @Override
  public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
    if(value == null || value.matches("\\d+")){
      return null;
    }
    Fornecedor fornecedor = genericoJPA.findById(Fornecedor.class, Integer.valueOf(value));
    
    if(fornecedor == null){
      throw new ConverterException(new FacesMessage("Registro desconhecido ID:"+value));
    }
    return fornecedor;
  }

  @Override
  public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
    if (!(value instanceof Fornecedor) || ((Fornecedor) value).getId() == 0) {
      return null;
    }
    return String.valueOf(((Fornecedor) value).getId());
  }

}
