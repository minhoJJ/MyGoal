package com.narsm.web.module.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.narsm.web.module.account.application.AccountService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final AccountService accountService;
    private final DataSource dataSource;
	
	@Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .mvcMatchers("/", "/login", "/sign-up", "/check-email-token",
                "/email-login", "/check-email-login", "/login-link", "/login-by-email").permitAll() // "/login-by-email"을 추가해줍니다.
        .mvcMatchers(HttpMethod.GET, "/profile/*").permitAll()
        .anyRequest().authenticated();
        http.formLogin()
        	.loginPage("/login")
        	.permitAll();
        http.logout()
        	.logoutSuccessUrl("/");
        http.rememberMe()
        	.userDetailsService(accountService)
        	.tokenRepository(tokenRepository());
        return http.build();
    }
	
	@Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
	
    @Bean
    protected WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        		.mvcMatchers("/node_modules/**", "/images/**")
        		.antMatchers("/h2-console/**");
    }
}