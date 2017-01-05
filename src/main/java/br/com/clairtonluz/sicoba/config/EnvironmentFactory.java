package br.com.clairtonluz.sicoba.config;

import br.com.clairtonluz.sicoba.config.impl.EnvironmentDevelopment;
import br.com.clairtonluz.sicoba.config.impl.EnvironmentProduction;
import br.com.clairtonluz.sicoba.config.impl.EnvironmentQuality;

/**
 * Created by clairtonluz on 28/02/16.
 */
public class EnvironmentFactory {

    private static final Environment PRODUCTION;
    private static final Environment DEVELOPMENT;
    private static final Environment QUALITY;

    static {
        PRODUCTION = new EnvironmentProduction();
        DEVELOPMENT = new EnvironmentDevelopment();
        QUALITY = new EnvironmentQuality();
    }

    public static Environment create() {
        String env = System.getenv("ENV");
        Environment current;
        if (env == null) {
            current = DEVELOPMENT;
        } else {
            switch (env) {
                case Environment.PRODUCTION:
                    current = PRODUCTION;
                    break;
                case Environment.QUALITY:
                    current = QUALITY;
                    break;
                default:
                    current = DEVELOPMENT;
            }
        }

        return current;
    }
}
