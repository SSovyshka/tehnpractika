import { KeycloakConfig } from "keycloak-js";

const keycloakConfig: KeycloakConfig = {
    url: 'http://####/auth/',
    realm: 'simcards',
    clientId: 'frontend'
};

export const environment = {
  production: true,
  apiUrl: 'http://####/sims/',
  keycloakConfig: keycloakConfig,
  defaultLocale: 'uk',
};
