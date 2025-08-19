import api from './api';

export const getAllExpenses = () => api.get('/expenses');

export const createExpense = (expenseData) => api.post('/expenses', expenseData);

export const deleteExpense = (id) => api.delete(`/expenses/${id}`);

export const updateExpense = (id, expenseData) => {
  return api.put(`/expenses/${id}`, expenseData);
};