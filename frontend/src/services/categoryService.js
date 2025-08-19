import api from './api';

export const getAllCategories = () => {
  return api.get('/categories');
};

export const createCategory = (categoryData) => {
  return api.post('/categories', categoryData);
};

export const deleteCategory = (id) => {
  return api.delete(`/categories/${id}`);
};

export const updateCategory = (id, categoryData) => {
  return api.put(`/categories/${id}`, categoryData);
};