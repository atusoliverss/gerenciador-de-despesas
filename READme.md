# 💹 Gerenciador de Despesas Pessoais

Este é um projeto Full Stack de um Gerenciador de Despesas Pessoais, desenvolvido para aplicar conceitos avançados de arquitetura de software, com um backend robusto em Java + Spring Boot e um frontend reativo em React.

## ✨ Funcionalidades

* **Gerenciamento de Categorias:** Crie, liste, edite e delete categorias de gastos (ex: Alimentação, Transporte).
* **Registro de Despesas:** Crie, liste, edite e delete despesas, associando cada uma a uma categoria.
* **Planejamento de Orçamentos (Budgets):** Defina orçamentos mensais por categoria, com funcionalidades de criação, edição, listagem e deleção.
* **Dashboard Interativo:** Visualize um resumo financeiro completo para qualquer mês com dados, incluindo:
    * Cards com total orçado, total gasto e saldo.
    * Gráfico de rosca (Doughnut) com a distribuição de gastos por categoria.
    * Tabela detalhada comparando o valor gasto vs. orçado para cada categoria.
* **Filtros Inteligentes:** A interface permite filtrar visualizações por meses que efetivamente contenham dados, melhorando a experiência do usuário.
* **Feedback ao Usuário:** Notificações "toast" para ações de sucesso ou erro, proporcionando uma experiência de usuário moderna.

---

## 🏛️ Arquitetura Utilizada: Clean Architecture

O backend foi estruturado seguindo os princípios da **Clean Architecture**, de Robert C. Martin. O objetivo é a separação de responsabilidades (Separation of Concerns), resultando em um sistema de baixo acoplamento, alta testabilidade e fácil manutenção.

Isso foi alcançado através da divisão do backend em 3 módulos Maven distintos:

#### 📁 `core` - O Cérebro

* É a camada mais interna e pura do sistema.
* **Conteúdo:** Contém as entidades de negócio (ex: `Expense`, `Category`), as regras de negócio e os casos de uso (`UseCases`). Também define os "contratos" (interfaces) dos repositórios.
* **Regra Principal:** Não depende de nenhum framework externo (Spring, JPA, etc.). É puro Java, garantindo que a lógica de negócio possa ser reutilizada ou migrada sem esforço.

#### 📁 `infrastructure` - Os Trabalhadores

* É a camada externa, onde ficam os detalhes de implementação.
* **Conteúdo:** Implementa as interfaces do `core`. Contém os `Controllers` da API REST (Spring Web), as entidades `@Entity` do JPA, as implementações dos repositórios que de fato conversam com o banco de dados (PostgreSQL), e os DTOs.
* **Regra Principal:** Depende do `core`, mas o `core` não depende dele (Inversão de Dependência).

#### 📁 `application` - A Cola

* É o módulo que inicia a aplicação e conecta todas as peças.
* **Conteúdo:** A classe principal `@SpringBootApplication`, as configurações de Beans (onde dizemos ao Spring qual implementação de repositório usar para qual interface do `core`) e a configuração do CORS.
* **Regra Principal:** É o ponto de entrada que conhece e "monta" o sistema.

Este design garante que mudanças em detalhes de implementação (como trocar de PostgreSQL para MySQL, ou de API REST para gRPC) tenham impacto mínimo ou nulo na lógica de negócio, que está protegida no `core`.

---

## 💻 Tecnologias Utilizadas

**Backend:**

* Java 21
* Spring Boot 3.3+
* Maven
* Spring Data JPA & Hibernate
* PostgreSQL
* Lombok

**Frontend:**

* React 18+
* Vite
* Axios (para chamadas à API)
* React Router DOM (para navegação)
* React Toastify (para notificações)
* Chart.js (para o gráfico do dashboard)

---

## 🚀 Como Executar o Projeto

**Pré-requisitos:**

* JDK 21 (ou superior)
* Maven 3.8+
* Node.js 18+ (com npm)
* PostgreSQL instalado e rodando.

### **1. Configuração do Backend**

1.  **Crie o Banco de Dados:**
    * Abra uma ferramenta de gerenciamento de PostgreSQL (como DBeaver ou pgAdmin).
    * Crie um novo banco de dados. Ex: `gerdp_db`.

2.  **Configure a Conexão:**
    * Abra o arquivo `backend/application/src/main/resources/application.properties`.
    * Altere as seguintes linhas com suas credenciais do PostgreSQL:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/gerdp_db
        spring.datasource.username=seu_usuario_postgres
        spring.datasource.password=sua_senha_postgres
        ```

3.  **Execute o Backend:**
    * Abra um terminal na pasta `backend/`.
    * Execute o comando:
        ```bash
        # No Linux/macOS
        ./mvnw spring-boot:run

        # No Windows (CMD)
        mvnw.cmd spring-boot:run
        ```
    * O servidor estará rodando em `http://localhost:8080`.

### **2. Configuração do Frontend**

1.  **Instale as Dependências:**
    * Abra um **novo terminal** na pasta `frontend/`.
    * Execute o comando:
        ```bash
        npm install
        ```
    * Este comando irá ler o `package.json` e baixar todas as bibliotecas necessárias para o React (`axios`, `react-router-dom`, `react-toastify`, `chart.js`).

2.  **Execute o Frontend:**
    * No mesmo terminal (na pasta `frontend`), execute:
        ```bash
        npm run dev
        ```
    * O servidor de desenvolvimento estará rodando no endereço indicado no terminal (geralmente `http://localhost:5173`).

3.  **Acesse a Aplicação:**
    * Abra seu navegador e acesse `http://localhost:5173`.

---
