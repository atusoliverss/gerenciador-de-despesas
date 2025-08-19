import axios from 'axios';

/**
 * Configuração central do Axios.
 * Define a URL base para todas as chamadas à API do backend.
 */
const api = axios.create({
  baseURL: 'http://localhost:8080', 
});

export default api;