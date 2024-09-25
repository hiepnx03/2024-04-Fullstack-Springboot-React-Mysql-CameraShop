import axios from 'axios';
import { ImageDTO } from '../types';

const API_URL = '/api/images';

export const getImages = async (): Promise<ImageDTO[]> => {
    const response = await axios.get(API_URL);
    return response.data;
};

export const getImageById = async (id: number): Promise<ImageDTO> => {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
};

export const createImage = async (image: ImageDTO): Promise<ImageDTO> => {
    const response = await axios.post(API_URL, image);
    return response.data;
};

export const updateImage = async (id: number, image: ImageDTO): Promise<ImageDTO> => {
    const response = await axios.put(`${API_URL}/${id}`, image);
    return response.data;
};

export const deleteImage = async (id: number): Promise<void> => {
    await axios.delete(`${API_URL}/${id}`);
};
