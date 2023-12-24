import {Component, OnInit} from '@angular/core';
import {FormGroup, NgForm} from "@angular/forms";
import {DomSanitizer} from "@angular/platform-browser";
import {ActivatedRoute} from "@angular/router";
import {Product} from "../_model/Product";
import {ProductApiService} from "../api/product-api.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgbDate} from '@ng-bootstrap/ng-bootstrap';
import {SupplierApiService} from "../api/supplier-api.service";
import {Supplier} from "../_model/Supplier";


@Component({
  selector: 'app-save-new-product',
  templateUrl: './save-new-product.component.html',
  styleUrl: './save-new-product.component.css'
})
export class SaveNewProductComponent implements OnInit {
  constructor(
    private apiProduct: ProductApiService,
    private apiSupplier: SupplierApiService,
    private sanitizer: DomSanitizer,
    private activatedRoute: ActivatedRoute) {

  }

  suppliers: Supplier[] = [];
  units: string[] = ['szt.', 'kg', 'l'];
  isNewProduct = true;
  product: Product = {
    quantity: 0,
    unit: "",
    price: 0,
    supplier: "",
    name: "",
    description: "",
    producer: "",
    id: 0,
    bestBeforeDate: new NgbDate(0, 0, 0),
    category: "",
    locationInStorage: "",
  }


  ngOnInit(): void {
   this.fetchSuppliersData();
   console.log(this.suppliers)
    this.product = this.activatedRoute.snapshot.data['product'];

    if (this.product && this.product.id) {
      this.isNewProduct = false;
    }
  }

  addProductForm(productForm: NgForm) {

    this.apiProduct.createProduct(this.product).subscribe(
      (response: Product) => {
        console.log(response)
        productForm.reset();

      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }
  fetchSuppliersData(){
    this.apiSupplier.getAllSuppliers().subscribe({
      next: data => {
        console.log(data)
        this.suppliers = data;
      },
      error: err => {
        console.log(err);
      }
    });
  }
  clearForm(productForm: NgForm) {
    productForm.reset();
  }

}
