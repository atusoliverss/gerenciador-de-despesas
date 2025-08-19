import React, { useState, useEffect } from 'react';
import { getAvailableBudgetPeriods } from '../services/budgetService';
import { getDashboardSummary } from '../services/dashboardService';
import { toast } from 'react-toastify';
import { Doughnut } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';

// Registra os componentes necessários para o gráfico de rosca
ChartJS.register(ArcElement, Tooltip, Legend);

function HomePage() {
  const [summaryData, setSummaryData] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [filterMonth, setFilterMonth] = useState('');
  const [availablePeriods, setAvailablePeriods] = useState([]);

  useEffect(() => {
    const fetchPeriods = async () => {
      try {
        const periodsResponse = await getAvailableBudgetPeriods();
        const periods = periodsResponse.data; // Ex: ["2025-08", "2025-07"]
        setAvailablePeriods(periods);

        // --- LÓGICA DE FILTRO INICIAL (ATUALIZADA) ---
        const currentMonth = new Date().toISOString().slice(0, 7); // Mês atual, ex: "2025-08"

        // Se a lista de períodos com dados INCLUI o mês atual...
        if (periods.includes(currentMonth)) {
          // ...então o filtro inicial é o mês atual.
          setFilterMonth(currentMonth);
        } 
        // Senão, se a lista de períodos não está vazia...
        else if (periods.length > 0) {
          // ...então o filtro inicial continua sendo o mais recente da lista (que já vem ordenado do backend).
          setFilterMonth(periods[0]);
        } 
        // Senão (a lista de períodos está vazia)...
        else {
          // ...então o filtro é o mês atual (a tela ficará vazia, o que está correto).
          setFilterMonth(currentMonth);
        }
        // --- FIM DA LÓGICA ATUALIZADA ---

      } catch (error) {
        toast.error("Erro ao carregar períodos disponíveis.");
        // Mesmo com erro, define o mês atual para evitar que o filtro fique vazio.
        setFilterMonth(new Date().toISOString().slice(0, 7));
      }
    };
    fetchPeriods();
  }, []);

  useEffect(() => {
    if (!filterMonth) return;

    const fetchSummary = async () => {
      setIsLoading(true);
      try {
        const summaryResponse = await getDashboardSummary(filterMonth);
        setSummaryData(summaryResponse.data);
      } catch (error) {
        toast.error(`Erro ao carregar o resumo de ${filterMonth}.`);
        setSummaryData(null);
      } finally {
        setIsLoading(false);
      }
    };
    fetchSummary();
  }, [filterMonth]);

  const formatCurrency = (value) => {
    return (value || 0).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  };
  
  const chartData = {
    labels: summaryData?.spendingByCategory.filter(c => c.amountSpent > 0).map(c => c.categoryName) || [],
    datasets: [
      {
        label: 'Gastos por Categoria',
        data: summaryData?.spendingByCategory.filter(c => c.amountSpent > 0).map(c => c.amountSpent) || [],
        backgroundColor: [
          '#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40',
          '#E7E9ED', '#8A2BE2', '#DEB887', '#5F9EA0'
        ],
        hoverBackgroundColor: [
          '#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40',
          '#E7E9ED', '#8A2BE2', '#DEB887', '#5F9EA0'
        ],
        borderWidth: 1,
      },
    ],
  };

  const differenceColor = summaryData?.difference >= 0 ? 'green' : 'red';

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1rem' }}>
        <h1>Dashboard Financeiro</h1>
        <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
          <label htmlFor="filter-month-select"><strong>Mês:</strong></label>
          <select id="filter-month-select" value={filterMonth} onChange={(e) => setFilterMonth(e.target.value)}>
            {/* Adiciona o mês atual como opção caso ele não esteja na lista */}
            {!availablePeriods.includes(filterMonth) && <option key={filterMonth} value={filterMonth}>{filterMonth}</option>}
            {availablePeriods.map(period => (<option key={period} value={period}>{period}</option>))}
          </select>
        </div>
      </div>

      {isLoading ? <p>Carregando resumo...</p> : !summaryData ? <p>Não foi possível carregar os dados para {filterMonth}.</p> : (
        <>
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', gap: '1.5rem', margin: '2rem 0' }}>
            <div className="form-container"><h3>Total Orçado</h3><p style={{fontSize: '1.5rem'}}>{formatCurrency(summaryData.totalBudget)}</p></div>
            <div className="form-container"><h3>Total Gasto</h3><p style={{fontSize: '1.5rem', color: '#dc3545'}}>{formatCurrency(summaryData.totalExpenses)}</p></div>
            <div className="form-container"><h3>Saldo</h3><p style={{fontSize: '1.5rem', color: differenceColor}}>{formatCurrency(summaryData.difference)}</p></div>
          </div>
          
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 2fr', gap: '2rem', alignItems: 'flex-start', flexWrap: 'wrap' }}>
            <div className="form-container" style={{height: '400px', display: 'flex', justifyContent: 'center', alignItems: 'center', minWidth: '250px'}}>
                {summaryData.totalExpenses > 0 ? <Doughnut data={chartData} options={{ maintainAspectRatio: false, responsive: true }}/> : <p>Nenhum gasto registrado neste mês.</p>}
            </div>

            <div className="list-container" style={{flexGrow: 1}}>
              <h2>Detalhes por Categoria</h2>
              <table>
                <thead><tr><th>Categoria</th><th>Orçado</th><th>Gasto</th><th>Saldo</th></tr></thead>
                <tbody>
                  {summaryData.spendingByCategory.map(cat => (
                    <tr key={cat.categoryId}>
                      <td>{cat.categoryName}</td>
                      <td>{formatCurrency(cat.amountBudgeted)}</td>
                      <td>{formatCurrency(cat.amountSpent)}</td>
                      <td style={{color: cat.difference >= 0 ? 'green' : 'red'}}>{formatCurrency(cat.difference)}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default HomePage;