import axios, { AxiosResponse } from 'axios';
import CategoryModel from "../model/CategoryModel";

const BASE_URL = 'http://localhost:8080/api/category'; // Địa chỉ cơ sở của API

// export async function getAllCategories(): Promise<CategoryModel[]> {
//     try {
//         const response: AxiosResponse<{ _embedded: { categories: CategoryModel[] } }> = await axios.get(`${BASE_URL}/api/category`);
//         return response.data._embedded.categories;
//     } catch (error) {
//         console.error('Lỗi khi lấy danh sách danh mục sản phẩm:', error);
//         throw error;
//     }
// }


export async function getAllCategories(): Promise<CategoryModel[]> {
    try {
        const response: AxiosResponse<CategoryModel[]> = await axios.get(`${BASE_URL}`);
        return response.data;
    } catch (error) {
        console.error('Lỗi khi lấy danh sách danh mục sản phẩm:', error);
        throw error;
    }
}


export async function createCategory(newCategoryData: CategoryModel): Promise<CategoryModel> {
    try {
        const response: AxiosResponse<CategoryModel> = await axios.post<CategoryModel>(`${BASE_URL}`, newCategoryData);
        return response.data;
    } catch (error) {
        console.error('Lỗi khi tạo mới danh mục sản phẩm:', error);
        throw error;
    }
}

export async function updateCategory(idCategory: number, updatedCategoryData: CategoryModel): Promise<CategoryModel> {
    try {
        const response: AxiosResponse<CategoryModel> = await axios.put<CategoryModel>(`${BASE_URL}/${idCategory}`, updatedCategoryData);
        return response.data;
    } catch (error) {
        console.error('Lỗi khi cập nhật danh mục sản phẩm:', error);
        throw error;
    }
}

export async function deleteCategory(idCategory: number): Promise<void> {
    try {
        await axios.delete(`${BASE_URL}/${idCategory}`);
    } catch (error) {
        console.error('Lỗi khi xóa danh mục sản phẩm:', error);
        throw error;
    }
}
