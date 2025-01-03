import axios from 'axios';

const BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080';

interface LoginRequest {
    username: string;
    password: string;
}

interface LoginResponse {
    status: string;
    message: string;
    data: {
        refresh_token: string;
        access_token: string;
    };
}

// Hàm gửi yêu cầu đăng nhập
export async function loginUser(request: LoginRequest): Promise<LoginResponse> {
    try {
        const response = await axios.post<LoginResponse>(`${BASE_URL}/api/auth/login`, request);
        const { access_token, refresh_token } = response.data.data;

        // Lưu token vào localStorage
        localStorage.setItem("access_token", access_token);
        localStorage.setItem("refresh_token", refresh_token);

        return response.data;
    } catch (error: any) {
        if (error.response) {
            const statusCode = error.response.status;
            if (statusCode === 400) {
                throw new Error("Tài khoản hoặc mật khẩu không đúng.");
            } else if (statusCode === 500) {
                throw new Error("Lỗi hệ thống. Vui lòng thử lại sau.");
            }
            throw new Error(error.response.data.message || "Đăng nhập thất bại");
        }
        throw new Error("Không thể kết nối đến máy chủ. Vui lòng kiểm tra kết nối mạng.");
    }
}

