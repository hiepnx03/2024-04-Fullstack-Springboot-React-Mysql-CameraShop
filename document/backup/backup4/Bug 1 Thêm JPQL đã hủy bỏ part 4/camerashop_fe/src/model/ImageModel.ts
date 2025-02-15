class ImageModel {
   idImage: number;
   nameImage?: string;
   thumbnail?: boolean;
   urlImage?: string;
   dataImage?: string;

   constructor(idImage: number, nameImage: string, thumbnail: boolean, urlImage: string, dataImage: string) {
      this.idImage = idImage;
      this.nameImage = nameImage;
      this.thumbnail = thumbnail;
      this.urlImage = urlImage;
      this.dataImage = dataImage;
   }
}

export default ImageModel;