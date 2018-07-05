package br.com.clairtonluz.sicoba.config.persistence;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by clairtonluz on 28/01/16.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("br.com.clairtonluz.sicoba")
@EnableJpaAuditing
public class DatabaseConfig {
//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
}