package com.helpdesk.HelpDesk.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;


@Configuration
public class LoginConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        http.authorizeRequests()
                .antMatchers("/login", "/css/**", "/js/**", "/images/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .successHandler((request, response, authentication) -> redirectStrategy.sendRedirect(request, response, "/loginSuccess"))
                .failureHandler((request, response, authentication) -> redirectStrategy.sendRedirect(request, response, "/login"));
    }
}
