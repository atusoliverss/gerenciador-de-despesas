import api from './api';

/**
 * Busca os dados de resumo do dashboard para um mês específico.
 */
export const getDashboardSummary = (period) => {
  return api.get(`/dashboard/summary?period=${period}`);
};