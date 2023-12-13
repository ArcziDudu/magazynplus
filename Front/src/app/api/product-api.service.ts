import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../_model/User";
import {Product} from "../_model/Product";

@Injectable({
  providedIn: 'root'
})
export class ProductApiService {
  constructor(private http: HttpClient) {
  }

  public getProduct(searchKey: string): Observable<Product[]> {
    return this.http.get<Product[]>("http://localhost:8081/api/product/find/"+searchKey);
  }
}
