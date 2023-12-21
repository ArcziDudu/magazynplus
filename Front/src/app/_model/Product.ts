import {NgbDate} from "@ng-bootstrap/ng-bootstrap";

export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  category: string,
  producer: string;
  quantity: number;
  unit: string,
  supplier: string;
  locationInStorage: string;
  bestBeforeDate: NgbDate;
}
