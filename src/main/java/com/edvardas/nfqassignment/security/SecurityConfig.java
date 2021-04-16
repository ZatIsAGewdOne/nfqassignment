package com.edvardas.nfqassignment.security;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .exceptionHandling()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("api/customer").permitAll()
//                .antMatchers("api/customer/**").permitAll()
//                .antMatchers("api/specialist/**").authenticated()
//                .and()
//                .formLogin()
//                .loginPage("api/specialist/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .successForwardUrl("api/specialist/login")
//                .failureForwardUrl("/")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("api/specialist/logout")
//                .permitAll()
//                .invalidateHttpSession(true)
//                .logoutSuccessUrl("/")
//                .permitAll();
        http.csrf().disable()
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .antMatchers("api/customer/**").permitAll()
                .antMatchers("api/specialist/**").authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("api/specialist/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutUrl("/api/specialist/logout")
                .permitAll();

    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        val users = Arrays.asList(
                User.withUsername("edva").password(getPasswordEncoder().encode("password")).roles("SPECIALIST").build(),
                User.withUsername("specialist132").password(getPasswordEncoder().encode("testSpecialistPassword142")).roles("SPECIALIST").build(),
                User.withUsername("testSpecialist").password(getPasswordEncoder().encode("testSpecialistPassword")).roles("SPECIALIST").build()
        );
        return new InMemoryUserDetailsManager(users);
    }

    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
