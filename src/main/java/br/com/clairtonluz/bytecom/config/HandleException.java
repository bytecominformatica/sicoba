package br.com.clairtonluz.bytecom.config;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class HandleException implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        e.printStackTrace();
        Map<String, String> map = new HashMap<>();
        map.put("error", e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).type("application/json;charset=UTF-8").entity(map).build();
    }
}
