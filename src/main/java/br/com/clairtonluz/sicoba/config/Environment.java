package br.com.clairtonluz.sicoba.config;

/**
 * Created by clairtonluz on 28/02/16.
 */
public interface Environment {
    String PRODUCTION = "PRODUCTION";
    String DEVELOPMENT = "DEVELOPMENT";
    String QUALITY = "QUALITY";

    String getEnv();

    static boolean isProduction() {
        return PRODUCTION.equals(EnvironmentFactory.create().getEnv());
    }

    static boolean isQuality() {
        return QUALITY.equals(EnvironmentFactory.create().getEnv());
    }
}
