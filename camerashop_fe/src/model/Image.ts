class ImageModel {
   id: number;          // ID của hình ảnh
   url: string;              // Đường dẫn của hình ảnh
   imageOrder: number;       // Thứ tự của hình ảnh trong bộ sưu tập
   productId: number;        // ID sản phẩm mà hình ảnh này thuộc về

   constructor(idImage: number, url: string, imageOrder: number, productId: number) {
      this.id = idImage;
      this.url = url;
      this.imageOrder = imageOrder;
      this.productId = productId;
   }
}

export default ImageModel;
