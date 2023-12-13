import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {AppComponent} from './app.component';
import {initializer} from "../utils/app-init";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {AppRoutingModule} from './app-routing.module';
import {AccessDeniedComponent} from './access-denied/access-denied.component';
import {UserInfoComponent} from './user-info/user-info.component';
import {HttpClientModule} from "@angular/common/http";
import {HeaderComponent} from "./header/header.component";
import {HomeComponent} from "./home/home.component";
import {MainPageComponent} from "./main-page/main-page.component";
import {MatTableModule} from "@angular/material/table";
import {MatCardModule} from "@angular/material/card";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {MatButtonModule} from "@angular/material/button";
import {MatDividerModule} from "@angular/material/divider";
import {MatIconModule} from "@angular/material/icon";
import {MatPaginatorModule} from "@angular/material/paginator";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    AccessDeniedComponent,
    UserInfoComponent,
    HeaderComponent,
    HomeComponent,
    MainPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    KeycloakAngularModule,
    HttpClientModule,
    MatFormFieldModule,
    MatInputModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatCardModule,
    MatDatepickerModule,
    MatCardModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonModule,
    MatButtonModule,
    MatDividerModule,
    MatIconModule,
    MatPaginatorModule,
    BrowserAnimationsModule,
    FormsModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      deps: [KeycloakService],
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
