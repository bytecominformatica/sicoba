package br.com.clairtonluz.bytecom.service.extra;

import br.com.clairtonluz.bytecom.model.jpa.entity.extra.ClienteGeoReferencia;
import br.com.clairtonluz.bytecom.model.jpa.extra.ClienteGeoReferenciaJPA;
import br.com.clairtonluz.bytecom.service.CrudService;

import javax.inject.Inject;

public class ClienteGeoLocalizacaoService extends CrudService {

    private static final long serialVersionUID = -8296012997453708684L;
    @Inject
    private ClienteGeoReferenciaJPA clienteGeoReferenciaJPA;

    public ClienteGeoReferencia findByClient(int clientId) {
        return clienteGeoReferenciaJPA.findByClient(clientId);
    }
}
