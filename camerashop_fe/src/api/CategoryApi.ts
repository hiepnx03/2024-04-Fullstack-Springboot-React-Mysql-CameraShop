import axios, { AxiosResponse } from 'axios';
import Category from "../model/Category";
import { ResponseObject } from '../types/ResponseObject';
import Product from "../model/Product"; // Điều chỉnh đường dẫn cho đúng

// Địa chỉ cơ sở của API có thể lấy từ biến môi trường để dễ dàng cấu hình
const BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080'; // Địa chỉ cơ sở của API

// Hàm xử lý lỗi chung
function handleError(error: any): void {
    if (error.response) {
        // Lỗi từ server (400, 404, 500, etc.)
        console.error('Error response:', error.response.data);
        console.error('Error status:', error.response.status);
    } else if (error.request) {
        // Lỗi không nhận được phản hồi từ server
        console.error('No response received:', error.request);
    } else {
        // Lỗi khác trong quá trình thiết lập yêu cầu
        console.error('Error message:', error.message);
    }
    throw error; // Quăng lại lỗi để có thể xử lý ở các phần khác
}

export async function getAllCategories(page: number = 0, size: number = 20): Promise<Category[]> {
    try {
        const response: AxiosResponse<ResponseObject> = await axios.get(
            `${BASE_URL}/admin/categories/all`,
            {
                params: { page, size },
            }
        );
        if (response.data && response.data.status === "ok") {
            return response.data.data || [];
        } else {
            console.warn("Unexpected response format:", response.data);
            return [];
        }
    } catch (error) {
        handleError(error);
        return [];
    }
}



export async function createCategory(newCategoryData: Category): Promise<Category> {
    try {
        const response: AxiosResponse<ResponseObject> = await axios.post(`${BASE_URL}/admin/categories`, newCategoryData);

        if (response.data.status === 'ok') {
            return response.data.data;  // Trả về danh mục đã tạo
        } else {
            throw new Error(response.data.message);  // Quăng lỗi nếu không thành công
        }
    } catch (error) {
        console.error('Lỗi khi tạo mới danh mục:', error);
        throw error;
    }
}


export async function updateCategory(idCategory: number, updatedCategoryData: Category): Promise<Category> {
    try {
        const response: AxiosResponse<ResponseObject> = await axios.put(`${BASE_URL}/admin/categories/${idCategory}`, updatedCategoryData);

        if (response.data.status === 'ok') {
            return response.data.data;  // Trả về danh mục đã cập nhật
        } else {
            throw new Error(response.data.message);  // Quăng lỗi nếu không thành công
        }
    } catch (error) {
        console.error('Lỗi khi cập nhật danh mục:', error);
        throw error;
    }
}


export const deleteCategory = async (categoryId: number) => {
    const token = localStorage.getItem('access_token'); // Lấy token từ localStorage

    try {
        const response = await axios.delete(`${BASE_URL}/admin/categories/${categoryId}`, {
            headers: {
                'Authorization': `Bearer ${token}`, // Gửi token trong header
                'Content-Type': 'application/json',
            }
        });
        return response.data;  // Dữ liệu trả về từ server
    } catch (error: unknown) {
        if (axios.isAxiosError(error)) {
            // Xử lý lỗi AxiosError cụ thể
            console.error("Lỗi khi xóa danh mục:", error.response?.data || error.message);
        } else {
            // Xử lý lỗi không phải AxiosError (ví dụ, lỗi hệ thống)
            console.error("Lỗi không phải từ Axios:", error);
        }
        throw new Error('Không thể xóa danh mục');
    }
};
