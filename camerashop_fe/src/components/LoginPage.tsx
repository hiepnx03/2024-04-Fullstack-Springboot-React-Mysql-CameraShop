import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // Để điều hướng sau khi đăng nhập thành công
import { AuthenticationApi } from "../api/AuthenticationApi";

const LoginPage: React.FC = () => {
    const [userName, setUserName] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [error, setError] = useState<string>("");

    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const loginData = { userName, password };
            const response = await AuthenticationApi.login(loginData);

            // Lưu token vào localStorage
            localStorage.setItem("access_token", response.access_token);

            // Điều hướng đến trang chính sau khi đăng nhập thành công
            navigate("/");
        } catch (err: unknown) {
            if (err instanceof Error) {
                setError(err.message);
            } else {
                setError("Đã xảy ra lỗi trong quá trình đăng nhập.");
            }
        }
    };

    return (
        <div className="container">
            <h2>Đăng Nhập</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="userName" className="form-label">
                        Tên người dùng:
                    </label>
                    <input
                        type="text"
                        id="userName"
                        className="form-control"
                        value={userName}
                        onChange={(e) => setUserName(e.target.value)}
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">
                        Mật khẩu:
                    </label>
                    <input
                        type="password"
                        id="password"
                        className="form-control"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                {error && <div className="alert alert-danger">{error}</div>}
                <button type="submit" className="btn btn-primary">
                    Đăng Nhập
                </button>
            </form>
        </div>
    );
};

export default LoginPage;
