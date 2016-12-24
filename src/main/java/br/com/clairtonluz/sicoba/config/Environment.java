package br.com.clairtonluz.sicoba.config;

/**
 * Created by clairtonluz on 28/02/16.
 */
public interface Environment {
    String PRODUCTION = "PRODUCTION";
    String DEVELOPMENT = "DEVELOPMENT";
    String QUALITY = "QUALITY";

    String getEnv();
}
