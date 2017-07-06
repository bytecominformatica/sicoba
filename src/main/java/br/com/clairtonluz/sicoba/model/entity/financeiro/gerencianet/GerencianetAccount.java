package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.config.Environment;
import br.com.clairtonluz.sicoba.exception.BadRequestException;
import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;
import br.com.clairtonluz.sicoba.util.StringUtil;
import org.json.JSONObject;

import javax.persistence.*;

/**
 * Created by clairton on 21/12/16.
 */
@Entity
@Table(name = "gerencianet_account")
public class GerencianetAccount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gerencianet_account_id_seq")
    @SequenceGenerator(name = "gerencianet_account_id_seq", sequenceName = "gerencianet_account_id_seq")
    private Integer id;
    private String name;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "client_secret")
    private String clientSecret;
    private Double fine;
    private Double interest;
    private boolean sandbox;
    @Column(name = "notify_client")
    private boolean notifyClient;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    public Double getFine() {
        return fine;
    }

    public void setFine(Double fine) {
        this.fine = fine;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public JSONObject createOptions() {
        JSONObject options = new JSONObject();
        options.put("client_id", getClientId());
        options.put("client_secret", getClientSecret());
        options.put("sandbox", Environment.isProduction());
        return options;
    }

    public boolean isNotifyClient() {
        if (!Environment.isProduction()) {
            return false;
        }

        return notifyClient;
    }

    public void setNotifyClient(boolean notifyClient) {
        this.notifyClient = notifyClient;
    }

    /**
     * O padrão de url a ser configurada: https://meudominio.com.br/api/service/%d/metodo
     * onde o %d será substituido pelo o id da conta gerencianet configurada.
     *
     * @return
     */
    public String createNotificationUrl() {
        String url = null;
        if (id == null) {
            throw new BadRequestException("Conta gerencianet não foi cadastrada no sistema");
        }

        String notificationUrl = System.getenv("NOTIFICATION_URL");
        if (!StringUtil.isEmpty(notificationUrl)) {
            if (!notificationUrl.contains("%d")) {
                throw new BadRequestException("A url e notificação segue o padrão expecificado");
            }
            url = String.format(notificationUrl, id);
        }
        return url;
    }
}
