import {Component} from '@angular/core';
import {Supplier} from "../_model/Supplier";
import {NgForm} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {SupplierApiService} from "../api/supplier-api.service";
import {FileApiService} from "../api/file-api.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-save-new-supplier',
  templateUrl: './save-new-supplier.component.html',
  styleUrl: './save-new-supplier.component.css'
})
export class SaveNewSupplierComponent {
  constructor(private apiSupplier: SupplierApiService,
              private fileApi: FileApiService,
              private snackBar: MatSnackBar) {
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
  loading: boolean = false;

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

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    this.loading = true;
    this.fileApi.uploadFile('suppliersFile', formData).subscribe(
      (response) => {
        if (response == 'OK') {
          this.showSnackBar('File uploaded successfully');
        } else if (response == 'BAD_REQUEST')
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
