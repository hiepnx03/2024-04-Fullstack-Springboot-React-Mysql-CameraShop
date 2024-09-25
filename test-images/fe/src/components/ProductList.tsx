import React from 'react';
import { Product } from '../types/Product';

interface ProductListProps {
    products: Product[];
    onEdit: (product: Product) => void;
    onDelete: (id: number) => void;
}

const ProductList: React.FC<ProductListProps> = ({ products, onEdit, onDelete }) => {
    return (
        <div>
            <h2 className="text-2xl font-semibold mb-4">Products</h2>
            <ul className="divide-y divide-gray-200">
                {products.map(product => (
                    <li key={product.id} className="py-4 flex">
                        <div className="flex-1">
                            <h3 className="text-lg font-medium">{product.name}</h3>
                            <div className="flex flex-wrap">
                                {product.imageUrls.map((url, index) => (
                                    <img
                                        key={index}
                                        src={`http://localhost:8080${url}`}
                                        alt="Product"
                                        className="w-24 h-24 object-cover mr-2 mb-2"
                                    />
                                ))}
                            </div>
                        </div>
                        <div className="ml-4 flex-shrink-0">
                            <button
                                onClick={() => onEdit(product)}
                                className="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md shadow-sm text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                            >
                                Edit
                            </button>
                            <button
                                onClick={() => onDelete(product.id)}
                                className="ml-2 inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md shadow-sm text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
                            >
                                Delete
                            </button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ProductList;
