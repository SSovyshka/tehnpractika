/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.config;

/**
 *
 * @author jeyson
 */
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomKeycloakSpringBootConfigResolver extends KeycloakSpringBootConfigResolver {
    private final KeycloakDeployment keycloakDeployment;

    public CustomKeycloakSpringBootConfigResolver(KeycloakSpringBootProperties properties) {
        keycloakDeployment = KeycloakDeploymentBuilder.build(properties);
    }
    @Override
    public KeycloakDeployment resolve(HttpFacade.Request facade) {
        return keycloakDeployment;
    }

//    private KeycloakDeployment keycloakDeployment;
//    private final ConcurrentHashMap<String, KeycloakDeployment> cache = new ConcurrentHashMap<>();
////    private AdapterConfig adapterConfig;
//
//    @Override
//    public KeycloakDeployment resolve(HttpFacade.Request request) {
//        if (keycloakDeployment != null) {
//            return keycloakDeployment;
//        }
//
//        String path = request.getURI();
//        String kc = "billing";
//        String[] realm = path.substring(path.indexOf("api/")).split("/");
//        if (realm.length > 1) {
//            kc = realm[1];
//            if (kc.contains("?")) {
//                kc = kc.split("\\?")[0];
//            }
//
//        }
//        KeycloakDeployment deployment = cache.get(kc);
//        if (null == deployment) {
//            // not found on the simple cache, try to load it from the file system
//            InputStream is = getClass().getResourceAsStream("/" + kc + "-keycloak.json");
//            if (is == null) {
//                throw new IllegalStateException("Not able to find the file /" + kc + "-keycloak.json");
//            }
//            deployment = KeycloakDeploymentBuilder.build(is);
//            cache.put(kc, deployment);
//        }
//
////        keycloakDeployment = KeycloakDeploymentBuilder.build(adapterConfig);
//
//        return cache.get(realm);
//    }

//    void setAdapterConfig(AdapterConfig adapterConfig) {
//        this.adapterConfig = adapterConfig;
//    }

}
