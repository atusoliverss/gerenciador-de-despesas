import React, { useState, useEffect } from 'react';
// AQUI ESTÁ A CORREÇÃO: Adicionamos 'updateCategory' na linha abaixo
import { getAllCategories, createCategory, deleteCategory, updateCategory } from '../services/categoryService';
import { toast } from 'react-toastify';
import Modal from '../components/Modal';
import '../components/Modal.css';

function CategoriesPage() {
  const [categories, setCategories] = useState([]);
  const [newCategoryName, setNewCategoryName] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingCategory, setEditingCategory] = useState(null);
  const [updatedName, setUpdatedName] = useState('');

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

  useEffect(() => {
    fetchCategories();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja deletar esta categoria?')) {
      try {
        await deleteCategory(id);
        toast.success('Categoria deletada com sucesso!');
        fetchCategories();
      } catch (error) {
        console.error('Erro ao deletar categoria:', error);
        toast.error('Erro ao deletar. Verifique se não há despesas associadas.');
      }
    }
  };
  
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

  const handleOpenEditModal = (category) => {
    setEditingCategory(category);
    setUpdatedName(category.name);
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setEditingCategory(null);
    setUpdatedName('');
  };

  const handleUpdateSubmit = async (e) => {
    e.preventDefault();
    if (!editingCategory || !updatedName.trim()) return;

    try {
      // Agora a função 'updateCategory' existe porque foi importada
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