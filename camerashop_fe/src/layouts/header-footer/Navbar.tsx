import React from 'react';
import { NavLink, useNavigate } from "react-router-dom";

// Example utility to check if the user is logged in
// You can replace this with a more robust authentication context or API check


const Navbar: React.FC = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        // Xóa token khỏi localStorage khi người dùng đăng xuất
        localStorage.removeItem("access_token");
        navigate("/login"); // Điều hướng về trang đăng nhập
    };

    const isAuthenticated = localStorage.getItem("access_token");

    return (
        <div className="sticky">
            <nav className="navbar navbar-expand-lg navbar-dark" style={{ background: "#4a4a4a" }}>
                <div className="container-fluid">
                    <a className="navbar-brand" href="#">
                        Camera Shop
                    </a>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>

                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                            <li className="nav-item">
                                <NavLink className="nav-link" aria-current="page" to="/">
                                    TRANG CHỦ
                                </NavLink>
                            </li>

                            {/* MÁY ẢNH */}
                            <li className="nav-item dropdown">
                                <NavLink className="nav-link dropdown-toggle" to="#" id="navbarDropdown1" role="button"
                                         data-bs-toggle="dropdown" aria-expanded="false">
                                    MÁY ẢNH
                                </NavLink>
                                <ul className="dropdown-menu" aria-labelledby="navbarDropdown1">
                                    <li><NavLink className="dropdown-item" to="/1">MÁY ẢNH 1</NavLink></li>
                                    <li><NavLink className="dropdown-item" to="/2">MÁY ẢNH 2</NavLink></li>
                                    <li><NavLink className="dropdown-item" to="/3">MÁY ẢNH 3</NavLink></li>
                                </ul>
                            </li>

                            {/* ỐNG KÍNH */}
                            <li className="nav-item dropdown">
                                <NavLink className="nav-link dropdown-toggle" to="#" id="navbarDropdown2" role="button"
                                         data-bs-toggle="dropdown" aria-expanded="false">
                                    ỐNG KÍNH
                                </NavLink>
                                <ul className="dropdown-menu" aria-labelledby="navbarDropdown2">
                                    <li><NavLink className="dropdown-item" to="/1">MÁY ẢNH 1</NavLink></li>
                                    <li><NavLink className="dropdown-item" to="/2">MÁY ẢNH 2</NavLink></li>
                                    <li><NavLink className="dropdown-item" to="/3">MÁY ẢNH 3</NavLink></li>
                                </ul>
                            </li>

                            {/* DRONE */}
                            <li className="nav-item dropdown">
                                <NavLink className="nav-link dropdown-toggle" to="#" id="navbarDropdown3" role="button"
                                         data-bs-toggle="dropdown" aria-expanded="false">
                                    DRONE
                                </NavLink>
                                <ul className="dropdown-menu" aria-labelledby="navbarDropdown3">
                                    <li><NavLink className="dropdown-item" to="/1">MÁY ẢNH 1</NavLink></li>
                                    <li><NavLink className="dropdown-item" to="/2">MÁY ẢNH 2</NavLink></li>
                                    <li><NavLink className="dropdown-item" to="/3">MÁY ẢNH 3</NavLink></li>
                                </ul>
                            </li>

                            {/* PHỤ KIỆN */}
                            <li className="nav-item dropdown">
                                <NavLink className="nav-link dropdown-toggle" to="#" id="navbarDropdown4" role="button"
                                         data-bs-toggle="dropdown" aria-expanded="false">
                                    PHỤ KIỆN
                                </NavLink>
                                <ul className="dropdown-menu" aria-labelledby="navbarDropdown4">
                                    <li><NavLink className="dropdown-item" to="/1">MÁY ẢNH 1</NavLink></li>
                                    <li><NavLink className="dropdown-item" to="/2">MÁY ẢNH 2</NavLink></li>
                                    <li><NavLink className="dropdown-item" to="/3">MÁY ẢNH 3</NavLink></li>
                                </ul>
                            </li>
                        </ul>
                    </div>

                    {/* Biểu tượng giỏ hàng */}
                    <ul className="navbar-nav me-1">
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/cart">
                                <i className="fas fa-shopping-cart"></i>
                                {/* Show cart item count (sample, replace with actual cart count) */}
                                <span className="badge bg-danger">3</span>
                            </NavLink>
                        </li>
                    </ul>


                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/">Trang Chủ</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/admin/product">Quản Lý Sản Phẩm</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/admin/category">Quản Lý Danh Mục</NavLink>
                            </li>
                        </ul>
                    </div>

                    {/* Hiển thị đăng nhập/đăng xuất */}
                    <ul className="navbar-nav me-1">
                        {isAuthenticated ? (
                            <li className="nav-item">
                                <button className="nav-link btn" onClick={handleLogout}>
                                    <i className="fas fa-sign-out-alt"></i> Đăng Xuất
                                </button>
                            </li>
                        ) : (
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/login">
                                    <i className="fas fa-user"></i> Đăng Nhập
                                </NavLink>
                            </li>
                        )}
                    </ul>
                </div>
            </nav>
        </div>
    );
};

export default Navbar;
