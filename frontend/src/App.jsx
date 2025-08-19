import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  NavLink,
} from "react-router-dom";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./App.css";
import CategoriesPage from "./pages/CategoriesPage";
import ExpensesPage from "./pages/ExpensesPage";
import HomePage from "./pages/HomePage";
import BudgetsPage from "./pages/BudgetsPage";

/**
 * Componente principal da aplicação.
 * Responsável pelo layout, navegação (rotas) e configuração de notificações.
 */
function App() {
  return (
    <Router>
      <div className="app-layout">
        {/* Container para exibir as notificações "toast" */}
        <ToastContainer
          position="top-right"
          autoClose={3000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
          theme="light"
        />

        {/* Barra de navegação com os links para as páginas */}
        <nav className="navbar">
          <ul>
            <li><NavLink to="/">Início</NavLink></li>
            <li><NavLink to="/categories">Categorias</NavLink></li>
            <li><NavLink to="/expenses">Despesas</NavLink></li>
            <li><NavLink to="/budgets">Orçamentos</NavLink></li>
          </ul>
        </nav>

        {/* Área principal onde o conteúdo de cada página será renderizado */}
        <main className="page-content">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/categories" element={<CategoriesPage />} />
            <Route path="/expenses" element={<ExpensesPage />} />
            <Route path="/budgets" element={<BudgetsPage />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;