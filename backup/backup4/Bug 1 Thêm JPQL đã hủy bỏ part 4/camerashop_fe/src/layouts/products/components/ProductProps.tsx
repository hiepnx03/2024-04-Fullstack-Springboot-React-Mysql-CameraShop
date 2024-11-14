// ProductProps.tsx
import React from "react";
import ProductModel from "../../../model/ProductModel";

interface ProductPropsInterface {
    product: ProductModel;
}

const ProductProps: React.FC<ProductPropsInterface> = (props)  => {

    return (
        <div className="products">
            <h3>{props.product.idProduct}</h3>
            <h3>{props.product.productName}</h3>
            <h3>{props.product.listPrice}</h3>
            <h3>{props.product.sellPrice}</h3>
            <h3>{props.product.description}</h3>
        </div>
    );
}

export default ProductProps;
