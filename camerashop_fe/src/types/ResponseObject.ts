// types/ResponseObject.ts

export interface ResponseObject {
    status: string;  // Trạng thái của phản hồi, ví dụ 'ok' hoặc 'error'
    message: string; // Thông điệp về kết quả
    data?: any;      // Dữ liệu có thể trả về, có thể là bất kỳ kiểu dữ liệu nào
}
