
export const environment = {
  production: false,
  apiUrl: '/api',
  keycloak: {
    // Url of the Identity Provider
    issuer: 'http://localhost:8080',
    // Realm
    realm: 'magazynplus',
    clientId: 'frontend'
  },
};
