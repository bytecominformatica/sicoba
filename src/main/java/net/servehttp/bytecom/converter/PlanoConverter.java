package net.servehttp.bytecom.converter;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;

import net.servehttp.bytecom.persistence.PlanoJPA;
import net.servehttp.bytecom.persistence.entity.Plano;

@ManagedBean
@RequestScoped
public class PlanoConverter implements Converter {

	@Inject
	private PlanoJPA planoJPA;

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (!(value instanceof Plano) || ((Plano) value).getId() == 0) {
			return null;
		}

		return String.valueOf(((Plano) value).getId());
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value == null || !value.matches("\\d+")) {
			return null;
		}

		Plano plano = planoJPA.buscarPorId(Integer.valueOf(value));

		if (plano == null) {
			throw new ConverterException(new FacesMessage("Registro desconhecido ID: "
					+ value));
		}

		return plano;
	}
}