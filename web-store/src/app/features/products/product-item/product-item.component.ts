import {Component, Input, OnInit} from '@angular/core';
import {Product} from "../product";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.scss']
})
export class ProductItemComponent implements OnInit {
  @Input() product: Product = new Product(0, '', '', 0.0, 0, '', '', []);
  constructor() { }

  ngOnInit(): void {
  }

  picturePath() {
    if(this.product) {
      const url = environment.picturesPath + "/" + this.product.picturePortraitFileName
      return url;
    }
    return '';
  }

}
