// LoginComponent.tsx

import React, { useState } from "react";
import { login, logout, isLoggedIn, getUserInfo } from "../api/auth"; // Import các hàm từ auth.ts

const LoginComponent: React.FC = () => {
    const [token, setToken] = useState<string>("");

    const handleLogin = () => {
        // Giả sử token được trả về từ API khi đăng nhập thành công
        const apiToken = "your-token-here"; // Thay bằng token thực tế trả về từ API
        login(apiToken);  // Lưu token vào localStorage
        setToken(apiToken);
        alert("Đăng nhập thành công!");
    };

    const handleLogout = () => {
        logout();  // Xóa token khỏi localStorage
        setToken("");
        alert("Đăng xuất thành công!");
    };

    const checkLoginStatus = () => {
        if (isLoggedIn()) {
            const userInfo = getUserInfo();
            alert(`Chào mừng ${userInfo?.sub}`);
        } else {
            alert("Bạn chưa đăng nhập.");
        }
    };

    return (
        <div>
            <h2>Login Component</h2>

            {isLoggedIn() ? (
                <div>
                    <p>Chào mừng {getUserInfo()?.sub}</p>
                    <button onClick={handleLogout}>Đăng xuất</button>
                    <button onClick={checkLoginStatus}>Kiểm tra trạng thái đăng nhập</button>
                </div>
            ) : (
                <div>
                    <button onClick={handleLogin}>Đăng nhập</button>
                </div>
            )}
        </div>
    );
};

export default LoginComponent;
