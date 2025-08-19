import React, { useState, useEffect } from 'react';
import { getAllCategories, createCategory, deleteCategory, updateCategory } from '../services/categoryService';
import { toast } from 'react-toastify';
import Modal from '../components/Modal';
import '../components/Modal.css';

/**
 * Página para gerenciar as Categorias.
 * Permite criar, listar, editar e deletar categorias.
 */
function CategoriesPage() {
  // --- STATES ---
  // Guarda a lista de categorias que vem da API.
  const [categories, setCategories] = useState([]);
  // Guarda o texto do input para criar uma nova categoria.
  const [newCategoryName, setNewCategoryName] = useState('');
  // Controla a exibição de mensagens de "carregando".
  const [isLoading, setIsLoading] = useState(false);
  // Controla se o modal de edição está aberto ou fechado.
  const [isModalOpen, setIsModalOpen] = useState(false);
  // Guarda os dados da categoria que está sendo editada.
  const [editingCategory, setEditingCategory] = useState(null);
  // Guarda o novo nome da categoria no formulário de edição.
  const [updatedName, setUpdatedName] = useState('');

  // --- LÓGICA DE DADOS ---
  // Função para buscar a lista de categorias da API.
  const fetchCategories = async () => {
    setIsLoading(true);
    try {
      const response = await getAllCategories();
      setCategories(response.data);
    } catch (error) {
      console.error('Erro ao buscar categorias:', error);
      toast.error('Não foi possível carregar as categorias.');
    } finally {
      setIsLoading(false);
    }
  };

  // Executa a busca de categorias assim que a página é carregada.
  useEffect(() => {
    fetchCategories();
  }, []);

  // --- HANDLERS (Ações do Usuário) ---
  // Deleta uma categoria após confirmação.
  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja deletar esta categoria?')) {
      try {
        await deleteCategory(id);
        toast.success('Categoria deletada com sucesso!');
        fetchCategories(); // Atualiza a lista
      } catch (error) {
        console.error('Erro ao deletar categoria:', error);
        toast.error('Erro ao deletar. Verifique se não há despesas associadas.');
      }
    }
  };
  
  // Lida com o envio do formulário de criação.
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!newCategoryName.trim()) {
      toast.warn('O nome da categoria não pode ser vazio.');
      return;
    }
    try {
      await createCategory({ name: newCategoryName });
      toast.success('Categoria criada com sucesso!');
      setNewCategoryName('');
      fetchCategories(); 
    } catch (error) {
      console.error('Erro ao criar categoria:', error);
      toast.error('Erro ao criar categoria.');
    }
  };

  // Abre o modal de edição com os dados da categoria clicada.
  const handleOpenEditModal = (category) => {
    setEditingCategory(category);
    setUpdatedName(category.name);
    setIsModalOpen(true);
  };

  // Fecha o modal de edição e limpa os dados.
  const handleCloseModal = () => {
    setIsModalOpen(false);
    setEditingCategory(null);
    setUpdatedName('');
  };

  // Lida com o envio do formulário de edição.
  const handleUpdateSubmit = async (e) => {
    e.preventDefault();
    if (!editingCategory || !updatedName.trim()) return;

    try {
      await updateCategory(editingCategory.id, { name: updatedName });
      toast.success('Categoria atualizada com sucesso!');
      handleCloseModal();
      fetchCategories();
    } catch (error) {
      console.error('Erro ao atualizar categoria:', error);
      toast.error('Não foi possível atualizar a categoria.');
    }
  };

  return (
    <div>
      {/* Formulário para criar uma nova categoria */}
      <div className="form-container">
        <h2>Criar Nova Categoria</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            value={newCategoryName}
            onChange={(e) => setNewCategoryName(e.target.value)}
            placeholder="Nome da Categoria"
            disabled={isLoading}
          />
          <button type="submit" disabled={isLoading}>
            {isLoading ? 'Criando...' : 'Criar'}
          </button>
        </form>
      </div>

      {/* Lista de categorias existentes */}
      <div className="list-container">
        <h2>Categorias Existentes</h2>
        {isLoading && <p>Carregando...</p>}
        <ul>
          {!isLoading && categories.map((cat) => (
            <li key={cat.id}>
              <span>{cat.name}</span>
              <div>
                <button onClick={() => handleOpenEditModal(cat)} style={{ marginRight: '10px' }}>Editar</button>
                <button onClick={() => handleDelete(cat.id)} className="delete-btn">Deletar</button>
              </div>
            </li>
          ))}
        </ul>
      </div>

      {/* Modal de edição, que só aparece quando isModalOpen é true */}
      <Modal isOpen={isModalOpen} onClose={handleCloseModal} title="Editar Categoria">
        <form onSubmit={handleUpdateSubmit}>
          <input
            type="text"
            value={updatedName}
            onChange={(e) => setUpdatedName(e.target.value)}
          />
          <button type="submit">Salvar Alterações</button>
        </form>
      </Modal>
    </div>
  );
}

export default CategoriesPage;
