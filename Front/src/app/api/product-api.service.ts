import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../_model/User";
import {Product} from "../_model/Product";
import {Form} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class ProductApiService {
  constructor(private http: HttpClient) {
  }

  public getProduct(searchKey: string): Observable<Product[]> {
    return this.http.get<Product[]>("http://localhost:8081/api/product/find/"+searchKey);
  }
  public getProductPageable(page: number): Observable<Product[]> {
    return this.http.get<Product[]>("http://localhost:8081/api/product/all/"+2+"/"+page);
  }

  public createProduct(product: Product){
    return this.http.post<Product>("http://localhost:8081/api/product/add", product);
  }

  public deleteProduct(productId: number){

    return this.http.delete("http://localhost:8081/api/product/delete/"+productId);
  }
}