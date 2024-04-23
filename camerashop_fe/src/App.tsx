import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Footer from './layouts/header-footer/Footer';
import Navbar from './layouts/header-footer/Navbar';
import ProductList from './layouts/products/ProductList';
import ProductManagement from "./admin/ProductManagement";
import CategoryManagement from "./admin/CategoryManagement";

function App() {
    return (
        <div className="App">
            <BrowserRouter>
                <Navbar />
                <Routes>
                    <Route path="/" element={<ProductList />} />
                    <Route path="/admin/product" element={<ProductManagement />} />
                    <Route path="/admin/category" element={<CategoryManagement/>} />

                </Routes>
                <Footer />
            </BrowserRouter>
        </div>
    );
}

export default App;
