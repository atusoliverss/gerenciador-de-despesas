import api from './api';

// ex: period = "2025-08"
export const getBudgetsByPeriod = (period) => {
  return api.get(`/budgets?period=${period}`);
};

export const createOrUpdateBudget = (budgetData) => {
  return api.post('/budgets', budgetData);
};

export const deleteBudget = (id) => {
  return api.delete(`/budgets/${id}`);
};

export const getAvailableBudgetPeriods = () => {
  return api.get('/budgets/periods');
};