package pl.mswierczewski.inclouds.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.mswierczewski.inclouds.security.filters.JWTAuthorizationFilter;
import pl.mswierczewski.inclouds.services.impl.DefaultUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DefaultUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    public SecurityConfig(DefaultUserService userService, PasswordEncoder passwordEncoder, JWTAuthorizationFilter jwtAuthorizationFilter) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers(
                                "/v3/api-docs", "/v3/api-docs/",
                                "/api-docs", "/api-docs/","/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/api-docs.yaml", "/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config",
                                "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui", "/swagger-ui.html", "/webjars/**").permitAll()
                        .antMatchers("/api/auth/authenticate", "/api/user/admin-sign-up").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
