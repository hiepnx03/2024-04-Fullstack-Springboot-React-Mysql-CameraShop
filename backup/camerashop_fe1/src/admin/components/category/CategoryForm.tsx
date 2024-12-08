import React, { useState } from 'react';
import FadeModal from '../../../layouts/utils/FadeModal';
import CategoryModel from "../../../model/CategoryModel";

interface CategoryFormProps {
    onSubmit: (newCategory: CategoryModel) => Promise<void>;
    isAdd: boolean; // Trạng thái để điều khiển sự hiển thị của modal
    handleClose: () => void; // Hàm để xử lý việc đóng modal
}

const CategoryForm: React.FC<CategoryFormProps> = ({ onSubmit, isAdd, handleClose }) => {
    const [newCategoryName, setNewCategoryName] = useState('');

    // Thay đổi kiểu đối số của hàm handleSubmit từ string thành CategoryModel
    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!newCategoryName.trim()) {
            alert('Vui lòng nhập tên danh mục.');
            return;
        }
        // Tạo một đối tượng CategoryModel mới với categoryName được nhập
        const newCategory: CategoryModel = {
            idCategory: 0, // ID có thể tùy chỉnh tùy theo yêu cầu của bạn
            categoryName: newCategoryName
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
}

export default CategoryForm;
