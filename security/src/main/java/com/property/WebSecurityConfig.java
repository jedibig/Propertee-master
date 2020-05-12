package com.property;

import com.property.dao.LoginRepository;
import com.property.dto.UserLogin;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
//@ConditionalOnBean(LoginRepository.class)
@AllArgsConstructor
@DependsOn("urlProperties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    LoginRepository repository;
    UrlProperties properties;

    public static void main(String[] args) {
        SpringApplication.run(WebSecurityConfig.class);
    }

    @Bean
    public PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(s -> {
            UserLogin user = repository.findById(s)
                    .orElseThrow(() -> new UsernameNotFoundException(""));

            return new User(user.getEmail(), user.getPassword(),
                    user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        });
    }

    //securityContext: application: securityContextHolder: for each user
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        System.out.println("mapping: " + properties.getMappingaccess());

        for ( Map.Entry<String, String> urlRole: properties.getMappingaccess().entrySet() ) {
            http.authorizeRequests().antMatchers(urlRole.getValue()).hasRole(urlRole.getKey());
        }
        http.authorizeRequests().antMatchers("/**").permitAll()
                .anyRequest().authenticated();
        http.formLogin().and()
                .exceptionHandling().accessDeniedPage("/accessDenied");
    }

    @Bean
    ApplicationRunner runner(LoginRepository repository){
        return a -> {
            repository.save(UserLogin.builder().email("admin").password("admin").roles(Collections.singletonList("ROLE_ADMIN")).build());
            repository.save(UserLogin.builder().email("user").password("user").roles(Collections.singletonList("ROLE_USER")).build());

            System.out.println("success");
        };
    }
}
