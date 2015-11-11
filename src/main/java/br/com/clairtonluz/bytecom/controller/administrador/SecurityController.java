package br.com.clairtonluz.bytecom.controller.administrador;

import br.com.clairtonluz.bytecom.model.jpa.entity.administrador.UserAccount;
import br.com.clairtonluz.bytecom.service.administrador.AccountService;
import br.com.clairtonluz.bytecom.util.NetworkUtil;
import br.com.clairtonluz.bytecom.util.ejb.MailEJB;
import br.com.clairtonluz.bytecom.util.extra.EmailAddress;
import br.com.clairtonluz.bytecom.util.web.WebUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * @author clairtonluz
 */
@Named
@RequestScoped
public class SecurityController implements Serializable {

    private static final long serialVersionUID = -4657746545855537894L;
    private static final Logger LOGGER = Logger.getLogger(SecurityController.class.getSimpleName());
    private static final String HOME_URL = "index.xhtml";

    private String username;
    private String password;
    private Subject currentUser = SecurityUtils.getSubject();

    @Inject
    private AccountService accountService;
    @EJB
    private MailEJB mail;
    @Inject
    private UserAccount userAccount;
    private String error;

    @PostConstruct
    public void carregar() throws IOException {
        if (currentUser.isAuthenticated()) {
            WebUtil.redirect(HOME_URL);
        }
    }

    public void authenticate() {
        error = null;
        if (!currentUser.isAuthenticated()) {
            try {
                currentUser.login(new UsernamePasswordToken(username, password));
                userAccount = accountService.findUserAccountByUsername(username);
                accountService.createPictureInSession(userAccount);
                currentUser.getSession().setAttribute("currentUser", userAccount);
                WebUtil.redirect(HOME_URL);
            } catch (AuthenticationException e) {
                error = "CREDENCIAIS INVÁLIDAS";
                LOGGER.info("[" + LocalDateTime.now() + "] - " + "[" + username + "] - " + "ACESSO NEGADO");
                sendAlert();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void logout() {
        SecurityUtils.getSubject().logout();
    }

    private void sendAlert() {
        final String assunto = "Tentativa de acesso";
        final String ipQueTentouAcessar = NetworkUtil.INSTANCE.getIp();
        final String mensagem =
                "O seguinte ip tentou acessar o sistema: " + ipQueTentouAcessar
                        + " com o seguite usuario: " + username;

        mail.send(EmailAddress.OWNERS, assunto, mensagem);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
