import axios from 'axios';

const token = localStorage.getItem('jwtToken');
console.log("it's token", token);

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Authorization': `Bearer ${token}`
  }
});

export default axiosInstance;
