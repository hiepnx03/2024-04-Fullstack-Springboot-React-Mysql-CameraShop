import React, { useEffect, useState } from 'react';
import { ProductDTO } from '../types';
import { getProducts } from '../services/ProductService';

const ProductList: React.FC = () => {
    const [products, setProducts] = useState<ProductDTO[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            const result = await getProducts();
            setProducts(result);
        };

        fetchData();
    }, []);

    return (
        <div>
            <h1>Products</h1>
            <ul>
                {products.map((product) => (
                    <li key={product.idProduct}>
                        {product.productName} - ${product.sellPrice}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ProductList;
