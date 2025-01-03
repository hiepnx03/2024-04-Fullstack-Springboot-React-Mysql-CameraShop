import axios from "axios";
import {Category} from "./Interface";


const BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080'; // Địa chỉ cơ sở của API

export const getAllCategories = async (): Promise<Category[]> => {
    try {
        const response = await axios.get(`${BASE_URL}/admin/categories`);
        // Truy cập vào response.data.data để lấy danh sách danh mục
        if (Array.isArray(response.data.data)) {
            return response.data.data;  // Trả về mảng các danh mục
        } else {
            console.error('Dữ liệu trả về không phải là một mảng:', response.data);
            return [];  // Trả về mảng rỗng nếu dữ liệu không hợp lệ
        }
    } catch (error) {
        console.error('Lỗi khi lấy danh mục:', error);
        return [];  // Trả về mảng rỗng nếu có lỗi
    }
};


