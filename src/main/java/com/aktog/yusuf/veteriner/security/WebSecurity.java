package com.aktog.yusuf.veteriner.security;

import com.aktog.yusuf.veteriner.entity.Role;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Qualifier("customUserDetailsService")
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenFilter jwtTokenFilter;

    public WebSecurity(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            JwtTokenFilter jwtTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/v*/pet/**", "/v*/owner/**")
                .hasAuthority("ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/v*/pet/**", "/v*/owner/**")
                .hasAnyAuthority("ADMIN", "USER", "VISITOR");

        http.authorizeRequests().antMatchers(HttpMethod.POST, "/v*/pet", "/v*/owner")
                .hasAnyAuthority("ADMIN", "USER");

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/v*")
                .hasAuthority("ADMIN");

        http.authorizeRequests().antMatchers("/v*/role/**")
                .hasAuthority("ADMIN");

        http.csrf().disable()
                .authorizeRequests().antMatchers("/v*/login", "/v*/register")
                .permitAll().anyRequest().hasAnyAuthority("USER,VISITOR")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}