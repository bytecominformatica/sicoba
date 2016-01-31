package br.com.clairtonluz.sicoba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println(System.getenv("USER"));
        System.out.println(System.getenv("JDBC_DATABASE_URL"));
        SpringApplication.run(Application.class, args);
    }

}
