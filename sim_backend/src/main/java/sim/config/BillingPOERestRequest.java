/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.config;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

/**
 *
 * @author dit51
 */
@Slf4j
public class BillingPOERestRequest<T,P> extends RestRequest<T, P> {

    private String poe_AUTHURL;

    private String poe_REALM;

    private String poe_service_key;

    private String poe_service_client;

    {
        this.BASE_URL = "http://10.107.4.159:8080";
    }    
   
    public BillingPOERestRequest() {
    }

    public BillingPOERestRequest(Class<T> entityType, String poe_AUTHURL, String poe_REALM, String poe_service_client, String poe_service_key) {
        super(entityType);
        this.poe_AUTHURL = poe_AUTHURL; 
        this.poe_REALM = poe_REALM;
        this.poe_service_key = poe_service_key;
        this.poe_service_client = poe_service_client;
    }


    @Override
    public String getAccessToken(){  
        
        try (Keycloak kc = KeycloakBuilder.builder()
                .serverUrl(poe_AUTHURL)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(poe_REALM)
                .clientId(poe_service_client)
                .clientSecret(poe_service_key)
                .build()) {

            //            RealmResource realmres = kc.realm(poe_REALM);
            String accToken = kc.tokenManager().getAccessTokenString();
            return accToken;

        }
        
    }
    
    
}
