import React, { useState, useEffect } from 'react';
import { getBudgetsByPeriod, createOrUpdateBudget, deleteBudget, getAvailableBudgetPeriods } from '../services/budgetService';
import { getAllCategories } from '../services/categoryService';
import { toast } from 'react-toastify';

function BudgetsPage() {
  // --- STATES ---
  const [budgets, setBudgets] = useState([]);
  const [categories, setCategories] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  // NOVO: State exclusivo para o FILTRO da lista
  const [filterMonth, setFilterMonth] = useState('');
  const [availablePeriods, setAvailablePeriods] = useState([]);

  // NOVO: State unificado para o FORMULÁRIO de criação/edição
  const [formData, setFormData] = useState({
    categoryId: '',
    amount: '',
    period: new Date().toISOString().slice(0, 7), // Mês atual por padrão
  });
  
  // NOVO: State para controlar o modo de edição
  const [isEditing, setIsEditing] = useState(false);

  // --- LÓGICA DE DADOS ---
  // Busca inicial de categorias e períodos disponíveis para o filtro
  useEffect(() => {
    const fetchInitialData = async () => {
      try {
        const [catResponse, periodsResponse] = await Promise.all([
          getAllCategories(),
          getAvailableBudgetPeriods()
        ]);
        
        const categoriesData = catResponse.data;
        setCategories(categoriesData);
        if (categoriesData.length > 0) {
          setFormData(prev => ({ ...prev, categoryId: categoriesData[0].id }));
        }

        const periods = periodsResponse.data;
        setAvailablePeriods(periods);
        if (periods.length > 0) {
          setFilterMonth(periods[0]); // Define o filtro para o mês mais recente
        }
      } catch (error) {
        toast.error("Erro ao carregar dados iniciais.");
      }
    };
    fetchInitialData();
  }, []);

  // Busca os orçamentos sempre que o FILTRO de mês mudar
  useEffect(() => {
    if (!filterMonth) return;

    const fetchBudgets = async () => {
      setIsLoading(true);
      try {
        const response = await getBudgetsByPeriod(filterMonth);
        setBudgets(response.data);
      } catch (error) {
        toast.error('Não foi possível carregar os orçamentos.');
      } finally {
        setIsLoading(false);
      }
    };
    
    fetchBudgets();
  }, [filterMonth]);
  
  // --- HANDLERS (Ações do Usuário) ---
  const handleFormChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };
  
  const resetForm = () => {
    setFormData({
      categoryId: categories.length > 0 ? categories[0].id : '',
      amount: '',
      period: new Date().toISOString().slice(0, 7),
    });
    setIsEditing(false);
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.categoryId || !formData.amount || parseFloat(formData.amount) <= 0) {
      toast.warn('Selecione uma categoria e insira um valor positivo.');
      return;
    }
    
    const budgetData = {
      categoryId: formData.categoryId,
      amount: parseFloat(formData.amount),
      period: formData.period,
    };
    
    try {
      await createOrUpdateBudget(budgetData);
      toast.success(`Orçamento ${isEditing ? 'atualizado' : 'salvo'} com sucesso!`);
      resetForm();
      // Atualiza a lista de orçamentos e também a lista de períodos disponíveis
      const [budgetsRes, periodsRes] = await Promise.all([getBudgetsByPeriod(filterMonth), getAvailableBudgetPeriods()]);
      setBudgets(budgetsRes.data);
      setAvailablePeriods(periodsRes.data);
    } catch (error) {
      toast.error('Falha ao salvar orçamento.');
    }
  };

  const handleEditClick = (budget) => {
    setIsEditing(true);
    setFormData({
        categoryId: budget.categoryId,
        amount: budget.amount.toString(),
        period: budget.period,
    });
    document.getElementById('amount-input')?.focus();
  };

  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja deletar este orçamento?')) {
      try {
        await deleteBudget(id);
        toast.success('Orçamento deletado com sucesso!');
        // Atualiza tudo após deletar
        const periodsRes = await getAvailableBudgetPeriods();
        setAvailablePeriods(periodsRes.data);
        // Se o mês deletado era o que estava sendo filtrado e ele não existe mais,
        // muda o filtro para o mais recente, se houver.
        if (!periodsRes.data.includes(filterMonth) && periodsRes.data.length > 0) {
            setFilterMonth(periodsRes.data[0]);
        } else {
            fetchBudgets();
        }
      } catch (error) {
        toast.error('Não foi possível deletar o orçamento.');
      }
    }
  };

  return (
    <div>
      <div className="form-container">
        <h2>{isEditing ? 'Editar Orçamento' : 'Definir Novo Orçamento'}</h2>
        <form onSubmit={handleSubmit}>
          {/* O formulário agora tem seu próprio seletor de mês */}
          <input name="period" type="month" value={formData.period} onChange={handleFormChange} />
          <select name="categoryId" value={formData.categoryId} onChange={handleFormChange} disabled={isEditing}>
            {categories.map(cat => <option key={cat.id} value={cat.id}>{cat.name}</option>)}
          </select>
          <input id="amount-input" name="amount" type="number" step="0.01" placeholder="Valor do Orçamento" value={formData.amount} onChange={handleFormChange} />
          <div style={{display: 'flex', gap: '10px'}}>
            <button type="submit">{isEditing ? 'Atualizar' : 'Salvar'}</button>
            {isEditing && <button type="button" onClick={resetForm} style={{backgroundColor: '#6c757d'}}>Cancelar</button>}
          </div>
        </form>
      </div>

      <div className="list-container">
        {/* NOVO: Seção de filtro separada */}
        <div style={{display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '1rem'}}>
          <label htmlFor="filter-month-select"><strong>Filtrar por Mês:</strong></label>
          <select
            id="filter-month-select"
            value={filterMonth}
            onChange={(e) => setFilterMonth(e.target.value)}
          >
            {availablePeriods.length === 0 && <option>Nenhum orçamento cadastrado</option>}
            {availablePeriods.map(period => (
              <option key={period} value={period}>{period}</option>
            ))}
          </select>
        </div>
        
        <h2>Orçamentos para {filterMonth}</h2>
        {isLoading ? <p>Carregando...</p> : (
          <table>
            <thead>
              <tr>
                <th>Categoria</th>
                <th>Valor Orçado</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {budgets.length > 0 ? budgets.map(b => (
                <tr key={b.id}>
                  <td>{b.categoryName}</td>
                  <td>R$ {b.amount.toFixed(2).replace('.', ',')}</td>
                  <td style={{display: 'flex', gap: '10px'}}>
                    <button onClick={() => handleEditClick(b)}>Editar</button>
                    <button onClick={() => handleDelete(b.id)} className="delete-btn">Deletar</button>
                  </td>
                </tr>
              )) : <tr><td colSpan="3">Nenhum orçamento definido para este mês.</td></tr>}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

export default BudgetsPage;