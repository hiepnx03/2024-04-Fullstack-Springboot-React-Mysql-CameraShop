import React, { useEffect, useState } from "react";
import Product from "../../model/Product";
import ProductProps from "./components/ProductProps";
import { layToanBoSanPham } from "../../api/ProductApi";

const ProductList: React.FC = () => {
    const [productList, setProductList] = useState<Product[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        layToanBoSanPham()
            .then((productData: Product[]) => {
                setProductList(productData);
                setIsLoading(false);
            })
            .catch((error: Error) => {
                setIsLoading(false);
                setError(error.message);
            });
    }, []);

    if (isLoading) {
        return <div className="container"><h1>Đang tải dữ liệu</h1></div>;
    }

    if (error) {
        return <div className="container"><h1>Gặp lỗi: {error}</h1></div>;
    }

    return (
        <div className="container mt-4">
            <div className="row row-cols-1 row-cols-md-3 g-4 mb-4">
                {productList.map((product) => (
                    <div key={product.id} className="col">
                        <div className="card h-100">
                            <div className="card-body">
                                <h5 className="card-title">{product.name}</h5>
                                <p className="card-text">Giá niêm yết: {product.listPrice}</p>
                                <p className="card-text">Giá bán: {product.sellPrice}</p>
                                <p className="card-text">Mô tả: {product.description}</p>
                                <p className="card-text">Số lượng: {product.quantity}</p>
                                <p className="card-text">Đã bán: {product.soldQuantity}</p>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default ProductList;
