package br.com.clairtonluz.sicoba.service.notification;

import br.com.clairtonluz.sicoba.config.MyEnvironment;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by clairton on 24/12/16.
 */
@Service
public class EmailService {
    private final MyEnvironment myEnvironment;
    private final SendGrid sendGrid;
    //    @Value("${spring.sendgrid.api-key}")
//    private String sendgridApiKey;
    @Value("${spring.mail.username}")
    private String from;
    @Value("${myapp.email.sac}")
    private String emailSac;
    @Value("${myapp.email.admin}")
    private String emailAdmin;
    @Value("${myapp.email.suporte}")
    private String emailSuporte;

    @Autowired
    public EmailService(MyEnvironment myEnvironment, SendGrid sendGrid) {
        this.myEnvironment = myEnvironment;
        this.sendGrid = sendGrid;
    }

    private static String stackTraceAsString(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    public void notificarAdmin(Exception e) {
        notificarAdmin(e.getMessage(), e);
    }

    public void notificarAdmin(String subject, Exception e) {
        subject = String.format("[SICOBA]%s[ERROR] - %s", myEnvironment.getEnv(), subject);
        String content = stackTraceAsString(e);
        sendToAdmin(subject, content);
    }

    public void sendToAdmin(String subject, String content) {
        send(emailAdmin, subject, content);
    }

    public void sendToSac(String subject, String content) {
        send(emailSac, subject, content);
    }

    public void send(String to, String subject, String content) {
        send(from, to, subject, content);
    }

    public void send(String from, String to, String subject, String content) {
        List<String> env = myEnvironment.getEnv();
        if (myEnvironment.isProduction() || myEnvironment.isQuality()) {
            if (!myEnvironment.isProduction()) {
                subject = String.format("%s%s", env, subject);
            }

            Mail mail = new Mail(new Email(from), subject, new Email(to),
                    new Content("text/plain", content));

            Request request = new Request();
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                Response response = sendGrid.api(request);
                System.out.println(response.getStatusCode());
                System.out.println(response.getBody());
                System.out.println(response.getHeaders());
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("======ERROR======");
                System.out.println(env);
                System.out.println(from);
                System.out.println(to);
                System.out.println(subject);
                System.out.println(content);
                System.out.println("======ERROR======");
            }
        } else {
            System.out.println(env);
            System.out.println(from);
            System.out.println(to);
            System.out.println(subject);
            System.out.println(content);
        }
    }
}
