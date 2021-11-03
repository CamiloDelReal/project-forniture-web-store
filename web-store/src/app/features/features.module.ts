import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoriesComponent } from './categories/categories.component';
import { HomeComponent } from './home/home.component';
import {CategoryItemComponent} from './categories/categoryitem/categoryitem.component';
import {HttpClientModule} from "@angular/common/http";
import { ProductsComponent } from './products/products.component';
import { ProductItemComponent } from './products/product-item/product-item.component';



@NgModule({
  declarations: [
    CategoriesComponent,
    HomeComponent,
    CategoryItemComponent,
    ProductsComponent,
    ProductItemComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule
  ]
})
export class FeaturesModule { }
