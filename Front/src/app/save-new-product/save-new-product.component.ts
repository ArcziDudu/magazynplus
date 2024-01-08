import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {DomSanitizer} from "@angular/platform-browser";
import {ActivatedRoute} from "@angular/router";
import {Product} from "../_model/Product";
import {ProductApiService} from "../api/product-api.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgbDate} from '@ng-bootstrap/ng-bootstrap';
import {SupplierApiService} from "../api/supplier-api.service";
import {Supplier} from "../_model/Supplier";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FileApiService} from "../api/file-api.service";


@Component({
  selector: 'app-save-new-product',
  templateUrl: './save-new-product.component.html',
  styleUrl: './save-new-product.component.css'
})
export class SaveNewProductComponent implements OnInit {

  constructor(
    private apiProduct: ProductApiService,
    private apiSupplier: SupplierApiService,
    private apiFile: FileApiService,
    private sanitizer: DomSanitizer,
    private activatedRoute: ActivatedRoute,
    private snackBar: MatSnackBar) {

  }

  loading: boolean = false;
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
      (response) => {
        if (response === 'OK') {
          console.log(response);
          productForm.reset();
          this.showSnackBar('Product created successfully');
        } else if (response === 'NO_CONTENT') {
          console.log(response);
          productForm.reset();
          this.showSnackBar('Same product found, quantity has been updated');
        }
      },
      (error: HttpErrorResponse) => {
        this.showSnackBar('Something went wrong, please check your form');
      }
    );
  }


  fetchSuppliersData() {
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

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    this.loading = true;
    this.apiFile.uploadFile('productsFile',formData).subscribe(
      (response) => {
       if(response == 'OK'){
         this.showSnackBar('File uploaded successfully');
       }
       else if (response == 'BAD_REQUEST')
         this.showSnackBar('Something went wrong, please check your file');
      },
      (error) => {
        this.loading = false;
        this.showSnackBar('Something went wrong, please check your file');
      },
      () => {
        this.loading = false;
      }
    )
  }
  private showSnackBar(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 7000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

}
