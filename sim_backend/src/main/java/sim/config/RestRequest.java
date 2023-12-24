/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.config;

import sim.exceptions.BillingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author dit51
 */
@Slf4j
public abstract class RestRequest<T,P> implements IRestRequest<T,P>{

    private Class<T> entityType;
    protected String BASE_URL;

        
    public RestRequest() {
    }

    public RestRequest(Class<T> entityType) {
        this.entityType = entityType;
    }

    /** 
    * Authentication
    */ 
    public abstract String getAccessToken();
//    {  
//        
//        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext()
//                .getAuthentication();
//        
//        KeycloakPrincipal<KeycloakSecurityContext> userPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) authentication
//                .getPrincipal();
//        
//        return userPrincipal.getKeycloakSecurityContext().getTokenString();
//    }
    
    
    @Override
    public T getEntity(String url) throws BillingException , Exception{
        
        //URL to cabinet_pobut

        HttpClient client = HttpClient.newBuilder().build();
        
//        HttpRequest request = HttpRequest.newBuilder()
//                        .uri(URI.create(url))
//                        .headers("Content-Type", "application/json")
//                        .headers("Authorization", "Bearer " + getAccessToken())
//                        .GET()
//                        .build();

        HttpRequest request = getHttpRequest(RequestMethod.GET, BASE_URL + url);

        log.debug("***************************************************************");
        log.debug("REST [GET] " + BASE_URL + url);
        
        
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        log.debug("[response CODE:"+ response.statusCode() + "]: " + response.body().toString());
        log.debug("***************************************************************");

        
        if (response.statusCode()!= 200) {
                    
            throw new BillingException(-500, "Сервіс тимчасово не працює. Спробуйте пізніше.");
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(response.body().toString(), new TypeReference<T>() { });
        return objectMapper.readValue(response.body().toString(), entityType);
    }
    
    
    @Override
    public T deleteEntity(String url) throws BillingException , Exception{
        
        HttpClient client = HttpClient.newBuilder().build();
        
//        HttpRequest request = HttpRequest.newBuilder()
//                        .uri(URI.create(url))
//                        .headers("Content-Type", "application/json")
//                        .headers("Authorization", "Bearer " + getAccessToken())
//                        .DELETE()
//                        .build();

        HttpRequest request = getHttpRequest(RequestMethod.DELETE, BASE_URL + url);
        
        log.debug("***************************************************************");
        log.debug("REST [DELETE] " + BASE_URL + url);
        
        
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        log.debug("[response CODE:"+ response.statusCode() + "]: " + response.body().toString());
        log.debug("***************************************************************");

        
        if (response.statusCode()!= 200) {
                    
            throw new BillingException(-500, "Сервіс тимчасово не працює. Спробуйте пізніше.");
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(response.body().toString(), new TypeReference<T>() { });
        return objectMapper.readValue(response.body().toString(), entityType);
    }

    @Override
    public T postEntity(String url, P payloadModel) throws BillingException, Exception {
        
        String payload = new ObjectMapper().writeValueAsString(payloadModel);

        HttpClient client = HttpClient.newBuilder().build();
        
//        HttpRequest request = HttpRequest.newBuilder()
//                        .uri(URI.create(url))
//                        .headers("Content-Type", "application/json")
//                        .headers("Authorization", "Bearer " + getAccessToken())
//                        .POST(HttpRequest.BodyPublishers.ofString(payload))
//                        .build();

        HttpRequest request = getHttpRequest(RequestMethod.POST, BASE_URL + url, payloadModel);


        log.debug("***************************************************************");
        log.debug("REST [POST] " + BASE_URL + url);
        log.debug("[payload]:" + payload);
        
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        log.debug("[response CODE:"+ response.statusCode() + "]: " + response.body().toString());
        log.debug("***************************************************************");
        
        if (response.statusCode()!= 200) {
                    
            throw new BillingException(-500, "Сервіс тимчасово не працює. Спробуйте пізніше.");
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body().toString(), entityType);
        
        
        
    }

    @Override
    public T putEntity(String url, P payloadModel) throws BillingException, Exception {
        
        String payload = new ObjectMapper().writeValueAsString(payloadModel);

        HttpClient client = HttpClient.newBuilder().build();
        
//        HttpRequest request = HttpRequest.newBuilder()
//                        .uri(URI.create(url))
//                        .headers("Content-Type", "application/json")
//                        .headers("Authorization", "Bearer " + getAccessToken())
//                        .POST(HttpRequest.BodyPublishers.ofString(payload))
//                        .build();

        HttpRequest request = getHttpRequest(RequestMethod.PUT, BASE_URL + url, payloadModel);


        log.debug("***************************************************************");
        log.debug("REST [PUT] " + BASE_URL + url);
        log.debug("[payload]:" + payload);
        
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        log.debug("[response CODE:"+ response.statusCode() + "]: " + response.body().toString());
        log.debug("***************************************************************");
        
        if (response.statusCode()!= 200) {
                    
            throw new BillingException(-500, "Сервіс тимчасово не працює. Спробуйте пізніше.");
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body().toString(), entityType);
        
        
        
    }

    
    public Map<String,String> setHttpHeaders(){
        
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getAccessToken());
        
        return headers;
    } 
    
     
    private HttpRequest getHttpRequest(RequestMethod requestMethod, String url){
        
          Builder builder = HttpRequest.newBuilder()
                                       .uri(URI.create(url));
          
          setHttpHeaders().forEach((k,v)->{
              builder.header(k, v);
          });
          
          switch(requestMethod){
          
              case GET: builder.GET(); break;
              case DELETE: builder.DELETE(); break;
          }
                  
          return builder.build();
        
    
    }

    private HttpRequest getHttpRequest(RequestMethod requestMethod, String url, P payloadModel) throws Exception{
        
          String payload = new ObjectMapper().writeValueAsString(payloadModel);
        
          Builder builder = HttpRequest.newBuilder()
                                       .uri(URI.create(url));
          
          setHttpHeaders().forEach((k,v)->{
              builder.header(k, v);
          });
          
          switch(requestMethod){
          
              case POST: builder.POST(HttpRequest.BodyPublishers.ofString(payload)); break;
              case PUT: builder.PUT(HttpRequest.BodyPublishers.ofString(payload)); break;
              
          }
                  
          return builder.build();
    
    }
    
}
