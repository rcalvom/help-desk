package com.helpdesk.HelpDesk.Configuration;

import com.helpdesk.HelpDesk.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

@Configuration
@EnableWebSecurity
public class ConfigurationImpl extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDAO userDAO;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDAO);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        http.headers().cacheControl();
        http.cors().and().csrf().disable();
        http
                .oauth2Login()
                    .loginPage("/login")
                    .successHandler((request, response, authentication) -> redirectStrategy.sendRedirect(request, response, "/loginSuccess"))
                    .failureHandler((request, response, authentication) -> redirectStrategy.sendRedirect(request, response, "/login"))
                    .and()
                .authorizeRequests()
                    // .antMatchers("/admin/**").hasRole("AGENT")
                    // .antMatchers("/agent/**", "/admin/**").hasRole("USER")
                    .antMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/");

    }

}
