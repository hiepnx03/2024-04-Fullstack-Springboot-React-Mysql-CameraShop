import React from "react";
import {NavLink} from "react-router-dom";
function Navbar(){
    return (

        <div className={"sticky"}>
            <nav className="navbar navbar-expand-lg navbar-dark " style={{background:"#4a4a4a"}}>
                <div className="container-fluid">
                    {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
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
                                <NavLink className="nav-link active" aria-current="page" to="/">
                                    TRANG CHỦ
                                </NavLink>
                            </li>

                            {/*MÁY ẢNH*/}
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

                            {/*ỐNG KÍNH*/}
                            <li className="nav-item dropdown">
                                <NavLink className="nav-link dropdown-toggle" to="#" id="navbarDropdown1" role="button"
                                         data-bs-toggle="dropdown" aria-expanded="false">
                                    ỐNG KÍNH
                                </NavLink>
                                <ul className="dropdown-menu" aria-labelledby="navbarDropdown1">
                                    <li><NavLink className="dropdown-item" to="/1">MÁY ẢNH 1</NavLink></li>
                                    <li><NavLink className="dropdown-item" to="/2">MÁY ẢNH 2</NavLink></li>
                                    <li><NavLink className="dropdown-item" to="/3">MÁY ẢNH 3</NavLink></li>
                                </ul>
                            </li>

                            {/*DRONE*/}
                            <li className="nav-item dropdown">
                                <NavLink className="nav-link dropdown-toggle" to="#" id="navbarDropdown1" role="button"
                                         data-bs-toggle="dropdown" aria-expanded="false">
                                    DRONE
                                </NavLink>
                                <ul className="dropdown-menu" aria-labelledby="navbarDropdown1">
                                    <li><NavLink className="dropdown-item" to="/1">MÁY ẢNH 1</NavLink></li>
                                    <li><NavLink className="dropdown-item" to="/2">MÁY ẢNH 2</NavLink></li>
                                    <li><NavLink className="dropdown-item" to="/3">MÁY ẢNH 3</NavLink></li>
                                </ul>
                            </li>

                            <li className="nav-item dropdown">
                                <NavLink className="nav-link dropdown-toggle" to="#" id="navbarDropdown1" role="button"
                                         data-bs-toggle="dropdown" aria-expanded="false">
                                    PHỤ KIỆN
                                </NavLink>
                                <ul className="dropdown-menu" aria-labelledby="navbarDropdown1">
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
                            <a className="nav-link" href="#">
                                <i className="fas fa-shopping-cart"></i>
                            </a>
                        </li>
                    </ul>

                    {/* Biểu tượng đăng nhập */}
                    <ul className="navbar-nav me-1">
                        <li className="nav-item">
                            <a className="nav-link" href="#">
                                <i className="fas fa-user"></i>
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    )
}

export default Navbar;