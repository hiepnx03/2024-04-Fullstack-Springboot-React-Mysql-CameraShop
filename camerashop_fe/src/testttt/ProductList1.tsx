import React, { useEffect, useState } from "react";
import { getAllProducts } from "./ProductService";
import { Product } from "./Interface";
import { useNavigate } from 'react-router-dom';  // Use useNavigate instead

const ProductList1: React.FC = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const navigate = useNavigate();  // Initialize useNavigate

    useEffect(() => {
        getAllProducts().then((data) => setProducts(data));
    }, []);

    const handleEdit = (id: number) => {
        navigate(`/edit-product/${id}`);  // Use navigate to go to the edit page
    };

    const handleAddProduct = () => {
        navigate("/product-form");  // Điều hướng đến trang thêm sản phẩm
    };

    return (
        <div>
            <h2>Product List</h2>
            {/* Nút thêm sản phẩm */}
            <button onClick={handleAddProduct} style={{ marginBottom: '20px' }}>
                Thêm Sản Phẩm
            </button>


            {products.map((product) => (
                <div key={product.id} style={{ marginBottom: '20px', border: '1px solid #ccc', padding: '10px' }}>
                    <h3>{product.name}</h3>
                    <p><strong>Description:</strong> {product.description}</p>
                    <p><strong>Price:</strong> {product.sellPrice} VND</p>
                    <p><strong>Import Price:</strong> {product.importPrice} VND</p>
                    <p><strong>List Price:</strong> {product.listPrice} VND</p>
                    <p><strong>Quantity:</strong> {product.quantity}</p>
                    <p><strong>Sold Quantity:</strong> {product.soldQuantity}</p>
                    <p><strong>Categories:</strong> {product.categoryIds.join(", ")}</p>
                    <div>
                        <h4>Images:</h4>
                        {product.images.length > 0 ? (
                            product.images.map((image) => (
                                <img
                                    key={image.id}
                                    src={image.url}
                                    alt={`Product image ${image.id}`}
                                    style={{ width: '100px', marginRight: '10px' }}
                                />
                            ))
                        ) : (
                            <p>No images available</p>
                        )}
                    </div>
                    <button onClick={() => handleEdit(product.id)}>Edit</button> {/* Edit button */}
                </div>
            ))}
        </div>
    );
};

export default ProductList1;
