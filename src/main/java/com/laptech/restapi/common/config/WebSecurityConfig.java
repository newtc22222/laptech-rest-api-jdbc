package com.laptech.restapi.common.config;

import com.laptech.restapi.jwt.config.JwtAuthenticationEntryPoint;
import com.laptech.restapi.jwt.filter.JwtRequestFilter;
import com.laptech.restapi.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <a href=https://stackoverflow.com/questions/56349296/how-to-do-for-authorize-endpoints-for-anonymous-user-with-spring-security>Spring security</a>
 *
 * @author Nhat Phi
 * @since 2023-01-06
 */
@SuppressWarnings({"Security custom config", "deprecation"})
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtService jwtService;

    private static final String[] WHITE_LIST = new String[]{
            "/api/v1/auth/*",
            "/swagger-ui.html",
            "/swagger-ui.html*/**",
            "/swagger-resources/**",
            "/swagger-*/**", // swagger-ui/index.html
            "/webjars/**",
            "/v*/api-docs"
    };

    private static final String[] GET_METHOD_API_LIST = new String[]{
            "brands",
            "brands/*/products",
            "banners",
            "categories",
            "categories/*/products",
            "comments",
            "discounts",
            "labels",
            "feedbacks",
            "files/**",
            "products",
            "products/**"   
    };

    private static String[] getGetMethodApiList() {
        for (int i = 0; i < GET_METHOD_API_LIST.length; i++) {
            GET_METHOD_API_LIST[i] = "/api/v1/" + GET_METHOD_API_LIST[i];
        }
        return GET_METHOD_API_LIST;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers(HttpHeaders.ALLOW).permitAll()
                .antMatchers(WHITE_LIST).permitAll()
                .antMatchers(HttpMethod.GET, getGetMethodApiList()).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(jwtService)
                .passwordEncoder(passwordEncoder());
    }
}
