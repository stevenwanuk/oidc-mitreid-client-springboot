package com.sven.oidc.client.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mitre.openid.connect.client.model.IssuerServiceResponse;
import org.mitre.openid.connect.client.service.IssuerService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "oidc")
public class StaticMultipleIssuerService implements IssuerService
{

    Map<String, String> issuer;

    public Map<String, String> getIssuer()
    {
        return issuer;
    }

    public void setIssuer(Map<String, String> issuer)
    {
        this.issuer = issuer;
    }

    @Override
    public IssuerServiceResponse getIssuer(HttpServletRequest request)
    {
        System.out.println(request.getParameterMap());
        String idp = request.getParameter("idp");
        return new IssuerServiceResponse(issuer.get(idp), null, null);
    }
}
