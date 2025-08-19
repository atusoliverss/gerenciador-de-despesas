import React from 'react';
import './Modal.css';

/**
 * Componente reutilizável para exibir uma janela pop-up (Modal).
 * Usado para formulários de edição.
 * @param {boolean} isOpen - Controla se o modal está visível ou não.
 * @param {function} onClose - Função para fechar o modal.
 * @param {string} title - O título exibido no cabeçalho do modal.
 * @param {React.ReactNode} children - O conteúdo a ser exibido dentro do modal (ex: um formulário).
 */
function Modal({ isOpen, onClose, title, children }) {
  // Se o modal não estiver aberto, não renderiza nada.
  if (!isOpen) {
    return null;
  }

  return (
    // Fundo escuro que cobre a tela. Clicar nele fecha o modal.
    <div className="modal-overlay" onClick={onClose}>
      {/* Conteúdo do modal. Clicar nele não fecha (stopPropagation). */}
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h2>{title}</h2>
          <button onClick={onClose} className="modal-close-btn">&times;</button>
        </div>
        <div className="modal-body">
          {/* Renderiza o conteúdo que foi passado para o componente (ex: o formulário) */}
          {children}
        </div>
      </div>
    </div>
  );
}

export default Modal;