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
              private dialog: MatDialog) {

  }
  loading: boolean = false;
  userProducts: Product[] = [];
  displayedColumns = [ 'Availability','Name', 'Category', 'Producer', 'Supplier', 'Location', 'Price', 'Quantity', 'Unit', 'BestBeforeDate', 'Actions',];
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
    this.loading=true;
    this.productApiService.getProductPageable(this.page).subscribe({
      next: data => {
        this.userProducts = data;
        this.productArraySize = this.userProducts.length;
        this.loading=false;
      },
      error: err => {
        console.log(err);
      }
    });
  }

  search(searchKey: string) {

    this.productApiService.getProduct(searchKey).subscribe(data => {
      this.userProducts = data;
      this.productArraySize = this.userProducts.length;
    });
  }


  openDialog(item: any, type: string) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: {type: type, id: item.id}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.productApiService.deleteProduct(item).subscribe(
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

    this.router.navigate(['/product/edit', {productId: productId}]);

  }

}
