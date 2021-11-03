import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private http: HttpClient) { }

  getRequest(url: string, errorHandle: any): Observable<any> {
    const headers = new HttpHeaders();
    headers.set("Access-Control-Allow-Origin", "*");
    return this.http.get(url, {headers: headers})
      .pipe(
        catchError(errorHandle)
      )
  }
}
