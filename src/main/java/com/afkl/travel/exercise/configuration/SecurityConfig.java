package com.afkl.travel.exercise.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/travel/**").hasAnyAuthority("USER")
                .antMatchers("/actuator/**").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .realmName("Travel App");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("someuser").password("{noop}psw").authorities("USER")
            .and()
                .withUser("ops").password("{noop}psw").authorities("ADMIN");
    }
}
