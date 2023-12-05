package com.seng696.Seng696.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests()
//
//                .antMatchers("/login").permitAll()
//                .antMatchers("/actuator/*").permitAll()
//                .antMatchers("/user_registration").permitAll()
//                .antMatchers("/generate-otp").permitAll()
//                .antMatchers("/validate-otp").permitAll()
//                .antMatchers("/checkUser").permitAll()
//                .antMatchers("/userRefresh").permitAll()
//                .antMatchers("/encrypt").permitAll()
//                .antMatchers("/decrypt").permitAll()
//                .anyRequest().authenticated()
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/login").permitAll()
                .antMatchers("/api/user_registration").permitAll()
                .antMatchers("/api/categories").permitAll()
                .antMatchers("/api/questions/*").permitAll()
                .antMatchers("/api/scores").permitAll()
                .antMatchers("/api/leaderboard/*").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore((Filter) jwtRequestFilter, (Class<? extends Filter>) UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}