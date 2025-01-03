export interface Product {
    id: number;
    name: string;
    description: string;
    categoryIds: number[];
    importPrice: number;
    listPrice: number;
    soldQuantity: number;
    sellPrice: number;      // Thêm thuộc tính sellPrice
    quantity: number;       // Thêm thuộc tính quantity
        images: {               // Thêm thuộc tính images
            id: number;
            url: string;
            order: number;
        }[];
}


export interface Category {
    id: number;
    name: string;
    description: string;
    image: string;
    active: boolean;
    deleted: boolean;
    editable: boolean;
    visible: boolean;
    status: string | null;
    slug: string | null;
    productIds: number[];
    brandId: number | null;
}

