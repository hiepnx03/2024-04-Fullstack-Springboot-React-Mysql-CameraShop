import React, { useState } from 'react';
import FadeModal from '../../../../layouts/utils/FadeModal';
import Category from "../../../../model/Category";

interface CategoryFormProps {
    onSubmit: (newCategory: Category) => Promise<void>;
    isAdd: boolean; // Trạng thái để điều khiển sự hiển thị của modal
    handleClose: () => void; // Hàm để xử lý việc đóng modal
}

const CategoryForm: React.FC<CategoryFormProps> = ({ onSubmit, isAdd, handleClose }) => {
    const [newCategoryName, setNewCategoryName] = useState<string>('');

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!newCategoryName.trim()) {
            alert('Vui lòng nhập tên danh mục.');
            return;
        }
        // Create a new Category object
        const newCategory: Category = {
            id: 0, // Assuming ID is auto-generated or handled server-side
            name: newCategoryName,
            description: '',
            image: '',
            active: true,
            deleted: false,
            editable: true,
            visible: true,
            slug: '',
            status: 1,
            products: [],
        };
        try {
            await onSubmit(newCategory);
            setNewCategoryName('');
            handleClose();
        } catch (error) {
            console.error('Lỗi khi thêm danh mục:', error);
        }
    };

    return (
        <FadeModal
            open={isAdd} // Điều khiển việc hiển thị modal
            handleClose={handleClose} // Hàm để xử lý việc đóng modal
            className="modal fade"
        >
            <div>
                <div className={"modal-header row-cols-2"}>
                    <div>
                        <h3 className="mb-4">Form Danh mục</h3>
                    </div>
                    <div className={"text-end"}>
                        <button type="button" className="btn-close" onClick={handleClose}></button>
                    </div>
                </div>

                <form onSubmit={handleSubmit}>
                    <div className="mb-3 row">
                        <label htmlFor="categoryName" className="col-sm-3 col-form-label">Tên danh mục:</label>
                        <div className="col-sm-9">
                            <input
                                type="text"
                                className="form-control"
                                id="categoryName"
                                name="categoryName"
                                value={newCategoryName}
                                onChange={(e) => setNewCategoryName(e.target.value)}
                                placeholder="Nhập tên danh mục mới"
                            />
                        </div>
                    </div>
                    <button type="submit" className="btn btn-primary">Tạo mới</button>
                </form>
            </div>
        </FadeModal>
    );
};

export default CategoryForm;
