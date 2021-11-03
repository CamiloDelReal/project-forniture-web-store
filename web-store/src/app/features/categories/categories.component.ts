import { Component, OnInit } from '@angular/core';
import {HttpService} from "../../shared/services/http.service";
import {Category} from "./category";
import {HttpErrorResponse} from "@angular/common/http";
import {throwError} from "rxjs";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit {
  categories: Category[] = []

  constructor(
    private httpService: HttpService
  ) { }

  ngOnInit(): void {
    const url = environment.categoriesPath
    this.httpService.getRequest(url, this.handleError)
      .subscribe((data) => {
        console.log(data)
        this.categories = data;
      })
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
