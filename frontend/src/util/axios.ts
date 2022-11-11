import axios, { AxiosRequestConfig } from "axios";

// create custom axios instance which will be used for all requests to the API which includes JWT Token
const axiosInstance = axios.create({
    baseURL: "http://localhost:8080/api",
    headers: {
        "Content-Type": "application/json",
    }
});

// add an axios request interceptor to add the jwt token
axiosInstance.interceptors.request.use((config: AxiosRequestConfig) => {
    const token = localStorage.getItem("token");
    const parsedJson = JSON.parse(token || "{}");
    if (parsedJson && config?.headers) {
        config.headers.Authorization = `Bearer ${parsedJson.token}`;
    }
    return config;
}, (error) => {
    Promise.reject(error);
});

export default axiosInstance;
