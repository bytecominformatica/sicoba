package br.com.clairtonluz.bytecom.config;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandle implements ExceptionMapper<Exception> {

    private static final HandleException HANDLE = new HandleException();

    @Override
    public Response toResponse(Exception e) {
        System.out.println("Global");
        e.printStackTrace();
        return HANDLE.handle(e);
    }
}
