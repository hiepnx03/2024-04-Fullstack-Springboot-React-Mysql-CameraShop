import React, { useEffect, useState } from "react";
import ProductModel from "../../model/ProductModel";
import ProductProps from "./components/ProductProps";
import { layToanBoSanPham } from "../../api/ProductApi";

const ProductList: React.FC = () => {
    const [productList, setProductList] = useState<ProductModel[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        layToanBoSanPham()
            .then((productData: ProductModel[]) => {
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
                    <div key={product.idProduct} className="col">
                        <div className="card h-100">
                            <div className="card-body">
                                <h5 className="card-title">{product.productName}</h5>
                                <p className="card-text">Giá niêm yết: {product.listPrice}</p>
                                <p className="card-text">Giá bán: {product.sellPrice}</p>
                                <p className="card-text">Mô tả: {product.description}</p>
                                <p className="card-text">Số lượng: {product.quantity}</p>
                                <p className="card-text">Xếp hạng: {product.avgRating}</p>
                                <p className="card-text">Đã bán: {product.soldQuantity}</p>
                                <p className="card-text">Giảm giá: {product.discountPercent}</p>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default ProductList;
