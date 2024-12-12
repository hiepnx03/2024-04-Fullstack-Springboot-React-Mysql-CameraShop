class ProductModel {
    idProduct: number;
    productName?: string;
    importPrice?: number | undefined;  // Thêm | undefined
    listPrice?: number | undefined;  // Thêm | undefined
    sellPrice?: number | undefined;  // Thêm | undefined
    description?: string;
    quantity?: number | undefined;  // Thêm | undefined
    soldQuantity?: number | undefined;  // Thêm | undefined
    idCategoris?: number[];

    constructor(idProduct: number, productName: string, importPrice: number | undefined, listPrice: number | undefined, sellPrice: number | undefined, description: string, quantity: number | undefined, soldQuantity: number | undefined, idCategoris: number[]) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.importPrice = importPrice;
        this.listPrice = listPrice;
        this.sellPrice = sellPrice;
        this.description = description;
        this.quantity = quantity;
        this.soldQuantity = soldQuantity;
        this.idCategoris = idCategoris;
    }
}

export default ProductModel;
