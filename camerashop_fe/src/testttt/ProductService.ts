import axios from "axios";
import {Category, Product} from "./Interface";

const BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080'; // Địa chỉ cơ sở của API


export const getAllProducts = async (): Promise<Product[]> => {
    try {
        const response = await axios.get(`${BASE_URL}/admin/products`);
        console.log("API Response:", response.data); // Log toàn bộ phản hồi
        return response.data.data; // Truy cập vào phần `data` trong `ResponseObject`
    } catch (error) {
        console.error("Error fetching products:", error); // Log lỗi nếu có
        throw error; // Ném lỗi để xử lý tiếp
    }
};



export const getProductById = async (id: number): Promise<Product> => {
    const response = await axios.get(`${BASE_URL}/admin/products/${id}`);
    const product = response.data.data;

    // Chuyển categories thành categoryIds
    const categoryIds = product.categories.map((category: Category) => category.id);

    return {
        ...product,
        categoryIds: categoryIds,  // Cập nhật categoryIds
    };
};

export const updateProduct = async (product: Product): Promise<Product> => {
    const token = localStorage.getItem("access_token"); // Lấy token từ localStorage

    if (!token) {
        throw new Error("Chưa có token, vui lòng đăng nhập lại.");
    }

    try {
        const response = await axios.put(
            `${BASE_URL}/admin/products/${product.id}`, // Gửi ID sản phẩm trong URL
            product, // Gửi đối tượng JSON
            {
                headers: {
                    Authorization: `Bearer ${token}`, // Gửi token trong header
                    "Content-Type": "application/json", // Định dạng JSON
                },
            }
        );
        return response.data; // Trả về dữ liệu sản phẩm đã được cập nhật
    } catch (error: unknown) {
        if (error instanceof Error) {
            throw new Error("Lỗi khi gửi yêu cầu: " + error.message);
        } else {
            throw new Error("Đã xảy ra lỗi không xác định");
        }
    }
};






export const createProduct = async (product: Product): Promise<void> => {
    const token = localStorage.getItem('access_token');  // Lấy token từ localStorage

    if (!token) {
        throw new Error('Chưa có token, vui lòng đăng nhập lại.');
    }

    try {
        const response = await fetch(`${BASE_URL}/admin/products`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,  // Gửi token trong header
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(product),
        });

        if (!response.ok) {
            throw new Error('Không thể thêm sản phẩm');
        }
    } catch (error: unknown) {
        if (error instanceof Error) {
            // Now TypeScript knows that `error` is an instance of `Error`
            throw new Error('Lỗi khi gửi yêu cầu: ' + error.message);
        } else {
            // Handle the case where error is not an instance of Error
            throw new Error('Đã xảy ra lỗi không xác định');
        }
    }
};

export const deleteProduct = async (id: number): Promise<void> => {
    await axios.delete(`${BASE_URL}/admin/products/${id}`);
};
