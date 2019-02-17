package br.com.clairtonluz.sicoba.repository.security;

import br.com.clairtonluz.sicoba.model.entity.security.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by clairtonluz on 01/01/16.
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    List<UserRole> findByUser_username(String username);
}
