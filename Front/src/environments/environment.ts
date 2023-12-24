
export const environment = {
  production: false,
  apiUrl: '/api',
  keycloak: {
    // Url of the Identity Provider
    issuer: 'http://localhost:9990',
    // Realm
    realm: 'magazynplus',
    clientId: 'frontend'
  },
};
