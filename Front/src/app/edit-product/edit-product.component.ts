import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {Product} from "../_model/Product";
import {NgbDate} from "@ng-bootstrap/ng-bootstrap";
import {ActivatedRoute} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {ProductApiService} from "../api/product-api.service";

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrl: './edit-product.component.css'
})
export class EditProductComponent implements OnInit {

  suppliers: string[] = ['szt.', 'kg', 'l', 'm2'];
  units: string[] = ['szt.', 'kg', 'l'];
  product: Product = {
    quantity: 0,
    unit: '',
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

  constructor(private activatedRoute: ActivatedRoute, private apiProduct: ProductApiService) {
  }

  ngOnInit(): void {
    this.product = this.activatedRoute.snapshot.data['product'];
  }


  editProductForm(productForm: NgForm) {
    this.apiProduct.updateProduct(this.product).subscribe(
      (response: Product) => {
        productForm.reset();

      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }
}
