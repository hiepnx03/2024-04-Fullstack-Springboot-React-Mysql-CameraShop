import React from 'react';
import CategoryModel from '../../../model/CategoryModel';

interface CategoryTableProps {
    categories: CategoryModel[];
    onUpdate: (category: CategoryModel) => void;
    onDelete: (idCategory: number) => void;
}

const CategoryTable: React.FC<CategoryTableProps> = ({ categories, onUpdate, onDelete }) => {
    if (!categories || categories.length === 0) {
        return <div>No categories to display.</div>;
    }

    console.log('Categories:', categories);

    return (
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            {categories.map(category => (
                <tr key={category.idCategory}>
                    <td>{category.idCategory}</td>
                    <td>{category.categoryName}</td>
                    <td>
                        <div className="modal-footer">
                            <button className="btn btn-primary me-2" onClick={() => onUpdate(category)}>Update</button>
                            <button className="btn btn-danger me-2"
                                    onClick={() => onDelete(category.idCategory)}>Delete
                            </button>
                        </div>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default CategoryTable;
