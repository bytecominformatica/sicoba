package br.com.clairtonluz.sicoba.repository.provedor;

import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Mikrotik;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by clairtonluz on 15/11/15.
 */
public interface MikrotikRepository extends JpaRepository<Mikrotik, Integer> {
}
