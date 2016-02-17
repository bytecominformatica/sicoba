package br.com.clairtonluz.sicoba.config.security;

import br.com.clairtonluz.sicoba.filter.CsrfTokenResponseHeaderBindingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

/**
 * Created by clairtonluz on 31/01/16.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);
//        http
//                .authorizeRequests()
//                .antMatchers("/index.html", "/#/login", "/login.html", "/", "/bower_components/**", "/dist/**"
//                        ,"/app/styles/**", "/app/directives/**","/app/template/**","/app/views/**").permitAll()
//                .anyRequest().authenticated()
//                .and().formLogin().loginPage("/#/login").permitAll()
//                .and().logout().permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().usersByUsernameQuery(
//                "select username,password, enabled from users where username=?")
//                .authoritiesByUsernameQuery(
//                        "select u.username, r.role from users u left join user_roles r on u.id = r.user_id where u.username=?");
        auth.inMemoryAuthentication()
                .withUser("admin").password("admin").roles("USER");
    }

}
