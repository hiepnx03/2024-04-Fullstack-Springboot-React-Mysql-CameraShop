import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Footer from './layouts/header-footer/Footer';
import Navbar from './layouts/header-footer/Navbar';
import ProductManagement from './components/admin/components/product/ProductManagement';
import CategoryManagement from './components/admin/components/category/CategoryManagement';
import LoginForm from "./components/auth/LoginForm";

import ProductList from "./components/products/ProductList";
import ProductForm from "./testttt/ProductForm";
import ProductList1 from "./testttt/ProductList1";
import ProductEditForm from "./testttt/ProductEditForm";

function App() {

    return (
        <div className="App">
            <BrowserRouter>
                <Navbar />
                <Routes>
                    <Route path="/login" element={<LoginForm />} />
                    <Route path="/" element={<ProductList />} />
                    <Route path="/admin/product" element={<ProductManagement />} />
                    <Route path="/admin/category" element={<CategoryManagement />} />

                    <Route path="/product-list" element={<ProductList1 />} />
                    <Route path="/edit-product/:id" element={<ProductEditForm />} /> {/* Define dynamic route */}
                    <Route path="/product-form" element={<ProductForm />} />
                </Routes>
                <Footer />
            </BrowserRouter>
        </div>
    );
}

export default App;
