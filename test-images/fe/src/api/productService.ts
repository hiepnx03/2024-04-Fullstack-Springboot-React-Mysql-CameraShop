import axiosConfig from './axiosConfig';
import { Product } from '../types/Product';

export const getProducts = async () => {
    const response = await axiosConfig.get<Product[]>('/products');
    return response.data;
};

export const getProductById = async (id: number) => {
    const response = await axiosConfig.get<Product>(`/products/${id}`);
    return response.data;
};

export const addProduct = async (product: Product) => {
    const response = await axiosConfig.post<Product>('/products', product);
    return response.data;
};

export const updateProduct = async (id: number, product: Product) => {
    const response = await axiosConfig.put<Product>(`/products/${id}`, product);
    return response.data;
};

export const deleteProduct = async (id: number) => {
    await axiosConfig.delete(`/products/${id}`);
};

export const deleteImage = async (filename: string) => {
    await axiosConfig.delete(`/images/files/${filename}`);
};
