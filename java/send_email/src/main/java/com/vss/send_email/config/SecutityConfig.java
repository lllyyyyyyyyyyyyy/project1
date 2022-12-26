package com.vss.send_email.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecutityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity security) throws Exception {
//        security.authorizeRequests()
//                .antMatchers("/list-users").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                    .usernameParameter("userName")
//                    .defaultSuccessUrl("/list-users")
//                    .permitAll()
//                .and()
//                .logout().logoutSuccessUrl("/").permitAll()
//                ;
        security.httpBasic().disable();
//                .httpBasic().disable();
        ;
    }
}
