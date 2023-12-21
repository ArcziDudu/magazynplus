import {Component, OnInit} from '@angular/core';
import {SupplierApiService} from "../api/supplier-api.service";
import {Supplier} from "../_model/Supplier";
import {ConfirmationDialogComponent} from "../confirmation-dialog/confirmation-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";

@Component({
  selector: 'app-suppliers',
  templateUrl: './suppliers.component.html',
  styleUrl: './suppliers.component.css'
})
export class SuppliersComponent implements OnInit {
  constructor(private supplierApi: SupplierApiService,
              private dialog: MatDialog,
              private router: Router) {
  }

  suppliers: Supplier[] = [];

  ngOnInit(): void {
    this.supplierApi.getAllSuppliers().subscribe({
      next: data => {
        this.suppliers = data;
      },
      error: err => {
        console.log(err);
      }
    });
  }

  openDialog(item: any, type: string) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: {type: type, id: item.id}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.supplierApi.deleteSupplier(item).subscribe(
          () => {
            this.ngOnInit();
          },
          (error) => {
            console.error('Error while removing supplier:', error);
          }
        );
      } else {
        console.log('Deletion cancelled by user');
      }
    });
  }

  editSupplier(supplierId: number) {
    console.log('Navigating to edit supplier with supplierId:', supplierId);
    this.router.navigate(['/supplier/edit', {supplierId: supplierId}])
      .catch(error => console.error('Navigation error:', error));
  }



}
