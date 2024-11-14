import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8080/api', // Thay đổi URL tùy theo cấu hình của bạn
    headers: {
        'Content-Type': 'application/json'
    }
});

export default instance;
