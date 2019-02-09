package br.com.clairtonluz.sicoba.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by clairtonluz on 28/02/16.
 */
@Component
public class MyEnvironment {
    private final Environment environment;
    private final List<String> activeProfiles;
    @Value("${myapp.boleto.notification-url}")
    private String notificationUrl;

    @Autowired
    public MyEnvironment(Environment environment) {
        this.environment = environment;
        this.activeProfiles = Arrays.asList(environment.getActiveProfiles());
    }

    public boolean isQuality() {
        return activeProfiles.contains("staging");
    }

    public boolean isProduction() {
        return activeProfiles.contains("production");
    }

    public List<String> getEnv() {
        return activeProfiles;
    }

    public String getNotificationUrl(){
        return notificationUrl;
    }
}
