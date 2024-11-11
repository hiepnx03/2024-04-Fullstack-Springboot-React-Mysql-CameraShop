import React, { useEffect, useState } from 'react';
import { CategoryDTO } from '../types';
import { getCategories } from '../services/CategoryService';

const CategoryList: React.FC = () => {
    const [categories, setCategories] = useState<CategoryDTO[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            const result = await getCategories();
            setCategories(result);
        };

        fetchData();
    }, []);

    return (
        <div>
            <h1>Categories</h1>
            <ul>
                {categories.map((category) => (
                    <li key={category.idCategory}>
                        {category.categoryName}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default CategoryList;
