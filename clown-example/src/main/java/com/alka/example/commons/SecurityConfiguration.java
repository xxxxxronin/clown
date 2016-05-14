package com.alka.example.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

/**
 * Created by lenli on 16/4/24.
 * 参考jwt https://www.toptal.com/java/rest-security-with-jwt-spring-security-and-java
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("root123").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("dba").password("root123").roles("ADMIN","DBA");//dba have two roles.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerImpl());
        http.authorizeRequests().accessDecisionManager(new DefaultAbstractAccessDecisionManager()).anyRequest().authenticated();

//        http.authorizeRequests().anyRequest().authenticated().withObjectPostProcessor(new MyFilterSecurityInterceptor());
//        http.authorizeRequests()
//                .antMatchers("/", "/home/").permitAll()
//                .antMatchers("/admin/**").access("hasRole('ADMIN')")
//                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
//                .and().formLogin()
//                .and().exceptionHandling().accessDeniedPage("/Access_Denied");

    }
}
