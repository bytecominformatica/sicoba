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
import net.servehttp.bytecom.persistence.entity.cadastro.Despesa;

@ManagedBean
@RequestScoped
public class DespesaConverter implements Converter {
	
	@Inject
	private GenericoJPA genericoJPA;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value == null || !value.matches("\\d+")) {
			return null;
		}

		Despesa despesa = genericoJPA.findById(Despesa.class, Integer.valueOf(value));

		if (despesa == null) {
			throw new ConverterException(new FacesMessage("Registro desconhecido ID: "
					+ value));
		}

		return despesa;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
			if (!(value instanceof Despesa) || ((Despesa) value).getId() == 0) {
				return null;
			}

			return String.valueOf(((Despesa) value).getId());
	}
	

}
