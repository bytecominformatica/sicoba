package br.com.clairtonluz.sicoba.service.provedor;

import br.com.clairtonluz.sicoba.exception.BadRequestException;
import br.com.clairtonluz.sicoba.exception.NotFoundException;
import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Mikrotik;
import br.com.clairtonluz.sicoba.repository.provedor.MikrotikRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by clairtonluz on 07/11/15.
 */
@Service
public class MikrotikService {

    private final MikrotikRepository mikrotikRepository;
    private final String token;

    public MikrotikService(MikrotikRepository mikrotikRepository,
                           @Value("${myapp.token}") String token) {
        this.mikrotikRepository = mikrotikRepository;
        this.token = token;
    }

    public Iterable<Mikrotik> buscarTodos() {
        return mikrotikRepository.findAll();
    }

    @Transactional
    public Mikrotik save(Mikrotik mikrotik) {
        return mikrotikRepository.save(mikrotik);
    }

    @Transactional
    public void remove(Integer id) {
        Mikrotik mikrotik = buscarPorId(id);
        mikrotikRepository.delete(mikrotik);
    }

    public Mikrotik buscarPorId(Integer id) {
        return mikrotikRepository.findOne(id);
    }

    public void atualizarHost(Integer id, String token, Mikrotik mikrotik) {
        validaToken(token);
        validaNovoHost(mikrotik);

        Mikrotik currentMikrotik = mikrotikRepository.findOne(id);

        if (currentMikrotik == null) throw new NotFoundException("Mikrotik " + id + " não encontrado");

        currentMikrotik.setHost(mikrotik.getHost());
        mikrotikRepository.save(currentMikrotik);
    }

    private void validaToken(String token) {
        if (!this.token.equals(token)) throw new AccessDeniedException("Token inválido");
    }

    private void validaNovoHost(Mikrotik mikrotik) {
        if (mikrotik == null || mikrotik.getHost() == null)
            throw new BadRequestException("atributo host não encontrado no payload");
    }
}
