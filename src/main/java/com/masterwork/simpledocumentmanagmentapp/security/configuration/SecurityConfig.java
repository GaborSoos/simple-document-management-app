package com.masterwork.simpledocumentmanagmentapp.security.configuration;

import com.masterwork.simpledocumentmanagmentapp.security.component.JwtAuthenticationExceptionHandler;
import com.masterwork.simpledocumentmanagmentapp.security.filter.JwtAuthenticationFilter;
import com.masterwork.simpledocumentmanagmentapp.security.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  private final JwtAuthenticationExceptionHandler jwtAuthExceptionHandler;
  private final UserServiceImpl userServiceImpl;

  public static final String[] PRIVATE_ENDPOINTS = {
      "/admin/**",
      "/document/**",
      "/doctag/**",
      "/doctype/**",
      "/partner/**"
  };

  public SecurityConfig(JwtAuthenticationExceptionHandler jwtAuthExceptionHandler, UserServiceImpl userServiceImpl) {
    this.jwtAuthExceptionHandler = jwtAuthExceptionHandler;
    this.userServiceImpl = userServiceImpl;
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(userServiceImpl);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthExceptionHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(PRIVATE_ENDPOINTS).authenticated()
        .antMatchers("/**").permitAll();
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
