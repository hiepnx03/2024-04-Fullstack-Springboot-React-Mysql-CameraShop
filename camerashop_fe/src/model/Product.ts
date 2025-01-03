import Image from "./Image";
import ImageModel from "./Image";

class Product {
    id: number;
    name?: string;
    importPrice?: number | undefined;  // Thêm | undefined
    listPrice?: number | undefined;  // Thêm | undefined
    sellPrice?: number | undefined;  // Thêm | undefined
    description?: string;
    quantity?: number | undefined;  // Thêm | undefined
    soldQuantity?: number | undefined;  // Thêm | undefined
    categoryIds: number[];
    images: Image[];


    constructor(id: number, name: string, importPrice: number | undefined, listPrice: number | undefined, sellPrice: number | undefined, description: string, quantity: number | undefined, soldQuantity: number | undefined, categoryIds: number[], images: ImageModel[]) {
        this.id = id;
        this.name = name;
        this.importPrice = importPrice;
        this.listPrice = listPrice;
        this.sellPrice = sellPrice;
        this.description = description;
        this.quantity = quantity;
        this.soldQuantity = soldQuantity;
        this.categoryIds = categoryIds;
        this.images = images;
    }
}

export default Product;
