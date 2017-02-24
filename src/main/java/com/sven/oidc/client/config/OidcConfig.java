package com.sven.oidc.client.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mitre.oauth2.model.RegisteredClient;
import org.mitre.openid.connect.client.NamedAdminAuthoritiesMapper;
import org.mitre.openid.connect.client.OIDCAuthenticationFilter;
import org.mitre.openid.connect.client.OIDCAuthenticationProvider;
import org.mitre.openid.connect.client.SubjectIssuerGrantedAuthority;
import org.mitre.openid.connect.client.service.AuthRequestOptionsService;
import org.mitre.openid.connect.client.service.AuthRequestUrlBuilder;
import org.mitre.openid.connect.client.service.ClientConfigurationService;
import org.mitre.openid.connect.client.service.IssuerService;
import org.mitre.openid.connect.client.service.ServerConfigurationService;
import org.mitre.openid.connect.client.service.impl.DynamicServerConfigurationService;
import org.mitre.openid.connect.client.service.impl.PlainAuthRequestUrlBuilder;
import org.mitre.openid.connect.client.service.impl.StaticAuthRequestOptionsService;
import org.mitre.openid.connect.client.service.impl.StaticClientConfigurationService;
import org.mitre.openid.connect.client.service.impl.StaticSingleIssuerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

@Configuration
public class OidcConfig
{

    @Bean
    public OIDCAuthenticationProvider oidcProvider()
    {

        OIDCAuthenticationProvider provider = new OIDCAuthenticationProvider();
        NamedAdminAuthoritiesMapper mapper = new NamedAdminAuthoritiesMapper();
        SubjectIssuerGrantedAuthority authority = new SubjectIssuerGrantedAuthority(
                "90342.ASDFJWFA", "http://localhost:8080/openid-connect-server-webapp/");
        Set<SubjectIssuerGrantedAuthority> authoritySet = new HashSet<>();
        authoritySet.add(authority);
        mapper.setAdmins(authoritySet);
        provider.setAuthoritiesMapper(mapper);
        return provider;
    }

    //@Bean
    public StaticSingleIssuerService staticIssuerService()
    {
        StaticSingleIssuerService service = new StaticSingleIssuerService();
        service.setIssuer("https://accounts.google.com");
        return service;

    }

    @Bean
    public DynamicServerConfigurationService dynamicServerConfigurationService()
    {

        return new DynamicServerConfigurationService();
    }

    @Bean
    public StaticClientConfigurationService staticClientConfigurationService()
    {
        StaticClientConfigurationService service = new StaticClientConfigurationService();
        
        Map<String, RegisteredClient> clients = new HashMap<>();
        
        RegisteredClient google = new RegisteredClient();
        google.setClientName("");
        google.setClientId("");
        google.setClientSecret("");
        Set<String> redirectUris = new HashSet<>();
        redirectUris.add("");
        google.setRedirectUris(redirectUris);
        Set<String> scopes = new HashSet<>();
        scopes.add("openid");
        scopes.add("email");
        scopes.add("profile");
        google.setScope(scopes); 
        clients.put(google.getClientName(), google);
        

        
        
        service.setClients(clients);
        return service;
    }

    @Bean
    public StaticAuthRequestOptionsService staticAuthRequestOptionsService()
    {
        return new StaticAuthRequestOptionsService();
    }

    @Bean
    public PlainAuthRequestUrlBuilder plainAuthRequestUrlBuilder()
    {
        return new PlainAuthRequestUrlBuilder();
    }

    @Bean
    public ProviderManager providerManager(OIDCAuthenticationProvider oidcProvider) 
    {
    
        List<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
        authenticationProviderList.add(oidcProvider);
        ProviderManager authenticationManager = new ProviderManager(
                authenticationProviderList);
        return authenticationManager;
    }
    
    @Bean
    public OIDCAuthenticationFilter oidcFilter(ProviderManager authenticationManager,
            IssuerService issuerService, ServerConfigurationService servers,
            ClientConfigurationService clients, AuthRequestOptionsService authOptions,
            AuthRequestUrlBuilder authRequestBuilder)
    {
        OIDCAuthenticationFilter filter = new OIDCAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setIssuerService(issuerService);
        filter.setServerConfigurationService(servers);
        filter.setClientConfigurationService(clients);
        filter.setAuthRequestOptionsService(authOptions);
        filter.setAuthRequestUrlBuilder(authRequestBuilder);
        return filter;
    }
}
