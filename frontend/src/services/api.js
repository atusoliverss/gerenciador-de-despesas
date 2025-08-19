import axios from 'axios';

const api = axios.create({
  // URL base do seu backend Spring Boot
  baseURL: 'http://localhost:8080', 
});

export default api;