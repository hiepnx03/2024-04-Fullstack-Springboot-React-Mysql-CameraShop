import axios from 'axios';
import { CategoryDTO } from '../types';

const API_URL = '/api/categories';

export const getCategories = async (): Promise<CategoryDTO[]> => {
    const response = await axios.get(API_URL);
    return response.data;
};

export const getCategoryById = async (id: number): Promise<CategoryDTO> => {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
};

export const createCategory = async (category: CategoryDTO): Promise<CategoryDTO> => {
    const response = await axios.post(API_URL, category);
    return response.data;
};

export const updateCategory = async (id: number, category: CategoryDTO): Promise<CategoryDTO> => {
    const response = await axios.put(`${API_URL}/${id}`, category);
    return response.data;
};

export const deleteCategory = async (id: number): Promise<void> => {
    await axios.delete(`${API_URL}/${id}`);
};
