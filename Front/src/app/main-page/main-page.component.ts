import {MatTableDataSource} from "@angular/material/table";
import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {WebApiService} from "../api/web-api.service";
import {Product} from "../_model/Product";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {ProductApiService} from "../api/product-api.service";


@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})

export class MainPageComponent implements OnInit, AfterViewInit {
  userProducts: Product[] = [];
  displayedColumns = ['Id', 'Name', 'Category', 'Producer', 'Price', 'Quantity', 'Availability'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  dataSource: MatTableDataSource<Product>;
  searchKey: string = '';

  constructor(private webApiService: WebApiService, private productApiService: ProductApiService) {
    this.dataSource = new MatTableDataSource(this.userProducts);
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  ngOnInit(): void {

    this.webApiService.getUser().subscribe({
      next: data => {
        console.log(data)
        this.userProducts = data.products;
      }, error: err => {
        console.log(err);
      }
    });

  }

  search(searchKey: string) {
    console.log(searchKey);
    this.productApiService.getProduct(searchKey).subscribe(data => {
      this.userProducts = data;
      console.log(data)
    });
  }
}
