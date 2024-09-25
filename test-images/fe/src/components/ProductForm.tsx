import React, { useState, useEffect } from 'react';
import { Product } from '../types/Product';
import { addProduct, updateProduct, deleteImage } from '../api/productService';
import ImageUploader from './ImageUploader';

interface ProductFormProps {
    product?: Product;
    onSave: () => void;
}

const ProductForm: React.FC<ProductFormProps> = ({ product, onSave }) => {
    const [name, setName] = useState(product ? product.name : '');
    const [imageUrls, setImageUrls] = useState<string[]>(product ? product.imageUrls : []);
    const [tempImageUrls, setTempImageUrls] = useState<string[]>([]);

    useEffect(() => {
        if (product) {
            setName(product.name);
            setImageUrls(product.imageUrls);
        }
    }, [product]);

    const handleImageUpload = (url: string) => {
        setTempImageUrls([...tempImageUrls, url]);
    };

    const handleDeleteImage = async (url: string) => {
        if (!tempImageUrls.includes(url)) {
            const filename = url.split('/').pop();
            if (filename) {
                await deleteImage(filename);
            }
        }
        setImageUrls(imageUrls.filter(imageUrl => imageUrl !== url));
        setTempImageUrls(tempImageUrls.filter(imageUrl => imageUrl !== url));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const newProduct: Product = {
            id: product ? product.id : 0,
            name,
            imageUrls: [...imageUrls, ...tempImageUrls]
        };

        if (product) {
            await updateProduct(product.id, newProduct);
        } else {
            await addProduct(newProduct);
        }

        onSave();
    };

    return (
        <form onSubmit={handleSubmit} className="mb-4">
            <div className="mb-4">
                <label className="block text-sm font-medium text-gray-700">Name:</label>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                />
            </div>
            <div>
                <label className="block text-sm font-medium text-gray-700">Current Image URLs:</label>
                <ul className="list-disc pl-5">
                    {imageUrls.map((url, index) => (
                        <li key={index} className="flex items-center">
                            <img src={`http://localhost:8080${url}`} alt="Product" className="max-w-xs mr-2" />
                            <button
                                type="button"
                                onClick={() => handleDeleteImage(url)}
                                className="text-red-500 hover:text-red-700">
                                Delete
                            </button>
                        </li>
                    ))}
                </ul>
            </div>
            <div className="mt-4">
                <label className="block text-sm font-medium text-gray-700">Temporary Image URLs:</label>
                <ul className="list-disc pl-5">
                    {tempImageUrls.map((url, index) => (
                        <li key={index} className="flex items-center">
                            <img src={`http://localhost:8080${url}`} alt="Product" className="max-w-xs mr-2" />
                            <button
                                type="button"
                                onClick={() => handleDeleteImage(url)}
                                className="text-red-500 hover:text-red-700">
                                Delete
                            </button>
                        </li>
                    ))}
                </ul>
            </div>
            <ImageUploader onUpload={handleImageUpload} />
            <button
                type="submit"
                className="mt-4 inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
                Save
            </button>
        </form>
    );
};

export default ProductForm;
