import axios from 'axios';
import { BASE_URL } from '../layouts/utils/Constant';
import ProductModel from "../model/ProductModel";

export async function layToanBoSanPham(): Promise<ProductModel[]> {
    try {
        const response = await axios.get(`${BASE_URL}/products`);
        const data = response.data;
        if (!data || !data._embedded || !Array.isArray(data._embedded.products)) {
            throw new Error('Dữ liệu sản phẩm không hợp lệ');
        }
        return data._embedded.products.map((product: any) => ({
            ...product
            // idProduct: product.idProduct,
            // productName: product.productName,
            // listPrice: product.listPrice,
            // sellPrice: product.sellPrice,
            // description: product.description,
            // quantity: product.quantity,
            // avgRating: product.avgRating,
            // soldQuantity: product.soldQuantity,
            // discountPercent: product.discountPercent,
        }));
    } catch (error) {
        console.error('Lỗi khi tải dữ liệu sản phẩm:', error);
        throw error;
    }
}

export async function themSanPham(newProduct: ProductModel): Promise<void> {
    try {
        await axios.post(`${BASE_URL}/products`, newProduct);
    } catch (error) {
        console.error('Lỗi khi thêm sản phẩm:', error);
        throw error;
    }
}

export async function capNhatSanPham(updatedProduct: ProductModel): Promise<void> {
    try {
        await axios.put(`${BASE_URL}/products/${updatedProduct.idProduct}`, updatedProduct);
    } catch (error) {
        console.error('Lỗi khi cập nhật sản phẩm:', error);
        throw error;
    }
}

export async function xoaSanPham(productId: number): Promise<void> {
    try {
        await axios.delete(`${BASE_URL}/products/${productId}`);
    } catch (error) {
        console.error('Lỗi khi xóa sản phẩm:', error);
        throw error;
    }
}
