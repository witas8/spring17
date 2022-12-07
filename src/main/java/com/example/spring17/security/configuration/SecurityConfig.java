package com.example.spring17.security.configuration;

import com.example.spring17.security.filter.CustomAuthenticationFilter;
import com.example.spring17.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.example.spring17.utils.ConstantURL.REFRESH_TOKEN_URL;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    //https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring
    //https://github.com/appdev1449/com.appdev.springtutorials/blob/spring-security-5.7-authentication/security-5.7/src/main/java/com/appdev/security/AppSecurityConfiguration.java
    //https://www.baeldung.com/spring-security-login

    private final UserDetailsService userDetailsService;

    //private PasswordEncoder bCryptPasswordEncoder;

//    @Value("${server.servlet.session.cookie.name:JSESSSIONID}")
//    private String cookieName;

    @Bean
    //@Before("filterChain")
    public PasswordEncoder passwordEncoder() {
        //bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Before("filterChain")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //http://localhost:8080/spring17/curiosity/all
    //http://localhost:8080/spring17/users/all
    @Bean("filterChain")
    public SecurityFilterChain filterChain(HttpSecurity http, PasswordEncoder bCryptPasswordEncoder) throws Exception {
    //public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager);
        customAuthenticationFilter.setFilterProcessesUrl("/spring17/login");

        return http
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests()
                .antMatchers("/spring17/login/**", "/spring17/token/refresh/**").permitAll() //REFRESH_TOKEN_URL
                .antMatchers("/spring17/user/all").permitAll()
                //swagger
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/configuration/security").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-ui/*").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .authenticationManager(authenticationManager)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                //.formLogin(v -> v.defaultSuccessUrl("/spring17/curiosity/all", true)) //any http
                //.logout().logoutSuccessUrl("/").clearAuthentication(true).deleteCookies(cookieName)
                //.and()
                .build();
    }

}
