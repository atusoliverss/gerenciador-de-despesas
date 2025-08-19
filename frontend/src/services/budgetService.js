import api from './api';

/**
 * Busca os orçamentos de um mês específico.
 */
export const getBudgetsByPeriod = (period) => {
  return api.get(`/budgets?period=${period}`);
};

/**
 * Cria um novo orçamento ou atualiza um existente.
 */
export const createOrUpdateBudget = (budgetData) => {
  return api.post('/budgets', budgetData);
};

/**
 * Deleta um orçamento pelo seu ID.
 */
export const deleteBudget = (id) => {
  return api.delete(`/budgets/${id}`);
};

/**
 * Busca a lista de meses que têm orçamentos cadastrados.
 */
export const getAvailableBudgetPeriods = () => {
  return api.get('/budgets/periods');
};