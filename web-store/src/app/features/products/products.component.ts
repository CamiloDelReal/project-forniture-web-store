import {Component, OnInit} from '@angular/core';
import {Category} from "../categories/category";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpService} from "../../shared/services/http.service";
import {environment} from "../../../environments/environment";
import {HttpErrorResponse} from "@angular/common/http";
import {Product} from "./product";
import {parseMappings} from "@angular/compiler-cli/src/ngtsc/sourcemaps/src/source_file";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {
  categoryId: number = 0;
  category: Category = new Category(0, 'All Products', '', '', '');
  products: Product[] = [];

  constructor(
    private activatedRoute: ActivatedRoute,
    private httpService: HttpService
  ) {
    this.categoryId = parseInt(this.activatedRoute.snapshot.queryParamMap.get('id') || '0');
    console.log("Products Component for category id = " + this.categoryId);
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.categoryId = parseInt(params['id'] || '0');
      if (this.categoryId !== 0) {
        const url = environment.categoriesPath + `/${this.categoryId}`;
        this.httpService.getRequest(url, this.handleError)
          .subscribe((data) => {
            console.log('Category received ' + data)
            this.category = data
            const urlProducts = `${environment.categoriesPath}/${this.categoryId}/products`
            this.httpService.getRequest(urlProducts, this.handleError)
              .subscribe((data) => {
                console.log('Products received ' + data)
                this.products = data;
              })
          })
      } else {
        this.category = new Category(0, 'All Products', '', '', '');
        const url = environment.productsPath;
        this.httpService.getRequest(url, this.handleError)
          .subscribe((data) => {
            console.log('Products received ' + data)
            this.products = data;
          })
      }
    });
  }

  handleError(error: HttpErrorResponse) {
    if(error.error instanceof ErrorEvent) {
      console.error('An error occured: ', error.error.message);
    } else {
      console.error(
        `Backend returned core ${error.status}, ` +
        `body was: ${error.error}`
      );
    }
  }

}
