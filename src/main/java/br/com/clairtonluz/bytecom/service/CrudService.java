package br.com.clairtonluz.bytecom.service;

import br.com.clairtonluz.bytecom.model.jpa.CrudJPA;
import br.com.clairtonluz.bytecom.model.jpa.comercial.PlanoJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.extra.EntityGeneric;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created by clairtonluz on 07/11/15.
 */
public class CrudService implements Serializable {
    @Inject
    private PlanoJPA crudJPA;

    public EntityGeneric save(EntityGeneric entityGeneric) {
        return crudJPA.save(entityGeneric);
    }

    public void remove(EntityGeneric entityGeneric) {
        crudJPA.remove(entityGeneric);
    }
}
