import React, { useState, useEffect } from 'react';
// AQUI ESTÁ A CORREÇÃO: Adicionamos 'updateExpense' na linha abaixo
import { createExpense, getAllExpenses, deleteExpense, updateExpense } from '../services/expenseService';
import { getAllCategories } from '../services/categoryService';
import { toast } from 'react-toastify';
import Modal from '../components/Modal';

function ExpensesPage() {
  const [categories, setCategories] = useState([]);
  const [expenses, setExpenses] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  
  const [description, setDescription] = useState('');
  const [amount, setAmount] = useState('');
  const [date, setDate] = useState(new Date().toISOString().split('T')[0]);
  const [selectedCategory, setSelectedCategory] = useState('');

  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [editingExpense, setEditingExpense] = useState(null);
  
  const fetchInitialData = async () => {
    setIsLoading(true);
    try {
      const [catResponse, expResponse] = await Promise.all([
        getAllCategories(),
        getAllExpenses()
      ]);
      
      const categoriesData = catResponse.data;
      setCategories(categoriesData);

      if (categoriesData.length > 0 && !selectedCategory) {
        setSelectedCategory(categoriesData[0].id);
      }
      
      const expensesWithCategoryNames = expResponse.data.map(exp => ({
        ...exp,
        categoryName: categoriesData.find(cat => cat.id === exp.categoryId)?.name || 'N/A'
      }));
      setExpenses(expensesWithCategoryNames);

    } catch (error) {
      console.error('Erro ao buscar dados iniciais:', error);
      toast.error('Não foi possível carregar os dados da página.');
    } finally {
      setIsLoading(false);
    }
  };
  
  useEffect(() => {
    fetchInitialData();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja deletar esta despesa?')) {
      try {
        await deleteExpense(id);
        toast.success('Despesa deletada com sucesso!');
        fetchInitialData();
      } catch (error) {
        toast.error('Erro ao deletar despesa.');
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!description || !amount || !date || !selectedCategory) {
      toast.warn('Por favor, preencha todos os campos.');
      return;
    }
    const expenseData = { description, amount: parseFloat(amount), date, categoryId: selectedCategory };
    try {
      await createExpense(expenseData);
      toast.success('Despesa registrada com sucesso!');
      setDescription('');
      setAmount('');
      fetchInitialData();
    } catch (error) {
      console.error('Erro ao registrar despesa:', error);
      toast.error('Falha ao registrar despesa. Verifique os dados.');
    }
  };

  const handleOpenEditModal = (expense) => {
    setEditingExpense({
      ...expense,
      date: new Date(expense.date).toISOString().split('T')[0]
    });
    setIsEditModalOpen(true);
  };

  const handleCloseEditModal = () => {
    setIsEditModalOpen(false);
    setEditingExpense(null);
  };

  const handleUpdateChange = (e) => {
    const { name, value } = e.target;
    setEditingExpense(prev => ({ ...prev, [name]: value }));
  };

  const handleUpdateSubmit = async (e) => {
    e.preventDefault();
    if (!editingExpense) return;
    const expenseData = {
      description: editingExpense.description,
      amount: parseFloat(editingExpense.amount),
      date: editingExpense.date,
      categoryId: editingExpense.categoryId,
    };
    try {
      // Agora a função 'updateExpense' existe porque foi importada
      await updateExpense(editingExpense.id, expenseData);
      toast.success('Despesa atualizada com sucesso!');
      handleCloseEditModal();
      fetchInitialData();
    } catch (error) {
      toast.error('Não foi possível atualizar a despesa.');
      console.error("Erro ao atualizar despesa:", error);
    }
  };

  return (
    <div>
      <div className="form-container">
        <h2>Registrar Nova Despesa</h2>
        <form onSubmit={handleSubmit}>
          <input type="text" placeholder="Descrição" value={description} onChange={(e) => setDescription(e.target.value)} />
          <input type="number" step="0.01" placeholder="Valor (ex: 50.75)" value={amount} onChange={(e) => setAmount(e.target.value)} />
          <input type="date" value={date} onChange={(e) => setDate(e.target.value)} />
          <select value={selectedCategory} onChange={(e) => setSelectedCategory(e.target.value)}>
            {categories.length === 0 ? <option>Carregando...</option> : <option value="" disabled>Selecione uma categoria</option>}
            {categories.map((cat) => (<option key={cat.id} value={cat.id}>{cat.name}</option>))}
          </select>
          <button type="submit">Registrar Despesa</button>
        </form>
      </div>

      <div className="list-container">
        <h2>Despesas Recentes</h2>
        {isLoading ? <p>Carregando despesas...</p> : 
          <table>
            <thead>
              <tr>
                <th>Descrição</th>
                <th>Valor</th>
                <th>Categoria</th>
                <th>Data</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {expenses.map((exp) => (
                <tr key={exp.id}>
                  <td>{exp.description}</td>
                  <td>R$ {exp.amount.toFixed(2).replace('.', ',')}</td>
                  <td>{exp.categoryName}</td>
                  <td>{new Date(exp.date + 'T00:00:00-03:00').toLocaleDateString('pt-BR')}</td>
                  <td style={{display: 'flex', gap: '10px'}}>
                    <button onClick={() => handleOpenEditModal(exp)}>Editar</button>
                    <button onClick={() => handleDelete(exp.id)} className="delete-btn">Deletar</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        }
      </div>

      <Modal isOpen={isEditModalOpen} onClose={handleCloseEditModal} title="Editar Despesa">
        {editingExpense && (
          <form onSubmit={handleUpdateSubmit}>
            <input name="description" type="text" value={editingExpense.description} onChange={handleUpdateChange} />
            <input name="amount" type="number" step="0.01" value={editingExpense.amount} onChange={handleUpdateChange} />
            <input name="date" type="date" value={editingExpense.date} onChange={handleUpdateChange} />
            <select name="categoryId" value={editingExpense.categoryId} onChange={handleUpdateChange}>
              {categories.map((cat) => (<option key={cat.id} value={cat.id}>{cat.name}</option>))}
            </select>
            <button type="submit">Salvar Alterações</button>
          </form>
        )}
      </Modal>
    </div>
  );
}

export default ExpensesPage;