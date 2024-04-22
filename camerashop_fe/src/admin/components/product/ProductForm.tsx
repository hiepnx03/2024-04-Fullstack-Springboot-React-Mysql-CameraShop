import React, {useState} from 'react';
import FadeModal from '../../../layouts/utils/FadeModal';
import ProductModel from "../../../model/ProductModel";

interface ProductFormProps {
    onSubmit: (newProduct: ProductModel) => void;
    isAdd: boolean; // State to control modal visibility
    handleClose: () => void; // Function to handle modal close
}

const ProductForm: React.FC<ProductFormProps> = ({onSubmit, isAdd, handleClose}) => {
    const [newProduct, setNewProduct] = useState<ProductModel>({
        idProduct: 0,
        productName: '',
        listPrice: 0,
        sellPrice: 0,
        description: ''
    });

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = event.target;
        setNewProduct({...newProduct, [name]: value});
    };

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        onSubmit(newProduct);
        setNewProduct({idProduct: 0, productName: '', listPrice: 0, sellPrice: 0, description: ''}); // Reset form after submit
        handleClose(); // Close the modal after submit
    };

    const handleCancelAdd = () => {
        handleClose(); // Close the modal when canceling
    };

    return (
        <FadeModal
            open={isAdd}
            handleClose={handleClose}
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
                    <div className="mb-3">
                        <input type="text" className="form-control" name="productName" value={newProduct.productName}
                               onChange={handleChange} placeholder="Tên sản phẩm" required/>
                    </div>
                    <div className="mb-3">
                        <input type="number" className="form-control" name="listPrice" value={newProduct.listPrice}
                               onChange={handleChange} placeholder="Giá niêm yết" required/>
                    </div>
                    <div className="mb-3">
                        <input type="number" className="form-control" name="sellPrice" value={newProduct.sellPrice}
                               onChange={handleChange} placeholder="Giá bán" required/>
                    </div>
                    <div className="mb-3">
                        <input type="text" className="form-control" name="description" value={newProduct.description}
                               onChange={handleChange} placeholder="Mô tả" required/>
                    </div>
                </form>

                <div className="modal-footer">
                    <button type="submit" className="btn btn-primary me-2">Thêm sản phẩm</button>
                    <button type="button" className="btn btn-danger me-2" onClick={handleCancelAdd}>Hủy</button>
                </div>
            </div>
        </FadeModal>
    );
}

export default ProductForm;
