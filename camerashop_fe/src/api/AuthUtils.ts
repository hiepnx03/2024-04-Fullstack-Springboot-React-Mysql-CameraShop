export function getAuthToken(): string {
    const token = localStorage.getItem('access_token'); // Lấy token từ localStorage
    if (!token) {
        throw new Error('Chưa có token, vui lòng đăng nhập lại.'); // Nếu không có token, ném lỗi
    }
    return token; // Trả về token
}
