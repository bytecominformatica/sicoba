package br.com.clairtonluz.bytecom.util.converter.jpa;

import br.com.clairtonluz.bytecom.model.jpa.entity.extra.EntityGeneric;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Map;


@FacesConverter(value = "entityConverter")
public class EntityConverter implements Converter {

    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        if (value != null) {
            Object o = this.getAttributesFrom(component).get(value);
            return o;
        }
        return null;
    }

    public String getAsString(FacesContext ctx, UIComponent component, Object value) {
        if (value != null && !"".equals(value)) {
            EntityGeneric entity = (EntityGeneric) value;

            this.addAttribute(component, entity);

            Integer codigo = entity.getId();
            if (codigo != null) {
                return String.valueOf(codigo);
            }
        }
        return (String) value;
    }

    protected void addAttribute(UIComponent component, EntityGeneric o) {
        String key = o.getId().toString(); // codigo da empresa como chave neste caso
        this.getAttributesFrom(component).put(key, o);
    }

    protected Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }
}