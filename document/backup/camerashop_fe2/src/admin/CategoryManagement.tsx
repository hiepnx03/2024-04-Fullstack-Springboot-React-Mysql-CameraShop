import React, { useState, useEffect } from "react";
import CategoryTable from "./components/category/CategoryTable";
import CategoryForm from "./components/category/CategoryForm";
import CategoryModel from "../model/CategoryModel";
import { getAllCategories, createCategory, updateCategory, deleteCategory } from "../api/CategoryApi";



const CategoryManagement: React.FC = () => {
    const [categoryList, setCategoryList] = useState<CategoryModel[]>([]);
    const [isAdding, setIsAdding] = useState(false); // Trạng thái để điều khiển sự hiển thị của modal

    useEffect(() => {
        loadData(); // Load dữ liệu danh mục khi component được render
    }, []);

    // Hàm để load dữ liệu danh mục
    const loadData = async () => {
        try {
            const categories = await getAllCategories();
            setCategoryList(categories);
        } catch (error) {
            console.error('Lỗi khi tải dữ liệu danh mục:', error);
        }
    };

    // Hàm xử lý khi cần thêm mới danh mục
    // Thay đổi kiểu đối số của hàm handleAddCategory từ string thành CategoryModel
    const handleAddCategory = async (newCategory: CategoryModel) => {
        try {
            // Gọi createCategory với đối tượng CategoryModel
            await createCategory(newCategory);
            await loadData(); // Sau khi thêm danh mục, cập nhật lại danh sách
            closeModal(); // Đóng modal sau khi thêm danh mục
        } catch (error) {
            console.error('Lỗi khi thêm danh mục:', error);
        }
    };




    // Hàm xử lý khi cần cập nhật danh mục
    const handleUpdateCategory = async (updatedCategory: CategoryModel) => {
        try {
            await updateCategory(updatedCategory.idCategory, updatedCategory);
            await loadData(); // Sau khi cập nhật danh mục, cập nhật lại danh sách
        } catch (error) {
            console.error('Lỗi khi cập nhật danh mục:', error);
        }
    };

    // Hàm xử lý khi cần xóa danh mục
    const handleDeleteCategory = async (categoryId: number) => {
        try {
            await deleteCategory(categoryId);
            await loadData(); // Sau khi xóa danh mục, cập nhật lại danh sách
        } catch (error) {
            console.error('Lỗi khi xóa danh mục:', error);
        }
    };

    // Hàm để đóng modal
    const closeModal = () => {
        setIsAdding(false);
    };

    // Hàm để mở modal
    const openModal = () => {
        setIsAdding(true);
    };

    return (
        <div>
            <h2>Quản lý Danh mục</h2>
            <button className="btn btn-primary" onClick={openModal}>Thêm danh mục</button>
            <CategoryForm onSubmit={handleAddCategory} isAdd={isAdding} handleClose={closeModal} />
            <CategoryTable categories={categoryList} onUpdate={handleUpdateCategory} onDelete={handleDeleteCategory} />
        </div>
    );
};

export default CategoryManagement;
