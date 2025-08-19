import api from './api';

/**
 * Busca todas as categorias.
 */
export const getAllCategories = () => {
  return api.get('/categories');
};

/**
 * Cria uma nova categoria.
 */
export const createCategory = (categoryData) => {
  return api.post('/categories', categoryData);
};

/**
 * Deleta uma categoria pelo seu ID.
 */
export const deleteCategory = (id) => {
  return api.delete(`/categories/${id}`);
};

/**
 * Atualiza uma categoria existente.
 */
export const updateCategory = (id, categoryData) => {
  return api.put(`/categories/${id}`, categoryData);
};