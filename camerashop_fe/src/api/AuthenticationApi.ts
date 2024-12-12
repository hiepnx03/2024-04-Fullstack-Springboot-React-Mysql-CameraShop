// src/api/AuthenticationApi.ts
const API_URL = 'http://localhost:8080/api/auth'; // Replace with your actual backend URL

interface LoginResponse {
    access_token: string;
}

interface LoginRequest {
    userName: string;
    password: string;
}
export const AuthenticationApi = {
    login: async (loginData: LoginRequest): Promise<LoginResponse> => {
        try {
            const response = await fetch(`${API_URL}/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(loginData),
            });

            if (!response.ok) {
                throw new Error('Login failed: ' + (await response.text()));
            }

            const data = await response.json();
            return data; // Returns the JWT token
        } catch (error: unknown) {
            // Type assertion to `Error`
            throw new Error((error as Error).message);
        }
    },

    logout: (): void => {
        localStorage.removeItem('access_token');
    },

    isAuthenticated: (): boolean => {
        const token = localStorage.getItem('access_token');
        return token ? true : false;
    },
};
