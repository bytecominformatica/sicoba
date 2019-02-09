package br.com.clairtonluz.sicoba.api;

import br.com.clairtonluz.sicoba.exception.BadRequestException;
import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;
import br.com.clairtonluz.sicoba.service.generic.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class CrudEndpoint<
        ENTITY extends BaseEntity,
        REPOSITORY extends JpaRepository<ENTITY, ID>,
        SERVICE extends CrudService<ENTITY, REPOSITORY, ID>,
        ID extends Serializable> {

    protected final SERVICE service;

    public CrudEndpoint(SERVICE service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ENTITY getById(@PathVariable ID id) {
        return service.findById(id);
    }

    @GetMapping
    public List<ENTITY> get(@RequestParam Map<String, Object> params) {
        return service.findAll(params);
    }

    @PostMapping
    public ENTITY post(@Valid @RequestBody ENTITY entity) {
        return service.save(entity);
    }

    @PostMapping("{id}")
    public ENTITY put(@PathVariable ID id, @Valid @RequestBody ENTITY entity) {
        if (!id.equals(entity.getId()))
            throw new BadRequestException("Id informado não é o mesmo informado na entidade");
        return service.save(entity);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable ID id) {
        service.delete(id);
    }

}
