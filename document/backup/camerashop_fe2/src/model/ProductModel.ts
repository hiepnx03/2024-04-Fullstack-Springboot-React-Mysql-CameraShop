class ProductModel {
    idProduct: number;
    productName?: string;
    listPrice?: number;
    sellPrice?: number;
    description?: string;
    quantity?: number;
    avgRating?: number;
    soldQuantity?: number;
    discountPercent?: number;
    idCategoris? :number[];


    constructor(idProduct: number, productName: string, listPrice: number, sellPrice: number, description: string, quantity: number, avgRating: number, soldQuantity: number, discountPercent: number, idCategoris: number[]) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.listPrice = listPrice;
        this.sellPrice = sellPrice;
        this.description = description;
        this.quantity = quantity;
        this.avgRating = avgRating;
        this.soldQuantity = soldQuantity;
        this.discountPercent = discountPercent;
        this.idCategoris = idCategoris;
    }
}

export default ProductModel;
