package net.servehttp.bytecom.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Mensalidade;

@Named
@RequestScoped
public class MensalidadeConverter implements Converter {

	@Inject
	private GenericoJPA genericoJPA;

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (!(value instanceof Mensalidade) || ((Mensalidade) value).getId() == 0) {
			return null;
		}

		return String.valueOf(((Mensalidade) value).getId());
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value == null || !value.matches("\\d+")) {
			return null;
		}

		Mensalidade mensalidade = genericoJPA.buscarPorId(Mensalidade.class, Integer.valueOf(value));

		if (mensalidade == null) {
			throw new ConverterException(new FacesMessage("mensalidade desconhecido ID: "
					+ value));
		}

		return mensalidade;
	}
}