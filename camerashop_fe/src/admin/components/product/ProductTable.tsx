// ProductTable.tsx
import React, {ChangeEvent, useState} from 'react';
import Modal from 'react-modal';
import ProductModel from "../../../model/ProductModel";
import FadeModal from "../../../layouts/utils/FadeModal";

interface ProductTableProps {
    products: ProductModel[];
    onUpdate: (updatedProduct: ProductModel) => void;
    onDelete: (productId: number) => void;
}

const ProductTable: React.FC<ProductTableProps> = ({products, onUpdate, onDelete}) => {
    const [editingProduct, setEditingProduct] = useState<ProductModel | null>(null);
    const [isEditing, setIsEditing] = useState(false);
    const [productName, setProductName] = useState<string>(''); // Separate state for product name
    const [listPrice, setListPrice] = useState<number>(0); // Separate state for list price
    const [sellPrice, setSellPrice] = useState<number>(0); // Separate state for sell price
    const [description, setDescription] = useState<string>(''); // Separate state for description

    const handleDelete = (productId: number) => {
        if (window.confirm("Bạn có chắc chắn muốn xóa sản phẩm này không?")) {
            onDelete(productId);
        }
    };

    const handleEdit = (product: ProductModel) => {
        setEditingProduct(product);
        setProductName(product.productName || ''); // Use empty string as default value if productName is undefined
        setListPrice(product.listPrice || 0); // Use 0 as default value if listPrice is undefined
        setSellPrice(product.sellPrice || 0); // Use 0 as default value if sellPrice is undefined
        setDescription(product.description || ''); // Use empty string as default value if description is undefined
        setIsEditing(true);
    };


    const handleCancelEdit = () => {
        setIsEditing(false);
        setEditingProduct(null);
    };

    const handleUpdate = () => {
        if (editingProduct) {
            const updatedProduct = {...editingProduct, productName, listPrice, sellPrice, description};
            onUpdate(updatedProduct);
            setIsEditing(false);
            setEditingProduct(null);
        }
    };

    const handleClose = () => {
        setIsEditing(false);
        setEditingProduct(null);
    };

    return (
        <div>
            <h3 className="mb-4">Bảng Sản phẩm</h3>
            <table className="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên sản phẩm</th>
                    <th>Giá niêm yết</th>
                    <th>Giá bán</th>
                    <th>Mô tả</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                {products.map((product) => (
                    <tr key={product.idProduct}>
                        <td>{product.idProduct}</td>
                        <td>{product.productName}</td>
                        <td>{product.listPrice}</td>
                        <td>{product.sellPrice}</td>
                        <td>{product.description}</td>
                        <td>
                            <button className="btn btn-primary me-2" onClick={() => handleEdit(product)}>Cập nhật</button>
                            <button className="btn btn-danger me-2" onClick={() => handleDelete(product.idProduct)}>Xóa</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
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
                            <div className="mb-3">
                                    <label htmlFor="productName" className="form-label">Tên sản phẩm:</label>
                                    <input type="text" className="form-control" id="productName" name="productName" value={productName} onChange={(e) => setProductName(e.target.value)} />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="listPrice" className="form-label">Giá niêm yết:</label>
                                    <input type="number" className="form-control" id="listPrice" name="listPrice" value={listPrice} onChange={(e) => setListPrice(parseFloat(e.target.value))} />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="sellPrice" className="form-label">Giá bán:</label>
                                    <input type="number" className="form-control" id="sellPrice" name="sellPrice" value={sellPrice} onChange={(e) => setSellPrice(parseFloat(e.target.value))} />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="description" className="form-label">Mô tả:</label>
                                    <textarea className="form-control" id="description" name="description" value={description} onChange={(e) => setDescription(e.target.value)}/>
                                </div>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-success me-2" onClick={handleUpdate}>Lưu</button>
                            <button type="button" className="btn btn-danger me-2" onClick={handleCancelEdit}>Hủy</button>
                        </div>
                    </div>
                </div>
            </FadeModal>
        </div>
    );
}

export default ProductTable;
