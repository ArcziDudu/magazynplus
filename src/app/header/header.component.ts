import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';
import {AuthService} from "../auth/auth.service";
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})

export class HeaderComponent {
  constructor(private authService: AuthService) {
  }

  public logout() {
    this.authService.logout();
  }
}
