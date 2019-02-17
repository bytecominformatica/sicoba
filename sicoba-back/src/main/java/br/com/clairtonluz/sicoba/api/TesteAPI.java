package br.com.clairtonluz.sicoba.api;

import br.com.clairtonluz.sicoba.service.notification.EmailService;
import br.com.clairtonluz.sicoba.util.NetworkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by clairtonluz on 28/02/16.
 */
@RestController()
@RequestMapping("api/testes")
public class TesteAPI {

    private final Environment environment;
    private final EmailService emailService;

    @Autowired
    public TesteAPI(Environment environment, EmailService emailService) {
        this.environment = environment;
        this.emailService = emailService;
    }

    @RequestMapping("/exception")
    public String testeException() {
        throw new RuntimeException("Teste");
    }

    @RequestMapping("/profile")
    public String[] profile() {
        return environment.getActiveProfiles();
    }

    @RequestMapping("/email")
    public String email(HttpServletRequest request) {
        emailService.sendToAdmin("TESTE", "Email de teste do ip " + NetworkUtil.INSTANCE.getIp(request));
        return "Email enviado";
    }


    @RequestMapping("/currentUser")
    public Principal currentUser(Principal principal) {
        return principal;
    }

}
