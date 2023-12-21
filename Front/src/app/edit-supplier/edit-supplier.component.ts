import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Supplier} from "../_model/Supplier";
import {NgForm} from "@angular/forms";
import {Product} from "../_model/Product";
import {HttpErrorResponse} from "@angular/common/http";
import {SupplierApiService} from "../api/supplier-api.service";

@Component({
  selector: 'app-edit-supplier',
  templateUrl: './edit-supplier.component.html',
  styleUrl: './edit-supplier.component.css'
})
export class EditSupplierComponent implements OnInit {
  constructor(private activatedRoute: ActivatedRoute, private supplierApi: SupplierApiService) {
  }

  supplier: Supplier = {
    id: 0,
    name: "",
    address: "",
    postalCode: "",
    email: "",
    phoneNumber: "",
    nip: 0
  }

  ngOnInit(): void {
    this.supplier = this.activatedRoute.snapshot.data['supplier'];
    if (!this.supplier) {
      console.error('Supplier data not available');
    }
  }

  editSupplierForm(supplierForm: NgForm) {
    this.supplierApi.updateSupplier(this.supplier).subscribe(
      (response: Supplier) => {
        supplierForm.reset();

      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }
}
