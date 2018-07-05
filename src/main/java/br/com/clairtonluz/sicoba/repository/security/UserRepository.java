package br.com.clairtonluz.sicoba.repository.security;

import br.com.clairtonluz.sicoba.model.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by clairtonluz on 01/01/16.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
