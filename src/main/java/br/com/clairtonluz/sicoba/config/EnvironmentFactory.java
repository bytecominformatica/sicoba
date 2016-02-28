package br.com.clairtonluz.sicoba.config;

import br.com.clairtonluz.sicoba.config.impl.EnvironmentDevelopment;
import br.com.clairtonluz.sicoba.config.impl.EnvironmentProduction;

/**
 * Created by clairtonluz on 28/02/16.
 */
public class EnvironmentFactory {

    private static final Environment PRODUCTION;
    private static final Environment DEVELOPMENT;

    static {
        PRODUCTION = new EnvironmentProduction();
        DEVELOPMENT = new EnvironmentDevelopment();
    }

    public static Environment create() {
        String env = System.getenv("ENV");
        if (env != null && env.equals(Environment.PRODUCTION)) {
            return PRODUCTION;
        } else {
            return DEVELOPMENT;
        }
    }
}
