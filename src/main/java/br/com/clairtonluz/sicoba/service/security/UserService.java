package br.com.clairtonluz.sicoba.service.security;

import br.com.clairtonluz.sicoba.model.entity.security.User;
import br.com.clairtonluz.sicoba.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> buscarTodos() {
        return userRepository.findAll();
    }

    public User buscarPorId(Integer id) {
        return userRepository.findOne(id);
    }

    @Transactional
    public User save(User user) {
        criptografaPassword(user);
        return userRepository.save(user);
    }

    private void criptografaPassword(User user) {
        if (isNovo(user) || passwordChanged(user)) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
        }
    }

    private boolean isNovo(User user) {
        return user.getId() == null || user.getId() == 0;
    }

    private boolean passwordChanged(User user) {
        User antigo = buscarPorId(user.getId());
        return !antigo.getPassword().equals(user.getPassword());
    }

    @Transactional
    public void remover(Integer id) {
        User user = userRepository.findOne(id);
        userRepository.delete(user);
    }

    public User buscarPorUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
