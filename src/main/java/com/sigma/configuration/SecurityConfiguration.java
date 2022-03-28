package com.sigma.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder().encode("user"))
//                .roles("user")
//                .and()
//                .withUser("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("admin", "user");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin();

//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests().anyRequest().permitAll();
//        http.addFilter(new AuthFilter(authenticationManagerBean()));

//        http.authorizeRequests()
//                .antMatchers("/admin/*").hasAnyRole("admin", "captain")
//                .antMatchers("/captain/*").hasRole("captain")
//                .antMatchers("/").hasAnyRole("admin", "captain")
//                .and()
//                .formLogin()
//                    .defaultSuccessUrl("/users")
//                    .permitAll()
//                .and()
//                .logout().logoutSuccessUrl("/").permitAll()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        ;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                //.passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("SELECT user_name, password, enabled FROM users WHERE user_name=?;")
//                .authoritiesByUsernameQuery("SELECT user_name, role FROM users WHERE user_name=?");

    }
}