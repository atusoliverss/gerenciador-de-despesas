import api from './api';

/**
 * Busca todas as despesas.
 */
export const getAllExpenses = () => api.get('/expenses');

/**
 * Cria uma nova despesa.
 */
export const createExpense = (expenseData) => api.post('/expenses', expenseData);

/**
 * Deleta uma despesa pelo seu ID.
 */
export const deleteExpense = (id) => api.delete(`/expenses/${id}`);

/**
 * Atualiza uma despesa existente.
 */
export const updateExpense = (id, expenseData) => {
  return api.put(`/expenses/${id}`, expenseData);
};