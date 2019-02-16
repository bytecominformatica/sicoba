package br.com.clairtonluz.sicoba.api.security;

import br.com.clairtonluz.sicoba.model.entity.security.User;
import br.com.clairtonluz.sicoba.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController()
@RequestMapping("api/users")
public class UserAPI {

    private final UserService userService;

    @Autowired
    public UserAPI(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> query() {
        return userService.buscarTodos();
    }

    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
    public User getPorUsername(@PathVariable String username) {
        return userService.buscarPorUsername(username);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getPorId(@PathVariable Integer id) {
        return userService.buscarPorId(id);
    }


    @RequestMapping(method = RequestMethod.POST)
    public User save(@Valid @RequestBody User user) {
        return userService.save(user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public User update(@Valid @RequestBody User user) {
        return userService.save(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable @NotNull Integer id) {
        userService.remover(id);
    }
}
