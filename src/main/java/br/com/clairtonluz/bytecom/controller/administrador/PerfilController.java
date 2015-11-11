package br.com.clairtonluz.bytecom.controller.administrador;

import br.com.clairtonluz.bytecom.model.jpa.entity.administrador.Authentication;
import br.com.clairtonluz.bytecom.model.jpa.entity.administrador.UserAccount;
import br.com.clairtonluz.bytecom.service.administrador.AccountService;
import br.com.clairtonluz.bytecom.service.administrador.UserAccountService;
import br.com.clairtonluz.bytecom.util.ImageUtil;
import br.com.clairtonluz.bytecom.util.qualifier.UsuarioLogado;
import br.com.clairtonluz.bytecom.util.seguranca.HashUtil;
import br.com.clairtonluz.bytecom.util.web.AlertaUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author clairtonluz
 */
@Named
@ViewScoped
public class PerfilController implements Serializable {

    private static final long serialVersionUID = -2081234112300283530L;
    @Inject
    @UsuarioLogado
    private UserAccount userAccount;
    @Inject
    private AccountService accountService;
    @Inject
    private UserAccountService userAccountService;
    private Authentication authentication;
    private String password;
    private String confirmPassword;
    private Part file;

    @PostConstruct
    public void load() {
        setAuthentication(accountService.findAuthenticationByUserAccount(userAccount));
    }

    public void carregarImagem() throws IOException {
        if (file != null) {
            userAccount.setImg(ImageUtil.setThumbnail(ImageUtil.tratarImagem(file.getInputStream()), "png"));
            userAccount.setImageGerada(null);
        }
    }

    private boolean userAccountValid() {
        boolean result = true;
        if (!accountService.emailAvaliable(userAccount)) {
            AlertaUtil.error("Email já cadastrado!");
            result = false;
        }
        if (!senhaConfere()) {
            AlertaUtil.error("Senha não confere!");
            result = false;
        }
        return result;
    }

    public void salvar() {
        if (userAccountValid()) {
            if (password.isEmpty()) {
                userAccountService.save(userAccount);
            } else {
                authentication.setUserAccount(userAccount);
                authentication.setPassword(HashUtil.sha256ToHex(password));
                userAccountService.save(authentication);
            }
            AlertaUtil.info("Usuário atualizado com sucesso!");
        }
    }

    private boolean senhaConfere() {
        return password.equals(confirmPassword);
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

}
