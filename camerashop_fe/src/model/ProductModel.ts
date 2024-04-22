class ProductModel {
    idProduct: number;
    productName?: string;
    listPrice?: number;
    sellPrice?: number;
    description?: string;


    constructor(idProduct: number, productName: string, listPrice: number, sellPrice: number, description: string) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.listPrice = listPrice;
        this.sellPrice = sellPrice;
        this.description = description;
    }
}

export default ProductModel;
