import React, { useEffect, useState } from 'react';
import ProductForm from "./components/product/ProductForm";
import ProductTable from "./components/product/ProductTable";

import ProductModel from "../../model/ProductModel";
import { capNhatSanPham, layToanBoSanPham, themSanPham, xoaSanPham } from "../../api/ProductApi"; // Import các hàm API

const ProductManagement: React.FC = () => {
    const [productList, setProductList] = useState<ProductModel[]>([]);
    const [isAdding, setIsAdding] = useState(false); // Trạng thái để điều khiển sự hiển thị của modal

    useEffect(() => {
        loadData(); // Load dữ liệu sản phẩm khi component được render
    }, []);

    // Hàm để tải dữ liệu sản phẩm từ API
    const loadData = async () => {
        try {
            const data = await layToanBoSanPham();
            setProductList(data);
        } catch (error) {
            console.error('Lỗi khi tải dữ liệu sản phẩm:', error);
        }
    };

    // Hàm để xử lý việc cập nhật sản phẩm
    const handleUpdateProduct = async (updatedProduct: ProductModel) => {
        try {
            await capNhatSanPham(updatedProduct);
            await loadData(); // Sau khi cập nhật sản phẩm, cập nhật lại danh sách
        } catch (error) {
            console.error('Lỗi khi cập nhật sản phẩm:', error);
        }
    };

    // Hàm để xử lý việc xóa sản phẩm
    const handleDeleteProduct = async (productId: number) => {
        try {
            await xoaSanPham(productId);
            await loadData(); // Sau khi xóa sản phẩm, cập nhật lại danh sách
        } catch (error) {
            console.error('Lỗi khi xóa sản phẩm:', error);
        }
    };

    // Hàm để xử lý việc thêm mới sản phẩm
    const handleAddProduct = async (newProduct: ProductModel) => {
        try {
            await themSanPham(newProduct);
            await loadData(); // Sau khi thêm sản phẩm, cập nhật lại danh sách
            setIsAdding(false); // Đóng modal sau khi thêm sản phẩm
        } catch (error) {
            console.error('Lỗi khi thêm sản phẩm:', error);
        }
    };

    // Hàm để đóng modal
    const closeModal = () => {
        setIsAdding(false);
    };

    // Hàm để mở modal
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
