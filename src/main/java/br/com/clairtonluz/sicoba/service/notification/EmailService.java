package br.com.clairtonluz.sicoba.service.notification;

import br.com.clairtonluz.sicoba.config.MyEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by clairton on 24/12/16.
 */
@Service
public class EmailService {
    private final MyEnvironment myEnvironment;
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;
    @Value("${myapp.email.sac}")
    private String emailSac;
    @Value("${myapp.email.admin}")
    private String emailAdmin;
    @Value("${myapp.email.suporte}")
    private String emailSuporte;

    @Autowired
    public EmailService(MyEnvironment myEnvironment, JavaMailSender javaMailSender) {
        this.myEnvironment = myEnvironment;
        this.javaMailSender = javaMailSender;
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
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            javaMailSender.send(message);
        } else {
            System.out.println(env);
            System.out.println(from);
            System.out.println(to);
            System.out.println(subject);
            System.out.println(content);
        }
    }
}
