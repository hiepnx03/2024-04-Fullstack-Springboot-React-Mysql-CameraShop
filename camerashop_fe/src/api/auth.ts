// auth.ts

// Lưu token vào localStorage
export const login = (token: string) => {
    localStorage.setItem("token", token);  // Lưu token vào localStorage
};

// Kiểm tra xem người dùng đã đăng nhập chưa
export const isLoggedIn = (): boolean => {
    const token = localStorage.getItem("token");
    return token !== null;  // Nếu có token, trả về true
};

// Lấy thông tin người dùng từ token
export const getUserInfo = () => {
    const token = localStorage.getItem("token");
    if (!token) return null;

    const decodedToken = JSON.parse(atob(token.split('.')[1])); // Giải mã token
    return decodedToken;
};


// Xóa token khỏi localStorage
export const logout = () => {
    localStorage.removeItem("token");  // Xóa token khỏi localStorage
};