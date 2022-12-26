package com.vss.lynt.config;

import com.vss.lynt.service.impliment.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/users/**").hasAuthority("ADMIN")
                .antMatchers("/classroom/**").hasAnyAuthority("ADMIN","STUDENT","TEACHER")
                .antMatchers("/student").hasAnyAuthority("ADMIN", "TEACHER", "STUDENT")
                .antMatchers("/student/update-{id}").hasAuthority("STUDENT")
                .antMatchers("/student/delete-image/{id}").hasAuthority("STUDENT")
                .antMatchers("/student/update-infor-{id}").hasAuthority("STUDENT")
                .antMatchers("/subject").hasAnyAuthority("ADMIN","STUDENT","TEACHER")
                .antMatchers("/subject/insert-subject").hasAuthority("ADMIN")
                .antMatchers("/subject/insert-class-subject").hasAuthority("ADMIN")
                .antMatchers("/subject/update-subject-{id}").hasAuthority("ADMIN")
                .antMatchers("/subject/delete-subject-{id}").hasAuthority("ADMIN")
                .antMatchers("/subject/upload-document-{id}").hasAuthority("TEACHER")
                .antMatchers("/subject/download-document-{filename}").hasAuthority("STUDENT")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/checklogin")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .usernameParameter("userName")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
        ;
    }
}