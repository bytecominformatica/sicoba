package br.com.clairtonluz.bytecom.config;

import br.com.clairtonluz.bytecom.util.MensagemException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MensagemExceptionHandle implements ExceptionMapper<MensagemException> {

    private static final HandleException HANDLE = new HandleException();

    @Override
    public Response toResponse(MensagemException e) {
        System.out.println("MensagemException");
        return HANDLE.handle(e);
    }
}
