package com.sven.oidc.client.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mitre.openid.connect.model.DefaultUserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController
{

    @RequestMapping("test")
    public DefaultUserInfo test(@RequestParam String idp, HttpServletRequest request, HttpServletResponse response) {
        
        System.out.println(request.getParameterMap());
        //request.getAttribute("userInfo");
        DefaultUserInfo info =  ((DefaultUserInfo)request.getAttribute("userInfo"));
        info.setSource(null);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        
        return info;
        
    }
    
    
}
