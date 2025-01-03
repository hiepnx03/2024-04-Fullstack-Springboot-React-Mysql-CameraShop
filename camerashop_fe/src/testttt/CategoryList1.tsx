import React, { useEffect, useState } from "react";
import { Category } from "./Interface";  // Import interface Category

const CategoryList1: React.FC = () => {
    const [categories, setCategories] = useState<Category[]>([]);

    useEffect(() => {
        // Giả sử bạn lấy dữ liệu từ API hoặc có dữ liệu sẵn
        const fetchCategories = async () => {
            const response = await fetch('http://localhost:8080/api/categories');  // Địa chỉ API của bạn
            const data = await response.json();
            setCategories(data.data);  // Dữ liệu của bạn là thuộc tính 'data' trong phản hồi
        };

        fetchCategories();
    }, []);

    return (
        <div>
            <h2>Category List</h2>
            {categories.map((category) => (
                <div key={category.id} style={{ marginBottom: '20px', border: '1px solid #ccc', padding: '10px' }}>
                    <h3>{category.name}</h3>
                    <p><strong>Description:</strong> {category.description}</p>
                    <img src={category.image} alt={category.name} style={{ width: '100px', height: '100px' }} />
                    <p><strong>Active:</strong> {category.active ? 'Yes' : 'No'}</p>
                    <p><strong>Editable:</strong> {category.editable ? 'Yes' : 'No'}</p>
                    <p><strong>Products:</strong> {category.productIds.join(", ")}</p>
                </div>
            ))}
        </div>
    );
};

export default CategoryList1;
