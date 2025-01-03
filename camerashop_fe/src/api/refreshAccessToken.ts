import {BASE_URL} from "../layouts/utils/Constant";
import axios from "axios";

interface RefreshTokenResponse {
    status: string;
    message: string;
    data: {
        access_token: string;
    };
}

export async function refreshAccessToken(): Promise<string> {
    try {
        const refresh_token = localStorage.getItem("refresh_token");
        if (!refresh_token) {
            throw new Error("Không tìm thấy refresh token. Vui lòng đăng nhập lại.");
        }

        const response = await axios.post<RefreshTokenResponse>(`${BASE_URL}/api/auth/refresh-token`, {
            refreshToken: refresh_token,
        });

        const { access_token } = response.data.data;

        // Cập nhật token mới vào localStorage
        localStorage.setItem("access_token", access_token);

        return access_token;
    } catch (error: any) {
        if (error.response) {
            throw new Error(error.response.data.message || "Làm mới token thất bại.");
        }
        throw new Error("Không thể kết nối đến máy chủ.");
    }
}
