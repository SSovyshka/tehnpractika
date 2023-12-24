/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 *
 * @author jeyson
 */
@Configuration
@EnableWebSecurity
@KeycloakConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableConfigurationProperties(KeycloakSpringBootProperties.class)
@RequiredArgsConstructor
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    /**
     * Registers the KeycloakAuthenticationProvider with the authentication
     * manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        // simple Authority Mapper to avoid ROLE_
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }
    
    /**
     * Defines the session authentication strategy.
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    /**
     * Secure appropriate endpoints
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
               http.csrf().disable()
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers("/auth/ **").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/main/ **").authenticated()
                .antMatchers("/kc/**").hasRole("full")
                .antMatchers("/tech/**").hasRole("full")
                .antMatchers("/swagger-ui/**").hasRole("swagger");

        // disable page caching
        http
                .headers()
                .frameOptions().sameOrigin()
                .cacheControl();

    }

}
