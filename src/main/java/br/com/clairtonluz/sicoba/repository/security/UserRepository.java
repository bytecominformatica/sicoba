package br.com.clairtonluz.sicoba.repository.security;

import br.com.clairtonluz.sicoba.model.entity.security.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by clairtonluz on 01/01/16.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
}
