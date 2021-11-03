export class Category {
  id: number;
  name: string;
  description: string;
  picturePortraitFileName: string;
  pictureLandscapeFileName: string;

  constructor(id: number, name: string, description: string, picturePortraitFileName: string, pictureLandscapeFileName: string) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.picturePortraitFileName = picturePortraitFileName;
    this.pictureLandscapeFileName = pictureLandscapeFileName;
  }
}
