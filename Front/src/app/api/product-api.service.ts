import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "../_model/Product";

@Injectable({
  providedIn: 'root'
})
export class ProductApiService {
  constructor(private http: HttpClient) {
  }

  public getProduct(searchKey: string): Observable<Product[]> {
    return this.http.get<Product[]>("http://localhost:8081/api/product/find/" + searchKey);
  }

  public getProductPageable(page: number): Observable<Product[]> {

    return this.http.get<Product[]>("http://localhost:8081/api/product/all/" + page);
  }

  public createProduct(product: Product) {
    return this.http.post("http://localhost:8081/api/product/add", product);
  }

  public deleteProduct(productId: number) {
    return this.http.delete("http://localhost:8081/api/product/delete/" + productId);
  }

  getProductDetails(id: number) {
    return this.http.get<Product>("http://localhost:8081/api/product/details/" + id);
  }

  updateProduct(product: Product) {
    return this.http.patch<Product>("http://localhost:8081/api/product/edit", product);
  }

  uploadFile(fileType: string, formData: FormData) {
    return this.http.post("http://localhost:8081/api/file/upload/" + fileType, formData);
  }
}
