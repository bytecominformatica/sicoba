package br.com.clairtonluz.sicoba.util;

import br.com.clairtonluz.sicoba.config.Environment;
import br.com.clairtonluz.sicoba.config.EnvironmentFactory;
import com.sendgrid.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by clairton on 24/12/16.
 */
public class SendEmail {

    private SendEmail() {

    }

    public static void notificarAdmin(Exception e) {
        notificarAdmin(e.getMessage(), e);
    }

    public static void notificarAdmin(String subject, Exception e) {
        subject = String.format("[SICOBA][%s][ERROR] - %s", EnvironmentFactory.create().getEnv(), subject);
        String content = stackTraceAsString(e);
        sendToAdmin(subject, content);
    }

    public static void sendToAdmin(String subject, String content) {
        send(System.getenv("SICOBA_EMAIL_ADMIN"), subject, content);
    }

    public static void send(String to, String subject, String content) {
        send(System.getenv("SICOBA_EMAIL_SUPORTE"), to, subject, content);
    }

    public static void send(String from, String to, String subject, String content) {
        String env = EnvironmentFactory.create().getEnv();
        if (Environment.PRODUCTION.equals(env) || Environment.QUALITY.equals(env)) {
            if (!Environment.PRODUCTION.equals(env)) {
                subject = String.format("[%s]%s", env, subject);
            }
            Email emailFrom = new Email(from);
            Email emailTo = new Email(to);
            Content content2 = new Content("text/plain", content);
            Mail mail = new Mail(emailFrom, subject, emailTo, content2);

            String sendgridApiKey = System.getenv("SENDGRID_API_KEY");
            SendGrid sg = new SendGrid(sendgridApiKey);
            Request request = new Request();
            try {
                request.method = Method.POST;
                request.endpoint = "mail/send";
                request.body = mail.build();
                Response response = sg.api(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(env);
            System.out.println(from);
            System.out.println(to);
            System.out.println(subject);
            System.out.println(content);
        }
    }

    private static String stackTraceAsString(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}
