import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Footer from './layouts/header-footer/Footer';
import Navbar from './layouts/header-footer/Navbar';
import ProductList from './components/products/ProductList';
import ProductManagement from './components/admin/ProductManagement';
import CategoryManagement from './components/admin/CategoryManagement';
import { AuthenticationApi } from './api/AuthenticationApi';
import Login from "./components/Login";
import LoginPage from "./components/LoginPage";

function App() {
    const isAuthenticated = AuthenticationApi.isAuthenticated();

    return (
        <div className="App">
            <BrowserRouter>
                <Navbar />
                <Routes>
                    <Route path="/" element={isAuthenticated ? <ProductList /> : <Login />} />
                    <Route path="/admin/product" element={isAuthenticated ? <ProductManagement /> : <Login />} />
                    <Route path="/admin/category" element={isAuthenticated ? <CategoryManagement /> : <Login />} />
                    <Route path="/login" element={<LoginPage />} /> {/* Thêm route cho trang đăng nhập */}
                </Routes>
                <Footer />
            </BrowserRouter>
        </div>
    );
}

export default App;
