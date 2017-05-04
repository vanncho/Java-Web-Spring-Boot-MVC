package com.onlinebusreservation.configuration;

import com.onlinebusreservation.areas.user.services.BasicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private BasicUserService basicUserService;

    @Autowired
    public SecurityConfiguration(BasicUserService basicUserService) {

        this.basicUserService = basicUserService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(this.basicUserService).passwordEncoder(getBCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/", "/user/register/**", "/css/**", "/images/**", "/bootstrap/**", "/scripts/**", "/jquery/**", "/connect/**", "/routes/**", "/contacts/**").permitAll()
                .antMatchers("/admin/**", "/users/**", "/buses/**", "/feedback/feedbacks/**", "/info/**").access("hasRole('ADMIN')")
                .antMatchers("/user/**", "/booking/**", "/messages/**").access("hasRole('USER')")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/user/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .rememberMe()
                .rememberMeParameter("remember")
                .key("encryptionKey")
                .rememberMeCookieName("remcookname")
                .tokenValiditySeconds(100000)
                .and()
                .logout().logoutSuccessUrl("/user/login?logout").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized")
        .and()
        .csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
