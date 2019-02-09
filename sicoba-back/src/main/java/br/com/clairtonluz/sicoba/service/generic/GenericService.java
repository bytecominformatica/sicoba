package br.com.clairtonluz.sicoba.service.generic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

public abstract class GenericService<ENTITY> implements Serializable {

    protected Example<ENTITY> toQuery(Map<String, Object> map) {
        return toQuery(map, null);
    }

    protected Example<ENTITY> toQuery(Map<String, Object> map, ExampleMatcher matcher) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(map);
            ENTITY t = objectMapper.readValue(json, getEntityClass());
            return matcher == null ? Example.of(t) : Example.of(t, matcher);
        } catch (IOException e) {
            throw new RuntimeException("Error ao converter mapa para Example", e);
        }
    }

    public abstract Class<ENTITY> getEntityClass();

}