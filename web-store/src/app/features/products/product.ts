import {Category} from "../categories/category";

export class Product {
  id: number;
  name: string;
  description: string;
  price: number;
  availability: number;
  picturePortraitFileName: string;
  pictureLandscapeFileName: string;
  categories: Category[];

  constructor(id: number, name: string, description: string, price: number, availability: number, picturePortraitFileName: string, pictureLandscapeFileName: string, categories: Category[]) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.availability = availability;
    this.picturePortraitFileName = picturePortraitFileName;
    this.pictureLandscapeFileName = pictureLandscapeFileName;
    this.categories = categories;
  }
}
