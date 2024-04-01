package com.projectSpring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//registra beans -- es decir la fabrica de beans en el contenedor de spring
@Configuration
public class WebSecurityConfig {

    //usuarios en memoria
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails user1 = User.builder()
                .username("user1")
                .password("{bcrypt}$2a$10$dS5RikHYahgKEkapahkjQeQlLzpwr/RvO1eNoZ2YMGJYx7yiQ0w/C")
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$dS5RikHYahgKEkapahkjQeQlLzpwr/RvO1eNoZ2YMGJYx7yiQ0w/C")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/personas").permitAll()
                        .requestMatchers("/personas/nueva").hasAnyRole("ADMIN")
                        .requestMatchers("personas/editar/*","personas/eliminar/*").hasRole("ADMIN")
                        .anyRequest().authenticated()
        ).httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex.accessDeniedPage("/403"));
        return httpSecurity.build();
    }
}
