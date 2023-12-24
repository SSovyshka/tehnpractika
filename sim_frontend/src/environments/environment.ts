
import { KeycloakConfig } from "keycloak-js";

const keycloakConfig: KeycloakConfig = {
    url: 'http://#####/auth/',
    realm: 'simcards',
    clientId: 'frontend'
};

export const environment = {
  production: false,
  apiUrl: 'http://#####/sims/',
  keycloakConfig: keycloakConfig,
  defaultLocale: 'uk',
};

