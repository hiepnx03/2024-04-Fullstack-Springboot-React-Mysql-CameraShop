import React, { useState, useEffect } from 'react';
import { createProduct } from './ProductService';
import { getAllCategories } from './CategoryService';
import { Category, Product } from './Interface';

const ProductForm: React.FC = () => {
    const [product, setProduct] = useState<Product>({
        id: 0,
        name: '',
        description: '',
        sellPrice: 0,
        importPrice: 0,
        listPrice: 0,
        quantity: 0,
        soldQuantity: 0,
        categoryIds: [],
        images: []
    });
    const [categories, setCategories] = useState<Category[]>([]);
    const [selectedCategories, setSelectedCategories] = useState<number[]>([]);

    useEffect(() => {
        // Lấy tất cả các category từ API
        getAllCategories()
            .then((response) => {
                if (Array.isArray(response)) {
                    setCategories(response); // Lưu các danh mục vào state
                    console.log("Danh sách category từ API:", response);
                } else {
                    console.error('Dữ liệu trả về không phải là mảng:', response);
                }
            })
            .catch((error) => {
                console.error('Lỗi khi gọi API:', error);
            });
    }, []);  // Chỉ chạy khi component được mount

    useEffect(() => {
        // Khi product thay đổi, đồng bộ lại selectedCategories
        console.log("Dữ liệu sản phẩm cập nhật:", product);
        setSelectedCategories(product.categoryIds);
    }, [product]);

    const handleCategoryChange = (id: number) => {
        // Cập nhật lại danh sách category được chọn
        setSelectedCategories((prev) => {
            const updated = prev.includes(id)
                ? prev.filter((catId) => catId !== id)
                : [...prev, id];
            console.log("Danh sách category đã chọn sau khi thay đổi:", updated);
            return updated;
        });
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        // Kiểm tra các trường cần thiết
        if (!product.name || product.sellPrice <= 0 || product.quantity < 0) {
            alert('Vui lòng điền đầy đủ thông tin sản phẩm!');
            return;
        }

        // Cập nhật lại categoryIds từ selectedCategories
        const newProduct: Product = {
            ...product,
            categoryIds: selectedCategories
        };

        console.log("Dữ liệu sản phẩm chuẩn bị gửi:", newProduct);

        createProduct(newProduct)
            .then(() => {
                alert('Sản phẩm đã được thêm thành công!');
                setProduct({
                    id: 0,
                    name: '',
                    description: '',
                    sellPrice: 0,
                    importPrice: 0,
                    listPrice: 0,
                    quantity: 0,
                    soldQuantity: 0,
                    categoryIds: [],
                    images: [],
                });
                setSelectedCategories([]); // Xóa danh sách category đã chọn
            })
            .catch((error) => {
                alert('Lỗi khi thêm sản phẩm: ' + error.message);
            });
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Thêm Sản Phẩm Mới</h2>

            <div>
                <label>Tên Sản Phẩm:</label>
                <input
                    type="text"
                    value={product.name}
                    onChange={(e) => setProduct({ ...product, name: e.target.value })}
                    required
                />
            </div>

            <div>
                <label>Mô Tả:</label>
                <textarea
                    value={product.description}
                    onChange={(e) => setProduct({ ...product, description: e.target.value })}
                />
            </div>

            <div>
                <label>Giá Bán:</label>
                <input
                    type="number"
                    value={product.sellPrice}
                    onChange={(e) => setProduct({ ...product, sellPrice: Number(e.target.value) })}
                    required
                    min="0"
                />
            </div>

            <div>
                <label>Giá Nhập:</label>
                <input
                    type="number"
                    value={product.importPrice}
                    onChange={(e) => setProduct({ ...product, importPrice: Number(e.target.value) })}
                    min="0"
                />
            </div>

            <div>
                <label>Giá Liệt Kê:</label>
                <input
                    type="number"
                    value={product.listPrice}
                    onChange={(e) => setProduct({ ...product, listPrice: Number(e.target.value) })}
                    min="0"
                />
            </div>

            <div>
                <label>Số Lượng:</label>
                <input
                    type="number"
                    value={product.quantity}
                    onChange={(e) => setProduct({ ...product, quantity: Number(e.target.value) })}
                    min="0"
                />
            </div>

            <div>
                <label>Số Lượng Đã Bán:</label>
                <input
                    type="number"
                    value={product.soldQuantity}
                    onChange={(e) => setProduct({ ...product, soldQuantity: Number(e.target.value) })}
                    min="0"
                />
            </div>

            <div>
                <h3>Danh Mục</h3>
                {categories.length > 0 ? (
                    categories.map((category) => (
                        <label key={category.id}>
                            <input
                                type="checkbox"
                                value={category.id}
                                onChange={() => handleCategoryChange(category.id)}
                                checked={selectedCategories.includes(category.id)}
                            />
                            {category.name}
                        </label>
                    ))
                ) : (
                    <p>Không có danh mục nào!</p>
                )}
            </div>

            <div>
                <label>Hình ảnh:</label>
                <input
                    type="file"
                    multiple
                    onChange={(e) => {
                        const files = Array.from(e.target.files || []);
                        setProduct({
                            ...product,
                            images: files.map((file, index) => ({
                                id: index,
                                url: URL.createObjectURL(file),
                                order: index,
                            })),
                        });
                    }}
                />
                <div>
                    {product.images.map((image, index) => (
                        <img key={index} src={image.url} alt={`image-${index}`} style={{ width: '100px' }} />
                    ))}
                </div>
            </div>

            <button type="submit">Thêm Sản Phẩm</button>
        </form>
    );
};

export default ProductForm;
