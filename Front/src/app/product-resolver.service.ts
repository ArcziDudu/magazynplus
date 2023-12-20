import { Injectable } from '@angular/core';
import { ProductApiService } from "./api/product-api.service";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router";
import { map, Observable, of } from "rxjs";
import { Product } from "./_model/Product";
import { NgbDate } from "@ng-bootstrap/ng-bootstrap";

@Injectable({
  providedIn: 'root'
})
export class ProductResolverService implements Resolve<Product> {

  constructor(private productService: ProductApiService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Product> {
    const id = route.paramMap.get("productId");
    if (id) {
      const numericId = +id;
      return this.productService.getProductDetails(numericId);
    } else {
      console.log("No productId provided");
      return of(this.getProductDetails());
    }
  }

  getProductDetails(): Product {
    return {
      imageLink: "",
      quantity: 0,
      unit: '',
      price: 0,
      supplier: "",
      name: "",
      description: "",
      bestBeforeDate: new NgbDate(0, 0, 0),
      producer: "",
      id: 0,
      category: "",
      locationInStorage: ""
    };
  }
}
