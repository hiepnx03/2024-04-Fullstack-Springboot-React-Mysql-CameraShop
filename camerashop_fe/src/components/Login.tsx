import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthenticationApi } from '../api/AuthenticationApi';

const Login: React.FC = () => {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [errorMessage, setErrorMessage] = useState<string>('');
    const navigate = useNavigate();

    // Handle form submission
    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const data = await AuthenticationApi.login({ userName: username, password: password });
            // Save the token in localStorage
            localStorage.setItem('access_token', data.access_token);

            // Redirect to the home page or dashboard
            navigate('/'); // Or navigate to a protected route
        } catch (error: any) {
            setErrorMessage(error.message);
        }
    };

    return (
        <div className="container mt-5">
            <h2 className="text-center">Đăng Nhập</h2>
            <form onSubmit={handleLogin}>
                <div className="mb-3">
                    <label htmlFor="username" className="form-label">Tên người dùng</label>
                    <input
                        type="text"
                        className="form-control"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">Mật khẩu</label>
                    <input
                        type="password"
                        className="form-control"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>

                {errorMessage && (
                    <div className="alert alert-danger" role="alert">
                        {errorMessage}
                    </div>
                )}

                <button type="submit" className="btn btn-primary w-100">Đăng Nhập</button>
            </form>
        </div>
    );
};

export default Login;
