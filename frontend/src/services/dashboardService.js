import api from './api';

export const getDashboardSummary = (period) => {
  return api.get(`/dashboard/summary?period=${period}`);
};