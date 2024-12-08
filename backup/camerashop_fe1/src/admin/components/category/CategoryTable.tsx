import React, {useState} from 'react';
import FadeModal from '../../../layouts/utils/FadeModal';
import CategoryModel from '../../../model/CategoryModel';

interface CategoryTableProps {
    categories: CategoryModel[];
    onUpdate: (category: CategoryModel) => void;
    onDelete: (idCategory: number) => void;
}

const CategoryTable: React.FC<CategoryTableProps> = ({categories, onUpdate, onDelete}) => {
    const [editingCategory, setEditingCategory] = useState<CategoryModel | null>(null);
    const [isEditing, setIsEditing] = useState(false);
    const [categoryName, setCategoryName] = useState<string>('');

    // Hàm để xử lý việc xóa sản phẩm
    const handleDelete = (productId: number) => {
        if (window.confirm("Bạn có chắc chắn muốn xóa sản phẩm này không?")) {
            onDelete(productId);
        }
    };

    // Hàm để xử lý việc chỉnh sửa sản phẩm
    const handleEdit = (category: CategoryModel) => {
        // Đặt sản phẩm đang chỉnh sửa và điền dữ liệu vào các trường chỉnh sửa
        setEditingCategory(category);
        setCategoryName(category.categoryName || ''); // Sử dụng chuỗi trống làm giá trị mặc định nếu categoryName không được xác định
        setIsEditing(true); // Đặt chế độ chỉnh sửa thành true
    };
    // Hàm để hủy bỏ quá trình chỉnh sửa
    const handleCancelEdit = () => {
        setIsEditing(false); // Đặt chế độ chỉnh sửa thành false
        setEditingCategory(null); // Đặt lại sản phẩm đang chỉnh sửa
    };
    const handleUpdate = () => {
        if (editingCategory) {
            // Tạo một đối tượng sản phẩm được cập nhật với dữ liệu đã chỉnh sửa
            const updatedCategory: CategoryModel = {
                ...editingCategory,
                categoryName,
            };
            // Gọi hàm onUpdate để cập nhật sản phẩm
            onUpdate(updatedCategory);
            // Đặt lại chế độ chỉnh sửa và sản phẩm đang chỉnh sửa
            setIsEditing(false);
            setEditingCategory(null);
        }
    };

    // Hàm để đóng modal
    const handleClose = () => {
        setIsEditing(false); // Đặt chế độ chỉnh sửa thành false
        setEditingCategory(null); // Đặt lại sản phẩm đang chỉnh sửa
    };

    return (
        <div className={"text-center"}>
            {/* Bảng hiển thị các sản phẩm */}
            <h3 className="mb-4">Bảng danh mục sản phẩm</h3>
            <table className="table">
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

                        {/* Nút cho việc chỉnh sửa và xóa sản phẩm */}
                        <td>
                            <button className="btn btn-primary me-2" onClick={() => handleEdit(category)}>Cập nhật
                            </button>
                            <button className="btn btn-danger me-2" onClick={() => handleDelete(category.idCategory)}>Xóa
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>


            {/* Modal để chỉnh sửa sản phẩm */}
            <FadeModal
                open={isEditing}
                handleClose={handleClose}
                className="modal fade"
            >
                <div className="modal-dialog modal-dialog-centered">
                    <div className="modal-content">
                        <div className="modal-header row-cols-2">
                            <div>
                                <h5 className="modal-title me-2">Cập nhật sản phẩm</h5>
                            </div>
                            <div className={"text-end"}>
                                <button type="button" className="btn-close" onClick={handleClose}></button>
                            </div>
                        </div>
                        <div className="modal-body">
                            <form>
                                {/* Các trường chỉnh sửa sản phẩm */}
                                <div className="mb-3">
                                    <label htmlFor="categoryName" className="form-label">Tên danh mục sản phẩm:</label>
                                    <input type="text" className="form-control" id="categoryName" name="categoryName"
                                           value={categoryName} onChange={(e) => setCategoryName(e.target.value)}/>
                                </div>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-success me-2" onClick={handleUpdate}>Lưu</button>
                            <button type="button" className="btn btn-danger me-2" onClick={handleCancelEdit}>Hủy
                            </button>
                        </div>
                    </div>
                </div>
            </FadeModal>
        </div>
    );
};

export default CategoryTable;
