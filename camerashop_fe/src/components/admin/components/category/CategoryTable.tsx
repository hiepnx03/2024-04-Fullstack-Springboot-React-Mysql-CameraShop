import React, { useState } from 'react';
import FadeModal from '../../../../layouts/utils/FadeModal';
import Category from '../../../../model/Category';

interface CategoryTableProps {
    categories: Category[];
    onUpdate: (category: Category) => void;
    onDelete: (idCategory: number) => void;
}

const CategoryTable: React.FC<CategoryTableProps> = ({ categories, onUpdate, onDelete }) => {
    const [editingCategory, setEditingCategory] = useState<Category | null>(null);
    const [isEditing, setIsEditing] = useState(false);
    const [name, setName] = useState<string>('');

    const handleDelete = (categoryId: number) => {
        if (window.confirm('Bạn có chắc chắn muốn xóa danh mục này không?')) {
            onDelete(categoryId);
        }
    };

    const handleEdit = (category: Category) => {
        setEditingCategory(category);
        setName(category.name || '');
        setIsEditing(true);
    };

    const handleCancelEdit = () => {
        setIsEditing(false);
        setEditingCategory(null);
    };

    const handleUpdate = () => {
        if (editingCategory) {
            const updatedCategory: Category = {
                ...editingCategory,
                name,
            };
            onUpdate(updatedCategory);
            setIsEditing(false);
            setEditingCategory(null);
        }
    };

    const handleClose = () => {
        setIsEditing(false);
        setEditingCategory(null);
    };

    return (
        <div className="container">
            <h3 className="mb-4">Bảng danh mục sản phẩm</h3>
            <table className="table table-bordered">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {categories.length > 0 ? (
                    categories.map((category) => (
                        <tr key={category.id}>
                            <td>{category.id}</td>
                            <td>{category.name}</td>
                            <td>
                                <button
                                    className="btn btn-primary me-2"
                                    onClick={() => handleEdit(category)}
                                >
                                    Cập nhật
                                </button>
                                <button
                                    className="btn btn-danger"
                                    onClick={() => handleDelete(category.id)}
                                >
                                    Xóa
                                </button>
                            </td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan={3} className="text-center">
                            Không có danh mục nào
                        </td>
                    </tr>
                )}
                </tbody>
            </table>

            {isEditing && (
                <FadeModal open={isEditing} handleClose={handleClose} className="modal fade">
                    <div className="modal-dialog modal-dialog-centered">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title">Cập nhật danh mục</h5>
                                <button type="button" className="btn-close" onClick={handleClose}></button>
                            </div>
                            <div className="modal-body">
                                <form>
                                    <div className="mb-3">
                                        <label htmlFor="name" className="form-label">Tên danh mục:</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="name"
                                            value={name}
                                            onChange={(e) => setName(e.target.value)}
                                        />
                                    </div>
                                </form>
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-success" onClick={handleUpdate}>Lưu</button>
                                <button type="button" className="btn btn-secondary" onClick={handleCancelEdit}>Hủy</button>
                            </div>
                        </div>
                    </div>
                </FadeModal>

            )}
        </div>
    );
};

export default CategoryTable;
