package br.com.clairtonluz.bytecom.config;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class HandleException {

    public Response handle(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("message", e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).type("application/json;charset=UTF-8").entity(map).build();
    }
}
