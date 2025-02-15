import React, { useState, useEffect } from 'react';
import { Product } from './types/Product';
import { getProducts, deleteProduct } from './api/productService';
import ProductForm from './components/ProductForm';
import ProductList from './components/ProductList';

const App: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [currentProduct, setCurrentProduct] = useState<Product | undefined>(undefined);
  const [isEditing, setIsEditing] = useState(false);

  const loadProducts = async () => {
    const products = await getProducts();
    setProducts(products);
  };

  useEffect(() => {
    loadProducts();
  }, []);

  const handleSave = () => {
    loadProducts();
    setIsEditing(false);
    setCurrentProduct(undefined);
  };

  const handleEdit = (product: Product) => {
    setCurrentProduct(product);
    setIsEditing(true);
  };

  const handleDelete = async (id: number) => {
    await deleteProduct(id);
    loadProducts();
  };

  return (
      <div className="container mx-auto p-4">
        <h1 className="text-3xl font-bold mb-4">Product Management</h1>
        {isEditing ? (
            <ProductForm product={currentProduct} onSave={handleSave} />
        ) : (
            <ProductForm onSave={handleSave} />
        )}
        <ProductList products={products} onEdit={handleEdit} onDelete={handleDelete} />
      </div>
  );
};

export default App;
