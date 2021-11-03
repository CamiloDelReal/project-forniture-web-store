import { Component, OnInit } from '@angular/core';
import {Category} from "../../features/categories/category";
import {HttpService} from "../services/http.service";
import {HttpErrorResponse} from "@angular/common/http";
import {computeStartOfLinePositions} from "@angular/compiler-cli/src/ngtsc/sourcemaps/src/source_file";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
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

  navigate(index: number) {
    console.log(`Request navigation to ${index}`)
  }

  handleError(error: HttpErrorResponse) {
    if(error.error instanceof ErrorEvent) {
      console.error(`An error ocurred ${error.error.message}`);
    } else {
      console.error(
        `Backend returned ${error.status}, ` +
        `body was ${error.error}`
      )
    }
  }

}
