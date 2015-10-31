package net.servehttp.bytecom.controller.administrador;

import net.servehttp.bytecom.controller.extra.GenericoController;
import net.servehttp.bytecom.model.jpa.entity.administrador.Authentication;
import net.servehttp.bytecom.model.jpa.entity.administrador.UserAccount;
import net.servehttp.bytecom.service.administrador.AccountService;
import net.servehttp.bytecom.util.ImageUtil;
import net.servehttp.bytecom.util.StringUtil;
import net.servehttp.bytecom.util.seguranca.HashUtil;
import net.servehttp.bytecom.util.web.AlertaUtil;
import net.servehttp.bytecom.util.web.WebUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @author felipe
 */
@Named
@ViewScoped
public class UserAccountController extends GenericoController implements Serializable {

    private static final long serialVersionUID = -2081234112300283530L;

    private List<UserAccount> listUserAccount;
    private UserAccount userAccount = new UserAccount();
    private String username;
    private String password;
    private String confirmPassword;
    private String page;
    private static final String EXTENSION = "png";

    private Part file;

    @Inject
    private AccountService service;

    @PostConstruct
    public void load() {
        listUserAccount = service.findUsersAccounts();
        getParameters();

    }

    private boolean valida(UserAccount userAccount) {
        boolean result = true;

        if (!service.emailAvaliable(userAccount)) {
            AlertaUtil.error("Email já cadastrado!");
            result = false;
        }

        if (password != null && !password.isEmpty() && !password.equals(confirmPassword)) {
            AlertaUtil.error("As senhas não confere");
            return false;
        }
        return result;
    }

    public String salvar() {
        page = null;

        if (valida(userAccount)) {
            if (file != null) {
                try {
                    userAccount.setImg(ImageUtil.setThumbnail(ImageUtil.tratarImagem(file.getInputStream()),
                            EXTENSION));
                } catch (IOException e) {
                    AlertaUtil.error(e.getMessage());
                }
            }
            if (userAccount.getId() == 0) {
                Authentication auth = new Authentication();
                auth.setUsername(username);
                auth.setPassword(HashUtil.sha256ToHex(password));
                auth.setUserAccount(userAccount);
                jpa.salvar(auth);
            }
            jpa.salvar(userAccount);
            AlertaUtil.info("Usuário salvo com sucesso!");
            page = "list";
        }

        return page;
    }

    public void resetPassword() {
        Authentication auth = service.findAuthenticationByUserAccount(userAccount);
        String senha = StringUtil.gerarSenha(8);
        auth.setPassword(HashUtil.sha256ToHex(senha));
        jpa.salvar(auth);
        AlertaUtil.info("A senha do usuário " + auth.getUsername() + " foi alterada para " + senha);
    }

    private void getParameters() {
        String userAccountId = WebUtil.getParameters("id");
        if (userAccountId != null && !userAccountId.isEmpty()) {
            userAccount = service.findUserAccountById(Integer.parseInt(userAccountId));
        }
    }

    public String remover() {
        page = null;
        service.remover(userAccount);
        page = "list";
        return page;
    }

    public List<UserAccount> getListUserAccount() {
        return listUserAccount;
    }

    public void setListUserAccount(List<UserAccount> listUserAccount) {
        this.listUserAccount = listUserAccount;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


}
