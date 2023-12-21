import {Component, OnInit} from '@angular/core';
import {FormGroup, NgForm} from "@angular/forms";
import {DomSanitizer} from "@angular/platform-browser";
import {ActivatedRoute} from "@angular/router";
import {Product} from "../_model/Product";
import {ProductApiService} from "../api/product-api.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgbDate} from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-save-new-product',
  templateUrl: './save-new-product.component.html',
  styleUrl: './save-new-product.component.css'
})
export class SaveNewProductComponent implements OnInit {
  constructor(
    private apiProduct: ProductApiService,
    private sanitizer: DomSanitizer,
    private activatedRoute: ActivatedRoute) {

  }

  suppliers: string[] = ['szt.', 'kg', 'l', 'm2'];
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
    this.product = this.activatedRoute.snapshot.data['product'];

    if (this.product && this.product.id) {
      this.isNewProduct = false;
    }
  }

  addProductForm(productForm: NgForm) {

    this.apiProduct.createProduct(this.product).subscribe(
      (response: Product) => {
        productForm.reset();

      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }

  clearForm(productForm: NgForm) {
    productForm.reset();
  }

}
