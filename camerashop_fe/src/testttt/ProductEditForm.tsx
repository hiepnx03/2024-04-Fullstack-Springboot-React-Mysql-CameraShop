import React, { useState, useEffect } from "react";
import { getProductById, updateProduct } from "./ProductService";
import { getAllCategories } from "./CategoryService";
import { Product, Category } from "./Interface";
import { useParams } from "react-router-dom";

const ProductEditForm: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [product, setProduct] = useState<Product | null>(null);
    const [categories, setCategories] = useState<Category[]>([]);
    const [selectedCategories, setSelectedCategories] = useState<number[]>([]);
    const [selectedImage, setSelectedImage] = useState<File | null>(null); // Declare selectedImage here

    useEffect(() => {
        if (id) {
            const productId = Number(id); // Ensure the ID is a valid number
            console.log("Product ID from URL:", productId); // Log the productId

            if (isNaN(productId)) {
                alert("Invalid product ID");
                return;
            }

            // Fetch the product details
            getProductById(productId)
                .then((fetchedProduct) => {
                    console.log("Fetched Product:", fetchedProduct); // Log fetched product
                    setProduct(fetchedProduct);
                    setSelectedCategories(fetchedProduct?.categoryIds || []); // Set selected categories
                })
                .catch((error) => {
                    console.error("Error fetching product:", error);
                    alert("Product not found");
                });
        } else {
            alert("Product ID is missing");
        }

        // Fetch all categories for the checkboxes
        getAllCategories()
            .then((data) => {
                if (Array.isArray(data)) {
                    setCategories(data);
                } else {
                    console.error('Categories data is not an array:', data);
                    setCategories([]);
                }
            })
            .catch((error) => {
                console.error('Error fetching categories:', error);
                setCategories([]);
            });
    }, [id]);

    const handleCategoryChange = (categoryId: number) => {
        setSelectedCategories((prev) =>
            prev.includes(categoryId)
                ? prev.filter((catId) => catId !== categoryId)
                : [...prev, categoryId]
        );
    };

    const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files ? e.target.files[0] : null;
        if (file) {
            setSelectedImage(file); // Store the selected image
        }
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (product) {
            const updatedProduct: Product = {
                ...product,
                categoryIds: selectedCategories, // Gắn các category đã chọn
            };

            // Gọi API cập nhật sản phẩm
            updateProduct(updatedProduct)
                .then((response) => {
                    alert("Product updated successfully!");
                })
                .catch((error) => {
                    alert("Error updating product: " + error.message);
                });
        }
    };



    if (!product) {
        return <div>Loading product...</div>;
    }

    return (
        <form onSubmit={handleSubmit}>
            <h2>Edit Product</h2>
            <div>
                <label>
                    Name:
                    <input
                        value={product.name}
                        onChange={(e) => setProduct({ ...product, name: e.target.value })}
                    />
                </label>
            </div>
            <div>
                <label>
                    Description:
                    <textarea
                        value={product.description}
                        onChange={(e) => setProduct({ ...product, description: e.target.value })}
                    />
                </label>
            </div>
            <div>
                <label>
                    Price:
                    <input
                        type="number"
                        value={product.sellPrice}
                        onChange={(e) =>
                            setProduct({ ...product, sellPrice: Number(e.target.value) })
                        }
                    />
                </label>
            </div>
            <div>
                <label>
                    Quantity:
                    <input
                        type="number"
                        value={product.quantity}
                        onChange={(e) =>
                            setProduct({ ...product, quantity: Number(e.target.value) })
                        }
                    />
                </label>
            </div>
            <div>
                <label>
                    Import Price:
                    <input
                        type="number"
                        value={product.importPrice}
                        onChange={(e) =>
                            setProduct({ ...product, importPrice: Number(e.target.value) })
                        }
                    />
                </label>
            </div>
            <div>
                <label>
                    List Price:
                    <input
                        type="number"
                        value={product.listPrice}
                        onChange={(e) =>
                            setProduct({ ...product, listPrice: Number(e.target.value) })
                        }
                    />
                </label>
            </div>
            <div>
                <label>
                    Sold Quantity:
                    <input
                        type="number"
                        value={product.soldQuantity}
                        onChange={(e) =>
                            setProduct({ ...product, soldQuantity: Number(e.target.value) })
                        }
                    />
                </label>
            </div>
            <div>
                <h3>Categories</h3>
                {categories.map((category) => (
                    <label key={category.id}>
                        <input
                            type="checkbox"
                            value={category.id}
                            onChange={() => handleCategoryChange(category.id)}
                            checked={selectedCategories.includes(category.id)}
                        />
                        {category.name}
                    </label>
                ))}
            </div>
            <div>
                <label>
                    Product Image:
                    <input type="file" onChange={handleImageChange} />
                </label>
            </div>
            <button type="submit">Update Product</button>
        </form>
    );
};

export default ProductEditForm;
