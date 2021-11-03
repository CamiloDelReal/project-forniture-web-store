import {Component, Input, OnInit} from '@angular/core';
import {Category} from "../category";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-categoryitem',
  templateUrl: './categoryitem.component.html',
  styleUrls: ['./categoryitem.component.scss']
})
export class CategoryItemComponent implements OnInit {
  @Input() category: Category = new Category(0, '', '', '', '');

  constructor() { }

  ngOnInit(): void {
  }

  picturePath() {
    if(this.category) {
      const url = environment.picturesPath + "/" + this.category.picturePortraitFileName
      return url;
    }
    return '';
  }

}
