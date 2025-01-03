import React, { useState } from 'react';
import FadeModal from "../../../../layouts/utils/FadeModal";
import Product from "../../../../model/Product";

interface ProductTableProps {
    products: Product[];  // Mảng các sản phẩm để hiển thị
    allCategories: { id: number, name: string }[];  // Mảng danh mục
    onUpdate: (updatedProduct: Product) => void;  // Hàm để xử lý cập nhật sản phẩm
    onDelete: (productId: number) => void;  // Hàm để xử lý xóa sản phẩm
}

const ProductTable: React.FC<ProductTableProps> = ({ products, allCategories, onUpdate, onDelete }) => {
    const [editingProduct, setEditingProduct] = useState<Product | null>(null);
    const [isEditing, setIsEditing] = useState(false);

    const handleDelete = (productId: number) => {
        if (window.confirm("Bạn có chắc chắn muốn xóa sản phẩm này không?")) {
            onDelete(productId);
        }
    };

    const handleEdit = (product: Product) => {
        setEditingProduct(product);
        setIsEditing(true);
    };

    const handleCancelEdit = () => {
        setIsEditing(false);
        setEditingProduct(null);
    };

    const handleSaveEdit = (updatedProduct: Product) => {
        if (updatedProduct) {
            onUpdate(updatedProduct);
        }
        handleCancelEdit();
    };

    const getCategoryNames = (categoryIds: number[]) => {
        return categoryIds
            .map(id => {
                const category = allCategories.find(cat => cat.id === id);
                return category ? category.name : "Không có danh mục";
            })
            .join(', ');
    };

    return (
        <div>
            <h3 className="mb-4 text-center">Bảng Sản phẩm</h3>
            <table className="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên sản phẩm</th>
                    <th>Giá nhập</th>
                    <th>Giá niêm yết</th>
                    <th>Giá bán</th>
                    <th>Mô tả</th>
                    <th>Số lượng</th>
                    <th>Đã bán</th>
                    <th>Danh mục</th>
                    <th>Ảnh</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                {products.map((product) => (
                    <tr key={product.id}>
                        <td>{product.id}</td>
                        <td>{product.name}</td>
                        <td>{product.importPrice}</td>
                        <td>{product.listPrice}</td>
                        <td>{product.sellPrice}</td>
                        <td>{product.description}</td>
                        <td>{product.quantity}</td>
                        <td>{product.soldQuantity}</td>
                        <td>{getCategoryNames(product.categoryIds)}</td>
                        <td>
                            {product.images && product.images.length > 0 ? (
                                product.images.map(image => (
                                    <img key={image.id} src={image.url} alt={`Product Image ${image.id}`}
                                         style={{ width: "60px", height: "auto", marginRight: "10px" }} />
                                ))
                            ) : (
                                "Không có ảnh"
                            )}
                        </td>
                        <td>
                            <button className="btn btn-primary me-2" onClick={() => handleEdit(product)}>Cập nhật</button>
                            <button className="btn btn-danger me-2" onClick={() => handleDelete(product.id)}>Xóa</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            {/* Modal chỉnh sửa sản phẩm */}
            {isEditing && editingProduct && (
                <FadeModal open={isEditing} handleClose={handleCancelEdit} className="custom-modal-class">
                    <div className="modal-dialog modal-dialog-centered">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title">Cập nhật sản phẩm</h5>
                                <button type="button" className="btn-close" onClick={handleCancelEdit}></button>
                            </div>
                            <div className="modal-body">
                                <form>
                                    <div className="mb-3">
                                        <label className="form-label">Tên sản phẩm</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            value={editingProduct.name}
                                            onChange={(e) => setEditingProduct({ ...editingProduct, name: e.target.value })}
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label className="form-label">Mô tả</label>
                                        <textarea
                                            className="form-control"
                                            value={editingProduct.description}
                                            onChange={(e) => setEditingProduct({ ...editingProduct, description: e.target.value })}
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label className="form-label">Giá nhập</label>
                                        <input
                                            type="number"
                                            className="form-control"
                                            value={editingProduct.importPrice}
                                            onChange={(e) => setEditingProduct({ ...editingProduct, importPrice: parseFloat(e.target.value) })}
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label className="form-label">Giá bán</label>
                                        <input
                                            type="number"
                                            className="form-control"
                                            value={editingProduct.sellPrice}
                                            onChange={(e) => setEditingProduct({ ...editingProduct, sellPrice: parseFloat(e.target.value) })}
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label className="form-label">Danh mục</label>
                                        <select
                                            className="form-control"
                                            value={editingProduct.categoryIds[0]} // Chọn một danh mục làm mặc định
                                            onChange={(e) => setEditingProduct({ ...editingProduct, categoryIds: [parseInt(e.target.value)] })}
                                        >
                                            {allCategories.map((category) => (
                                                <option key={category.id} value={category.id}>
                                                    {category.name}
                                                </option>
                                            ))}
                                        </select>
                                    </div>
                                </form>
                                <button className="btn btn-primary" onClick={() => handleSaveEdit(editingProduct)}>Lưu</button>
                            </div>
                        </div>
                    </div>
                </FadeModal>
            )}
        </div>
    );
};

export default ProductTable;
