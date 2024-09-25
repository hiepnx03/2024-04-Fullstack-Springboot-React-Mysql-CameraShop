import axios from 'axios';
import { ProductDTO } from '../types';

const API_URL = '/api/products';

export const getProducts = async (): Promise<ProductDTO[]> => {
    const response = await axios.get(API_URL);
    return response.data;
};

export const getProductById = async (id: number): Promise<ProductDTO> => {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
};

export const createProduct = async (product: ProductDTO): Promise<ProductDTO> => {
    const response = await axios.post(API_URL, product);
    return response.data;
};

export const updateProduct = async (id: number, product: ProductDTO): Promise<ProductDTO> => {
    const response = await axios.put(`${API_URL}/${id}`, product);
    return response.data;
};

export const deleteProduct = async (id: number): Promise<void> => {
    await axios.delete(`${API_URL}/${id}`);
};
