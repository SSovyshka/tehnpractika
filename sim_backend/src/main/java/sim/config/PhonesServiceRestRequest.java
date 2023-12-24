/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.config;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author dit51
 */
@Slf4j
public class PhonesServiceRestRequest<T,P> extends RestRequest<T, P> {

    {
        this.BASE_URL = "http://10.107.25.32:8080";
    }
    
    public PhonesServiceRestRequest() {       
    }

    public PhonesServiceRestRequest(Class<T> entityType) {
        super(entityType);
    }


    @Override
    public String getAccessToken(){  
        //без авторизації Keycloak
        return null;
    }

    @Override
    public Map<String,String> setHttpHeaders(){
        
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
            
        return headers;
    } 
    
    
}
