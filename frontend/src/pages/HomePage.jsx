import React, { useState, useEffect } from 'react';
import { getAvailableBudgetPeriods } from '../services/budgetService';
import { getDashboardSummary } from '../services/dashboardService';
import { toast } from 'react-toastify';
import { Doughnut } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';

// Registra os componentes do Chart.js necessários para o gráfico de rosca.
ChartJS.register(ArcElement, Tooltip, Legend);

/**
 * Página principal que funciona como um Dashboard financeiro.
 * Exibe um resumo dos gastos, orçamentos e um gráfico.
 */
function HomePage() {
  // --- STATES ---
  // Guarda os dados consolidados que vêm da API para o dashboard.
  const [summaryData, setSummaryData] = useState(null);
  // Controla a exibição de mensagens de "carregando".
  const [isLoading, setIsLoading] = useState(true);
  // Guarda o mês selecionado no filtro.
  const [filterMonth, setFilterMonth] = useState('');
  // Guarda a lista de meses que têm orçamentos para popular o filtro.
  const [availablePeriods, setAvailablePeriods] = useState([]);

  // --- LÓGICA DE DADOS ---
  // Busca a lista de períodos disponíveis para popular o filtro.
  useEffect(() => {
    const fetchPeriods = async () => {
      try {
        const periodsResponse = await getAvailableBudgetPeriods();
        const periods = periodsResponse.data;
        setAvailablePeriods(periods);

        // Lógica para definir o filtro inicial de forma inteligente.
        const currentMonth = new Date().toISOString().slice(0, 7);
        if (periods.includes(currentMonth)) {
          // Se o mês atual tem dados, ele é o padrão.
          setFilterMonth(currentMonth);
        } else if (periods.length > 0) {
          // Senão, o padrão é o mês mais recente com dados.
          setFilterMonth(periods[0]);
        } else {
          // Senão, o padrão é o mês atual (mesmo que vazio).
          setFilterMonth(currentMonth);
        }
      } catch (error) {
        toast.error("Erro ao carregar períodos disponíveis.");
        setFilterMonth(new Date().toISOString().slice(0, 7));
      }
    };
    fetchPeriods();
  }, []);

  // Busca os dados de resumo do dashboard sempre que o mês do filtro é alterado.
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

  // --- FUNÇÕES AUXILIARES ---
  // Formata um número para o padrão de moeda brasileiro (R$).
  const formatCurrency = (value) => {
    return (value || 0).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  };
  
  // Prepara os dados para o componente de gráfico.
  const chartData = {
    labels: summaryData?.spendingByCategory.filter(c => c.amountSpent > 0).map(c => c.categoryName) || [],
    datasets: [
      {
        label: 'Gastos por Categoria',
        data: summaryData?.spendingByCategory.filter(c => c.amountSpent > 0).map(c => c.amountSpent) || [],
        backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40'],
        borderWidth: 1,
      },
    ],
  };

  // Define a cor do saldo (verde se positivo, vermelho se negativo).
  const differenceColor = summaryData?.difference >= 0 ? 'green' : 'red';

  return (
    <div>
      {/* Cabeçalho com título e filtro de mês */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1rem' }}>
        <h1>Dashboard Financeiro</h1>
        <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
          <label htmlFor="filter-month-select"><strong>Mês:</strong></label>
          <select id="filter-month-select" value={filterMonth} onChange={(e) => setFilterMonth(e.target.value)}>
            {!availablePeriods.includes(filterMonth) && <option key={filterMonth} value={filterMonth}>{filterMonth}</option>}
            {availablePeriods.map(period => (<option key={period} value={period}>{period}</option>))}
          </select>
        </div>
      </div>

      {/* Exibe mensagem de carregamento ou os dados do dashboard */}
      {isLoading ? <p>Carregando resumo...</p> : !summaryData ? <p>Não foi possível carregar os dados para {filterMonth}.</p> : (
        <>
          {/* Cards com os totais */}
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', gap: '1.5rem', margin: '2rem 0' }}>
            <div className="form-container"><h3>Total Orçado</h3><p style={{fontSize: '1.5rem'}}>{formatCurrency(summaryData.totalBudget)}</p></div>
            <div className="form-container"><h3>Total Gasto</h3><p style={{fontSize: '1.5rem', color: '#dc3545'}}>{formatCurrency(summaryData.totalExpenses)}</p></div>
            <div className="form-container"><h3>Saldo</h3><p style={{fontSize: '1.5rem', color: differenceColor}}>{formatCurrency(summaryData.difference)}</p></div>
          </div>
          
          {/* Seção com o gráfico e a tabela de detalhes */}
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
