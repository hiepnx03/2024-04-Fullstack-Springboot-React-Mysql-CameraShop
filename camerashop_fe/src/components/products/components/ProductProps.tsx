// ProductProps.tsx
import React from "react";
import Product from "../../../model/Product";

interface ProductPropsInterface {
    product: Product;
}

const ProductProps: React.FC<ProductPropsInterface> = (props)  => {

    return (
        <div className="products">
            <h3>{props.product.id}</h3>
            <h3>{props.product.name}</h3>
            <h3>{props.product.listPrice}</h3>
            <h3>{props.product.sellPrice}</h3>
            <h3>{props.product.description}</h3>
        </div>
    );
}

export default ProductProps;
