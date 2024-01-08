import {Injectable} from "@angular/core";
import {KeycloakService} from "keycloak-angular";
import {Router} from "@angular/router";

@Injectable({
  providedIn: "root"
})
export class AuthService {

  constructor(private keycloakService: KeycloakService,
              private router: Router) {
  }

  public getUsername(): string {
    return this.keycloakService.getUsername();
  }

  logout() {
    this.keycloakService.logout().then(() => {
      console.log('logout');
    }).catch((error) => {
      console.error('error', error);
    });
  }

}
