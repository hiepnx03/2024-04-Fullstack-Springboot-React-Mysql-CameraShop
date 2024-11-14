import React, { useState } from 'react';
import FadeModal from '../../../layouts/utils/FadeModal';
import ProductModel from "../../../model/ProductModel";

interface ProductFormProps {
    onSubmit: (newProduct: ProductModel) => void; // Hàm để xử lý việc gửi form
    isAdd: boolean; // Trạng thái để điều khiển sự hiển thị của modal
    handleClose: () => void; // Hàm để xử lý việc đóng modal
}

const ProductForm: React.FC<ProductFormProps> = ({ onSubmit, isAdd, handleClose }) => {
    // State cho sản phẩm mới được thêm vào
    const [newProduct, setNewProduct] = useState<ProductModel>({
        idProduct: 0,
        productName: '',
        listPrice: undefined,
        sellPrice: undefined,
        description: '',
        quantity: undefined,
        avgRating: undefined,
        soldQuantity: undefined,
        discountPercent: undefined,
    });

    // Xử lý sự kiện khi input thay đổi
    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setNewProduct({ ...newProduct, [name]: value });
    };

    // Xử lý việc gửi form
    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        // Gửi sản phẩm mới
        onSubmit(newProduct);
        // Reset form sau khi gửi
        setNewProduct({
            idProduct: 0,
            productName: '',
            listPrice: undefined,
            sellPrice: undefined,
            description: '',
            quantity: undefined,
            avgRating: undefined,
            soldQuantity: undefined,
            discountPercent: undefined,
        });
        // Đóng modal sau khi gửi
        handleClose();
    };

    // Xử lý việc hủy thêm sản phẩm mới
    const handleCancelAdd = () => {
        handleClose(); // Đóng modal
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
                        <h3 className="mb-4">Form Sản phẩm</h3>
                    </div>
                    <div className={"text-end"}>
                        <button type="button" className="btn-close" onClick={handleClose}></button>
                    </div>
                </div>

                <form onSubmit={handleSubmit}>
                    {/* Các trường nhập cho thông tin sản phẩm */}
                    <div className="mb-3 row">
                        <label htmlFor="productName" className="col-sm-3 col-form-label">Tên sản phẩm:</label>
                        <div className="col-sm-9">
                            <input type="text" className="form-control" id="productName" name="productName" value={newProduct.productName}
                                   onChange={handleChange} placeholder="Tên sản phẩm" required />
                        </div>
                    </div>
                    <div className="mb-3 row">
                        <label htmlFor="listPrice" className="col-sm-3 col-form-label">Giá niêm yết:</label>
                        <div className="col-sm-9">
                            <input type="number" className="form-control" id="listPrice" name="listPrice" value={newProduct.listPrice}
                                   onChange={handleChange} placeholder="Giá niêm yết" required />
                        </div>
                    </div>
                    <div className="mb-3 row">
                        <label htmlFor="sellPrice" className="col-sm-3 col-form-label">Giá bán:</label>
                        <div className="col-sm-9">
                            <input type="number" className="form-control" id="sellPrice" name="sellPrice" value={newProduct.sellPrice}
                                   onChange={handleChange} placeholder="Giá bán" required />
                        </div>
                    </div>
                    <div className="mb-3 row">
                        <label htmlFor="description" className="col-sm-3 col-form-label">Mô tả:</label>
                        <div className="col-sm-9">
                            <input type="text" className="form-control" id="description" name="description"
                                   value={newProduct.description} onChange={handleChange} placeholder="Mô tả" required />
                        </div>
                    </div>
                    <div className="mb-3 row">
                        <label htmlFor="quantity" className="col-sm-3 col-form-label">Số lượng:</label>
                        <div className="col-sm-9">
                            <input type="number" className="form-control" id="quantity" name="quantity" value={newProduct.quantity}
                                   onChange={handleChange} placeholder="Số lượng" required />
                        </div>
                    </div>
                    <div className="mb-3 row">
                        <label htmlFor="avgRating" className="col-sm-3 col-form-label">Xếp hạng:</label>
                        <div className="col-sm-9">
                            <input type="number" className="form-control" id="avgRating" name="avgRating" value={newProduct.avgRating}
                                   onChange={handleChange} placeholder="Xếp hạng" required />
                        </div>
                    </div>
                    <div className="mb-3 row">
                        <label htmlFor="soldQuantity" className="col-sm-3 col-form-label">Đã bán:</label>
                        <div className="col-sm-9">
                            <input type="number" className="form-control" id="soldQuantity" name="soldQuantity"
                                   value={newProduct.soldQuantity} onChange={handleChange} placeholder="Đã bán" required />
                        </div>
                    </div>
                    <div className="mb-3 row">
                        <label htmlFor="discountPercent" className="col-sm-3 col-form-label">Giảm giá:</label>
                        <div className="col-sm-9">
                            <input type="number" className="form-control" id="discountPercent" name="discountPercent"
                                   value={newProduct.discountPercent} onChange={handleChange} placeholder="Giảm giá" required />
                        </div>
                    </div>

                    {/* Nút để gửi form và hủy */}
                    <div className="modal-footer">
                        <button type="submit" className="btn btn-primary me-2">Thêm sản phẩm</button>
                        <button type="button" className="btn btn-danger me-2" onClick={handleCancelAdd}>Hủy</button>
                    </div>
                </form>
            </div>
        </FadeModal>
    );
}

export default ProductForm;
