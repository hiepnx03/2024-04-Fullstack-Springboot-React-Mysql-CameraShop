import axios from 'axios';
import {BASE_URL} from '../layouts/utils/Constant';
import ProductModel from "../model/ProductModel";

// Lấy tất cả sản phẩm
export async function layToanBoSanPham(): Promise<ProductModel[]> {
    try {
        const response = await axios.get(`${BASE_URL}/admin/products`); // API endpoint đã thay đổi theo controller của bạn
        const data = response.data;

        if (!data || !Array.isArray(data.data)) {
            throw new Error('Dữ liệu sản phẩm không hợp lệ');
        }

        // Map dữ liệu để trả về theo đúng mô hình ProductModel
        return data.data.map((product: any) => ({
            idProduct: product.id,
            productName: product.name,
            importPrice: product.importPrice,
            listPrice: product.listPrice,
            sellPrice: product.sellPrice,
            description: product.description,
            quantity: product.quantity,
            avgRating: product.avgRating || 0,  // Giả sử rating có thể không có
            soldQuantity: product.soldQuantity || 0,
            discountPercent: product.discountPercent || 0,
            idCategoris: product.categories?.map((category: any) => category.id) || [],
        }));
    } catch (error) {
        console.error('Lỗi khi tải dữ liệu sản phẩm:', error);
        throw error;
    }
}

// Thêm sản phẩm mới
export async function themSanPham(newProduct: ProductModel): Promise<void> {
    try {
        await axios.post(`${BASE_URL}/admin/products`, newProduct);
    } catch (error) {
        console.error('Lỗi khi thêm sản phẩm:', error);
        throw error;
    }
}

// Cập nhật sản phẩm
export async function capNhatSanPham(updatedProduct: ProductModel): Promise<void> {
    try {
        await axios.put(`${BASE_URL}/admin/products/${updatedProduct.idProduct}`, updatedProduct);
    } catch (error) {
        console.error('Lỗi khi cập nhật sản phẩm:', error);
        throw error;
    }
}

// Xóa sản phẩm
export async function xoaSanPham(productId: number): Promise<void> {
    const token = localStorage.getItem('access_token');  // Lấy token từ localStorage

    if (!token) {
        throw new Error('Chưa có token, vui lòng đăng nhập lại.');
    }

    try {
        // Gửi yêu cầu DELETE với token trong header Authorization
        await axios.delete(`${BASE_URL}/admin/products/${productId}`, {
            headers: {
                'Authorization': `Bearer ${token}`,  // Gửi token trong header
                'Content-Type': 'application/json',  // Đảm bảo gửi dữ liệu đúng định dạng
            }
        });
    } catch (error) {
        console.error('Lỗi khi xóa sản phẩm:', error);
        throw error;  // Ném lỗi để có thể xử lý ở các phần khác
    }
}

// Thêm sản phẩm (API cũ, có thể không cần thiết nếu đã có API trên)
export async function addProduct1(newProduct: ProductModel): Promise<void> {
    try {
        await axios.post(`${BASE_URL}/api/products/add-product`, newProduct);
    } catch (error) {
        console.error('Lỗi khi thêm sản phẩm:', error);
        throw error;
    }
}
