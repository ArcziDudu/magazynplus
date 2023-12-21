import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Supplier} from "../_model/Supplier";
import {Observable} from "rxjs";
import {Product} from "../_model/Product";

@Injectable({
  providedIn: 'root'
})
export class SupplierApiService {
  constructor(private http: HttpClient) {
  }

  public createSupplier(supplier: Supplier) {
    return this.http.post<Supplier>("http://localhost:8081/api/supplier/create", supplier);
  }
  public getAllSuppliers(): Observable<Supplier[]> {
    return this.http.get<Supplier[]>("http://localhost:8081/api/supplier/all");
  }
  public deleteSupplier(supplierId: number){
   return this.http.delete("http://localhost:8081/api/supplier/delete/"+supplierId);
  }

  getSupplierDetails(id: number){
    return this.http.get<Supplier>("http://localhost:8081/api/supplier/details/"+ id);
  }

  updateSupplier(supplier: Supplier) {
    return this.http.patch<Supplier>("http://localhost:8081/api/supplier/edit", supplier);
  }
}
