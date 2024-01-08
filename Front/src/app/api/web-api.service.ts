import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {User} from "../_model/User";

@Injectable({
  providedIn: 'root'
})
export class WebApiService {

  constructor(private http: HttpClient) {
  }

  public getUserInfo(): Observable<string> {
    return this.http.get(`${environment.apiUrl}/userInfo1`, {responseType: 'text'});
  }

  getUser(): Observable<User> {
    const url = 'http://localhost:8081/api/user/info/2';
    return this.http.get<User>(url);
  }
}
