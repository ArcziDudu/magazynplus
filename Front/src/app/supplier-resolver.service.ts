import {Injectable} from '@angular/core';

import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Observable, of} from "rxjs";
import {Supplier} from "./_model/Supplier";
import {SupplierApiService} from "./api/supplier-api.service";

@Injectable({
  providedIn: 'root'
})
export class SupplierResolverService implements Resolve<Supplier>  {

  constructor(private supplierApi: SupplierApiService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Supplier> {
    console.log("1")
    const id = route.paramMap.get("supplierId");
    if (id) {
      const numericId = +id;
      return this.supplierApi.getSupplierDetails(numericId);
    } else {
      console.log("No productId provided");
      return of(this.getProductDetails());
    }

  }

  getProductDetails(): Supplier {
    return {
      id: 0,
      name: "",
      address: "",
      postalCode: "",
      email: "",
      phoneNumber: "",
      nip: 0
    };
  }
}


