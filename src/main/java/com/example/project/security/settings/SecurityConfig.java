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
@EnableWebSecurity
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
                        auth.requestMatchers(
                                        new AntPathRequestMatcher("/", "GET"),
                                        new AntPathRequestMatcher("/developer", "GET"),
                                        new AntPathRequestMatcher("/register", "POST"),
                                        new AntPathRequestMatcher("/register", "GET"),
                                        new AntPathRequestMatcher("/css/**"),
                                        new AntPathRequestMatcher("/images/**"),
                                        new AntPathRequestMatcher("/profile"),
                                        new AntPathRequestMatcher("/js/**")
                                ).permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/requests/create", "GET"),
                                        new AntPathRequestMatcher("/create", "POST"),
                                        new AntPathRequestMatcher("/completed-requests", "GET"),
                                        new AntPathRequestMatcher("/completed-requests/{id}", "GET")
                                ).hasRole("USER")
                                .requestMatchers(
                                        new AntPathRequestMatcher("/requests/create-response", "GET"),
                                        new AntPathRequestMatcher("/requests/create-response", "POST")
                                ).hasRole("ADMIN")
                                .requestMatchers(
                                        new AntPathRequestMatcher("/responses/all", "GET")
                                ).hasAnyRole("USER", "ADMIN", "MODERATION")
                                .requestMatchers(
                                        new AntPathRequestMatcher("/user/all", "GET"),
                                        new AntPathRequestMatcher("/user/remove/{login}", "GET"),
                                        new AntPathRequestMatcher("/user/details/{login}", "POST"),
                                        new AntPathRequestMatcher("/responses/all_moderator", "GET"),
                                        new AntPathRequestMatcher("/responses/delete/{responseId}", "GET"),
                                        new AntPathRequestMatcher("/requests/all_moderator", "GET"),
                                        new AntPathRequestMatcher("/requests/moderator/details/{id}", "GET"),
                                        new AntPathRequestMatcher("/requests/delete/{id}")
                                ).hasRole("MODERATION")
                                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login-user").permitAll()
                        .defaultSuccessUrl("/", true))
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/").permitAll()
                        .invalidateHttpSession(true) // инвалидация текущей сессии
                        .deleteCookies("JSESSIONID", "cookieName1", "cookieName2") // удаление куки-файлов
                )
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
