package com.example.project.security.settings;

import com.example.project.security.service.UserDetailSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private UserDetailSecurityService userDetailSecurityService;

    @Autowired
    public SecurityConfig(UserDetailSecurityService userDetailSecurityService) {
        this.userDetailSecurityService = userDetailSecurityService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(new AntPathRequestMatcher("/", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/id", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/delete/id", "GET")).hasRole("MODERATION")
                                .requestMatchers(new AntPathRequestMatcher("/login-user", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/register","POST")).permitAll()
                                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login-user").permitAll()
                        .defaultSuccessUrl("/", true))
                //.failureUrl("/login-user.html?error=true")) //целевая страница после неудачного входа в систему
                .userDetailsService(userDetailSecurityService)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
