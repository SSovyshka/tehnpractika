/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.config;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author dit51
 */
@Slf4j
@Component
public class DbInitFilter implements Filter {

    public static String uname;
    public static String UnameLog;
    public static String APILog;
    private final ThreadLocal methodLog = new ThreadLocal();
//    public static String methodLog;
    public static String DBLog;
    public static String IPaddr;
    public static String jsonLog;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        
        boolean flFile = false;
        if(httpRequest.getContentType() != null){
          if(httpRequest.getContentType().contains("multipart/form-data")){
            flFile = true;
          }
        }
        MultiReadHttpServletRequest requestWrapper = null;
        if(!flFile)
            requestWrapper = new MultiReadHttpServletRequest(httpRequest); 
        
        //MultiReadHttpServletRequest requestWrapper = new MultiReadHttpServletRequest(httpRequest);
        //requestWrapper.setCharacterEncoding("UTF-8");

        try {
            KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();
            KeycloakPrincipal<KeycloakSecurityContext> userPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) authentication
                .getPrincipal();
          
            uname = userPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername();
            Integer pernr = (Integer) userPrincipal.getKeycloakSecurityContext().getToken().getOtherClaims().get("employeeID");
            String ldapidStr  = (String) userPrincipal.getKeycloakSecurityContext().getToken().getOtherClaims().get("LDAP_ID");
            String uFullName = uname + " (" + userPrincipal.getKeycloakSecurityContext().getToken().getName() + ")";
      

            UserContextHolder.setUser(new CurrentUser(uname,
                                                      userPrincipal.getKeycloakSecurityContext().getToken().getName(),
                                                      httpRequest.getRemoteAddr(),
                                                      !flFile ? requestWrapper.getBody(): null,
                                                      httpRequest.getMethod(),
                                                      httpRequest.getRequestURL().toString(),
                                                      uFullName,
                                                      userPrincipal.getKeycloakSecurityContext().getToken().getSubject(),
                                                      pernr,
                                                      ldapidStr != null ? UUID.fromString(ldapidStr) : null
                                                      ));


                log.info("{} HTTP REQUEST: {} {}", uFullName, httpRequest.getMethod(), httpRequest.getRequestURL().toString());


        } catch (NullPointerException ex) {

        }
        
        if(flFile) {
           chain.doFilter(request, response);
        } else {
           chain.doFilter(requestWrapper, response);
        }   
        
        //chain.doFilter(httpRequest, response);
    }

    @Override
    public void destroy() {
    }

}
