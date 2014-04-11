package net.servehttp.bytecom.converter;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;

import net.servehttp.bytecom.persistence.ContratoJPA;
import net.servehttp.bytecom.persistence.entity.Contrato;

@ManagedBean
@RequestScoped
public class ContratoConverter implements Converter {

	@Inject
	private ContratoJPA contratoJPA;

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (!(value instanceof Contrato) || ((Contrato) value).getId() == 0) {
			return null;
		}

		return String.valueOf(((Contrato) value).getId());
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value == null || !value.matches("\\d+")) {
			return null;
		}

		Contrato contrato = contratoJPA.buscarPorId(Integer.valueOf(value));

		if (contrato == null) {
			throw new ConverterException(new FacesMessage("Registro desconhecido ID: "
					+ value));
		}

		return contrato;
	}
}