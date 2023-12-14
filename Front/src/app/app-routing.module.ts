import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AccessDeniedComponent} from "./access-denied/access-denied.component";
import {AuthGuard} from "./auth/auth.guard";
import {UserInfoComponent} from "./user-info/user-info.component";
import {HomeComponent} from "./home/home.component";
import {MainPageComponent} from "./main-page/main-page.component";
import {SaveNewProductComponent} from "./save-new-product/save-new-product.component";

const routes: Routes = [
  {
    path: 'access-denied',
    component: AccessDeniedComponent,
    canActivate: [AuthGuard]
  },
  {path: 'home', component: HomeComponent},
  {
    path: 'main',
    component: MainPageComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'user-info',
    component: UserInfoComponent,
    canActivate: [AuthGuard],
    data: {roles: ['user']}
  },
  {
    path: 'product/save',
    component: SaveNewProductComponent,
    canActivate: [AuthGuard],
    data: {roles: ['user']}
  },
  {
    path: 'supplier',
    component: SaveNewProductComponent,
    canActivate: [AuthGuard],
    data: {roles: ['user']}
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
