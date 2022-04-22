package com.sigma.configuration;

import com.sigma.configuration.AuthFilter;
import com.sigma.model.entity.Role;
import com.sigma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final AuthFilter authFilter;

//    @Autowired
//    private final DataSource dataSource;

//    @Bean
//    public FilterRegistrationBean<AuthFilter> someFilter() {
//        final FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(authFilter);
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setName("AuthFilter");
//        registrationBean.setOrder(Integer.MAX_VALUE);
//        return registrationBean;
//    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();

        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/signIn").permitAll()
                //.antMatchers("/users").hasAnyRole(Role.CAPTAIN.toString())
                .anyRequest().authenticated();
//                .anyRequest().permitAll(); // does everything


        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).antMatcher("/*");
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("SELECT user_name, password, enabled FROM users WHERE user_name=?;")
//                .authoritiesByUsernameQuery("SELECT user_name, role FROM users WHERE user_name=?");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B);
    }
}