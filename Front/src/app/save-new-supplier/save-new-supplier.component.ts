import { Component } from '@angular/core';
import {Supplier} from "../_model/Supplier";
import {NgForm} from "@angular/forms";
import {Product} from "../_model/Product";
import {HttpErrorResponse} from "@angular/common/http";
import {SupplierApiService} from "../api/supplier-api.service";

@Component({
  selector: 'app-save-new-supplier',
  templateUrl: './save-new-supplier.component.html',
  styleUrl: './save-new-supplier.component.css'
})
export class SaveNewSupplierComponent {
  constructor(private apiSupplier: SupplierApiService) {
  }
  supplier: Supplier ={
    id: 0,
    name: "",
    address: "",
    postalCode: "",
    email: "",
    phoneNumber: "",
    nip: 0
  }

  createSupplierForm(productForm: NgForm) {
    console.log(productForm)
    this.apiSupplier.createSupplier(this.supplier).subscribe(
      (response: Supplier) => {
        productForm.reset();
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }
}
