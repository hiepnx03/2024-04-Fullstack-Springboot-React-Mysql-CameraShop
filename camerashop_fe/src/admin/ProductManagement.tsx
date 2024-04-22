import React, { useEffect, useState } from 'react';
import ProductForm from "./components/product/ProductForm";
import ProductTable from "./components/product/ProductTable";

import ProductModel from "../model/ProductModel";
import { capNhatSanPham, layToanBoSanPham, themSanPham, xoaSanPham } from "../api/ProductApi"; // Import các hàm API

const ProductManagement: React.FC = () => {
    const [productList, setProductList] = useState<ProductModel[]>([]);
    const [isAdding, setIsAdding] = useState(false); // State to control the visibility of the modal

    useEffect(() => {
        loadData(); // Load dữ liệu sản phẩm khi component được render
    }, []);

    const loadData = async () => {
        try {
            const data = await layToanBoSanPham();
            setProductList(data);
        } catch (error) {
            console.error('Lỗi khi tải dữ liệu sản phẩm:', error);
        }
    };

    const handleUpdateProduct = async (updatedProduct: ProductModel) => {
        try {
            await capNhatSanPham(updatedProduct);
            await loadData(); // Sau khi cập nhật sản phẩm, cập nhật lại danh sách
        } catch (error) {
            console.error('Lỗi khi cập nhật sản phẩm:', error);
        }
    };

    const handleDeleteProduct = async (productId: number) => {
        try {
            await xoaSanPham(productId);
            await loadData(); // Sau khi xóa sản phẩm, cập nhật lại danh sách
        } catch (error) {
            console.error('Lỗi khi xóa sản phẩm:', error);
        }
    };

    // Function to handle adding a new product
    const handleAddProduct = async (newProduct: ProductModel) => {
        try {
            await themSanPham(newProduct);
            await loadData(); // Sau khi thêm sản phẩm, cập nhật lại danh sách
            setIsAdding(false); // Close the modal after adding the product
        } catch (error) {
            console.error('Lỗi khi thêm sản phẩm:', error);
        }
    };

    // Function to handle closing the modal
    const closeModal = () => {
        setIsAdding(false);
    };

    // Function to handle opening the modal
    const openModal = () => {
        setIsAdding(true);
    };

    return (
        <div>
            <h2>Quản lý Sản phẩm</h2>
            <button className={"btn btn-primary"} onClick={openModal}>Thêm sản phẩm</button>
            <ProductForm onSubmit={handleAddProduct} isAdd={isAdding} handleClose={closeModal} />
            <ProductTable products={productList} onUpdate={handleUpdateProduct} onDelete={handleDeleteProduct} />
        </div>
    );
}

export default ProductManagement;
