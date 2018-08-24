package com.lw.dmappserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    @Autowired
    private Environment env;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.authorizeRequests().anyRequest().permitAll();
        http.csrf().disable();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (env.getProperty("debug").equals("true")) {
            registry.addMapping("/**")
                    .allowedMethods("*")
                    .allowedOrigins("*");
        }
    }



    public static void main(String[] args) {
        String posted = args[0];
        String existing = args[1];
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPosted = encoder.encode(posted);
        String encodedExisting = encoder.encode(existing);

        System.out.println("-------- Trivial --------");
        boolean isMatch = encoder.matches(existing, encodedExisting);
        System.out.println("existing" + (isMatch ? " does " : " does not ") +  "match existing");
        isMatch = encoder.matches(posted, encodedPosted);
        System.out.println("posted" + (isMatch ? " does " : " does not ") +  "match posted");

        System.out.println("-------- Reflexive --------");
        isMatch = encoder.matches(existing, encodedPosted);
        System.out.println("existing" + (isMatch ? " does " : " does not ") +  "match posted");
        isMatch = encoder.matches(posted, encodedExisting);
        System.out.println("posted" + (isMatch ? " does " : " does not ") +  "match existing");
    }
}