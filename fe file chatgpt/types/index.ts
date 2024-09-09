export interface ProductDTO {
    idProduct: number;
    productName: string;
    listPrice: number;
    sellPrice: number;
    description: string;
    quantity: number;
    avgRating: number;
    soldQuantity: number;
    discountPercent: number;
    categoryIds: number[];
    imageIds: number[];
}

export interface CategoryDTO {
    idCategory: number;
    categoryName: string;
    productList: ProductDTO[];
}

export interface ImageDTO {
    idImage: number;
    nameImage: string;
    isThumbnail: boolean;
    urlImage: string;
    dataImage: string;
    product: ProductDTO;
}
