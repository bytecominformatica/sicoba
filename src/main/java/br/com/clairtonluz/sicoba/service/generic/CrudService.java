package br.com.clairtonluz.sicoba.service.generic;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class CrudService<ENTITY extends BaseEntity,
        REPOSITORY extends JpaRepository<ENTITY, ID>,
        ID extends Serializable> extends GenericService<ENTITY> {

    protected REPOSITORY repository;

    public CrudService(REPOSITORY repository) {
        this.repository = repository;
    }

    public List<ENTITY> findAll(Map<String, Object> params) {
        Example<ENTITY> example = toQuery(params);
        return findAll(example);
    }

    public List<ENTITY> findAll(Example<ENTITY> example) {
        return repository.findAll(example);
    }

    public ENTITY findById(ID id) {
        return repository.getOne(id);
    }

    public ENTITY save(ENTITY entity) {
        return repository.save(entity);
    }

    public void delete(ID id) {
        repository.deleteById(id);
    }


}