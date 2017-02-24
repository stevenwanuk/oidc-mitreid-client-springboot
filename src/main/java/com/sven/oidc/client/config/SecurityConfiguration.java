package com.sven.oidc.client.config;

import org.mitre.openid.connect.client.OIDCAuthenticationFilter;
import org.mitre.openid.connect.client.OIDCAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    private OIDCAuthenticationProvider oidcAuthenticationProvider;

    @Autowired
    private OIDCAuthenticationFilter oidcAuthenticationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(oidcAuthenticationProvider);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {

        LoginUrlAuthenticationEntryPoint entryPoint =
                new LoginUrlAuthenticationEntryPoint("/openid_connect_login");
        entryPoint.setUseForward(true);
        
        http.exceptionHandling().authenticationEntryPoint(entryPoint);
        http.addFilterBefore(oidcAuthenticationFilter, BasicAuthenticationFilter.class);

        http.authorizeRequests().anyRequest().authenticated();
        // http.authorizeRequests().antMatchers("/api/users/login").permitAll() // Permit
        // // access for
        // // all to
        // // login REST
        // // service
        // .antMatchers("/").permitAll() // Neccessary to permit access to default
        // // document
        // .anyRequest().authenticated().and() // All other requests require
        // // authentication
        // .httpBasic().and().logout().and().csrf().disable();
    }
}
