import {Product} from "./Product";

export interface User {
  id: number;
  email: string;
  firstname: string;
  lastname: string;
  products: Product[];
}


