import React, { useState, useEffect } from 'react';
import { createExpense, getAllExpenses, deleteExpense, updateExpense } from '../services/expenseService';
import { getAllCategories } from '../services/categoryService';
import { toast } from 'react-toastify';
import Modal from '../components/Modal';

/**
 * Página para gerenciar as Despesas (Expenses).
 * Permite criar, listar, editar e deletar despesas.
 */
function ExpensesPage() {
  // --- STATES ---
  // Guarda a lista de todas as categorias para os formulários.
  const [categories, setCategories] = useState([]);
  // Guarda a lista de despesas que vem da API.
  const [expenses, setExpenses] = useState([]);
  // Controla a exibição de mensagens de "carregando".
  const [isLoading, setIsLoading] = useState(false);
  
  // States para o formulário de CRIAÇÃO de despesa.
  const [description, setDescription] = useState('');
  const [amount, setAmount] = useState('');
  const [date, setDate] = useState(new Date().toISOString().split('T')[0]);
  const [selectedCategory, setSelectedCategory] = useState('');

  // States para o MODAL DE EDIÇÃO.
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [editingExpense, setEditingExpense] = useState(null);
  
  // --- LÓGICA DE DADOS ---
  // Busca os dados iniciais (categorias e despesas) quando a página carrega.
  const fetchInitialData = async () => {
    setIsLoading(true);
    try {
      // Faz as duas chamadas à API em paralelo para ser mais rápido.
      const [catResponse, expResponse] = await Promise.all([
        getAllCategories(),
        getAllExpenses()
      ]);
      
      const categoriesData = catResponse.data;
      setCategories(categoriesData);

      // Define a primeira categoria como padrão no formulário de criação.
      if (categoriesData.length > 0 && !selectedCategory) {
        setSelectedCategory(categoriesData[0].id);
      }
      
      // Combina os dados de despesas com os nomes das categorias para exibir na tabela.
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
  
  // Executa a busca de dados assim que a página é carregada pela primeira vez.
  useEffect(() => {
    fetchInitialData();
  }, []);

  // --- HANDLERS (Ações do Usuário) ---
  // Deleta uma despesa após confirmação.
  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja deletar esta despesa?')) {
      try {
        await deleteExpense(id);
        toast.success('Despesa deletada com sucesso!');
        fetchInitialData(); // Recarrega os dados da tela
      } catch (error) {
        toast.error('Erro ao deletar despesa.');
      }
    }
  };

  // Lida com o envio do formulário de criação.
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
      // Limpa o formulário e recarrega os dados da tela.
      setDescription('');
      setAmount('');
      fetchInitialData();
    } catch (error) {
      console.error('Erro ao registrar despesa:', error);
      toast.error('Falha ao registrar despesa. Verifique os dados.');
    }
  };

  // Abre o modal de edição com os dados da despesa clicada.
  const handleOpenEditModal = (expense) => {
    setEditingExpense({
      ...expense,
      date: new Date(expense.date).toISOString().split('T')[0] // Formata a data para o input
    });
    setIsEditModalOpen(true);
  };

  // Fecha o modal de edição e limpa os dados.
  const handleCloseEditModal = () => {
    setIsEditModalOpen(false);
    setEditingExpense(null);
  };

  // Atualiza o estado do formulário de edição conforme o usuário digita.
  const handleUpdateChange = (e) => {
    const { name, value } = e.target;
    setEditingExpense(prev => ({ ...prev, [name]: value }));
  };

  // Lida com o envio do formulário de edição.
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
      await updateExpense(editingExpense.id, expenseData);
      toast.success('Despesa atualizada com sucesso!');
      handleCloseEditModal();
      fetchInitialData(); // Recarrega os dados da tela
    } catch (error) {
      toast.error('Não foi possível atualizar a despesa.');
      console.error("Erro ao atualizar despesa:", error);
    }
  };

  return (
    <div>
      {/* Formulário para registrar uma nova despesa */}
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

      {/* Tabela com a lista de despesas recentes */}
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
                  {/* Corrige o fuso horário para exibir a data corretamente */}
                  <td>{new Date(exp.date + 'T03:00:00Z').toLocaleDateString('pt-BR')}</td>
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

      {/* Modal de edição, que só aparece quando isEditModalOpen é true */}
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
