import {Component, Injectable, OnInit} from '@angular/core';
import {WebApiService} from "../api/web-api.service";
import {Product} from "../_model/Product";
import {ProductApiService} from "../api/product-api.service";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmationDialogComponent} from "../confirmation-dialog/confirmation-dialog.component";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})

@Injectable()
export class MainPageComponent implements OnInit {

  constructor(private webApiService: WebApiService,
              private productApiService: ProductApiService,
              private router: Router,
              private dialog: MatDialog,
  private route: ActivatedRoute){

  }

  userProducts: Product[] = [];
  displayedColumns = ['Id', 'Name', 'Category', 'Producer', 'Price', 'Quantity', 'Availability', 'Actions'];
  productArraySize = 0;

  nextPage() {
    this.page++;
    this.loadNextPage();
  }

  previousPage() {
    this.page--;
    this.loadNextPage();
  }

  searchKey: string = '';
  page: number = 0;

  loadNextPage() {
    this.productApiService.getProductPageable(this.page).subscribe({
      next: data => {
        this.userProducts = data;
        this.productArraySize = this.userProducts.length;
      },
      error: err => {
        console.log(err);
      }
    });
  }

  ngOnInit(): void {
    this.productApiService.getProductPageable(this.page).subscribe({
      next: data => {
        this.userProducts = data;
        this.productArraySize = this.userProducts.length;
      },
      error: err => {
        console.log(err);
      }
    });
  }

  search(searchKey: string) {
    console.log(searchKey);
    this.productApiService.getProduct(searchKey).subscribe(data => {
      this.userProducts = data;
      this.productArraySize = this.userProducts.length;
      console.log(data)
    });
  }


  openDialog(id: number) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.productApiService.deleteProduct(id).subscribe(
          () => {
            this.ngOnInit();
          },
          (error) => {
            console.error('Error while removing product:', error);
          }
        );
      } else {
        console.log('Deletion cancelled by user');
      }
    });
  }
  editProductDetails(productId: number) {
    console.log('Navigating to edit product with productId:', productId);

    this.router.navigate(['/product/edit', { productId: productId }]);

    // Log the current route's snapshot
    console.log('Current route snapshot:', this.route.snapshot);
  }

}
