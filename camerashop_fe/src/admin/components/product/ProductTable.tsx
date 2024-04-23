// ProductTable.tsx

import React, {ChangeEvent, useState} from 'react';
import Modal from 'react-modal';
import ProductModel from "../../../model/ProductModel";
import FadeModal from "../../../layouts/utils/FadeModal";

interface ProductTableProps {
    products: ProductModel[]; // Mảng các sản phẩm để hiển thị
    onUpdate: (updatedProduct: ProductModel) => void; // Hàm để xử lý cập nhật sản phẩm
    onDelete: (productId: number) => void; // Hàm để xử lý xóa sản phẩm
}

const ProductTable: React.FC<ProductTableProps> = ({products, onUpdate, onDelete}) => {
    // Các biến trạng thái để quản lý việc chỉnh sửa sản phẩm
    const [editingProduct, setEditingProduct] = useState<ProductModel | null>(null);
    const [isEditing, setIsEditing] = useState(false);
    // Các biến trạng thái riêng lẻ cho từng trường của sản phẩm đang chỉnh sửa
    const [productName, setProductName] = useState<string>('');
    const [listPrice, setListPrice] = useState<number>(0);
    const [sellPrice, setSellPrice] = useState<number>(0);
    const [description, setDescription] = useState<string>('');
    const [quantity, setQuantity] = useState<number>(0);
    const [avgRating, setAvgRating] = useState<number>(0);
    const [soldQuantity, setSoldQuantity] = useState<number>(0);
    const [discountPercent, setDiscountPercent] = useState<number>(0);

    // Hàm để xử lý việc xóa sản phẩm
    const handleDelete = (productId: number) => {
        if (window.confirm("Bạn có chắc chắn muốn xóa sản phẩm này không?")) {
            onDelete(productId);
        }
    };

    // Hàm để xử lý việc chỉnh sửa sản phẩm
    const handleEdit = (product: ProductModel) => {
        // Đặt sản phẩm đang chỉnh sửa và điền dữ liệu vào các trường chỉnh sửa
        setEditingProduct(product);
        setProductName(product.productName || ''); // Sử dụng chuỗi trống làm giá trị mặc định nếu productName không được xác định
        setListPrice(product.listPrice || 0); // Sử dụng 0 làm giá trị mặc định nếu listPrice không được xác định
        setSellPrice(product.sellPrice || 0); // Sử dụng 0 làm giá trị mặc định nếu sellPrice không được xác định
        setDescription(product.description || ''); // Sử dụng chuỗi trống làm giá trị mặc định nếu description không được xác định
        setQuantity(product.quantity || 0); // Sử dụng 0 làm giá trị mặc định nếu quantity không được xác định
        setAvgRating(product.avgRating || 0); // Sử dụng 0 làm giá trị mặc định nếu avgRating không được xác định
        setSoldQuantity(product.soldQuantity || 0); // Sử dụng 0 làm giá trị mặc định nếu soldQuantity không được xác định
        setDiscountPercent(product.discountPercent || 0); // Sử dụng 0 làm giá trị mặc định nếu discountPercent không được xác định

        setIsEditing(true); // Đặt chế độ chỉnh sửa thành true
    };

    // Hàm để hủy bỏ quá trình chỉnh sửa
    const handleCancelEdit = () => {
        setIsEditing(false); // Đặt chế độ chỉnh sửa thành false
        setEditingProduct(null); // Đặt lại sản phẩm đang chỉnh sửa
    };

    // Hàm để xử lý việc cập nhật sản phẩm
    const handleUpdate = () => {
        if (editingProduct) {
            // Tạo một đối tượng sản phẩm được cập nhật với dữ liệu đã chỉnh sửa
            const updatedProduct: ProductModel = {
                ...editingProduct,
                productName,
                listPrice,
                sellPrice,
                description,
                quantity,
                avgRating,
                soldQuantity,
                discountPercent,
            };
            // Gọi hàm onUpdate để cập nhật sản phẩm
            onUpdate(updatedProduct);
            // Đặt lại chế độ chỉnh sửa và sản phẩm đang chỉnh sửa
            setIsEditing(false);
            setEditingProduct(null);
        }
    };

    // Hàm để đóng modal
    const handleClose = () => {
        setIsEditing(false); // Đặt chế độ chỉnh sửa thành false
        setEditingProduct(null); // Đặt lại sản phẩm đang chỉnh sửa
    };

    return (
        <div>
            {/* Bảng hiển thị các sản phẩm */}
            <h3 className="mb-4">Bảng Sản phẩm</h3>
            <table className="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên sản phẩm</th>
                    <th>Giá niêm yết</th>
                    <th>Giá bán</th>
                    <th>Mô tả</th>
                    <th>Số lượng</th>
                    <th>Xếp hạng</th>
                    <th>Đã bán</th>
                    <th>Giảm giá</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                {/* Lặp qua từng sản phẩm và hiển thị chi tiết của nó trong một hàng bảng */}
                {products.map((product) => (
                    <tr key={product.idProduct}>
                        <td>{product.idProduct}</td>
                        <td>{product.productName}</td>
                        <td>{product.listPrice}</td>
                        <td>{product.sellPrice}</td>
                        <td>{product.description}</td>
                        <td>{product.quantity}</td>
                        <td>{product.avgRating}</td>
                        <td>{product.soldQuantity}</td>
                        <td>{product.discountPercent}</td>

                        {/* Nút cho việc chỉnh sửa và xóa sản phẩm */}
                        <td>
                            <button className="btn btn-primary me-2" onClick={() => handleEdit(product)}>Cập nhật
                            </button>
                            <button className="btn btn-danger me-2" onClick={() => handleDelete(product.idProduct)}>Xóa
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
                                    <label htmlFor="productName" className="form-label">Tên sản phẩm:</label>
                                    <input type="text" className="form-control" id="productName" name="productName"
                                           value={productName} onChange={(e) => setProductName(e.target.value)}/>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="listPrice" className="form-label">Giá niêm yết:</label>
                                    <input type="number" className="form-control" id="listPrice" name="listPrice"
                                           value={listPrice}
                                           onChange={(e) => setListPrice(parseFloat(e.target.value))}/>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="sellPrice" className="form-label">Giá bán:</label> <input
                                    type="number" className="form-control" id="sellPrice" name="sellPrice"
                                    value={sellPrice} onChange={(e) => setSellPrice(parseFloat(e.target.value))}/>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="description" className="form-label">Mô tả:</label> <textarea
                                    className="form-control" id="description" name="description" value={description}
                                    onChange={(e) => setDescription(e.target.value)}/>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="quantity" className="form-label">Số lượng:</label>
                                    <input type="number" className="form-control" id="quantity" name="quantity"
                                           value={quantity} onChange={(e) => setQuantity(parseFloat(e.target.value))}/>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="avgRating" className="form-label">Xếp hạng:</label>
                                    <input type="number" className="form-control" id="avgRating" name="avgRating"
                                           value={avgRating}
                                           onChange={(e) => setAvgRating(parseFloat(e.target.value))}/>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="soldQuantity" className="form-label">Đã bán:</label> <input
                                    type="number" className="form-control" id="soldQuantity" name="soldQuantity"
                                    value={soldQuantity} onChange={(e) => setSoldQuantity(parseFloat(e.target.value))}/>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="discountPercent" className="form-label">Giảm giá:</label> <input
                                    type="number" className="form-control" id="soldQuantity" name="discountPercent"
                                    value={discountPercent}
                                    onChange={(e) => setDiscountPercent(parseFloat(e.target.value))}/>
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
}

export default ProductTable;
