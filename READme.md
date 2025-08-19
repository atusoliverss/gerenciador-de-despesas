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

A estrutura do backend foi planejada com base na **Clean Architecture**, um padrão de design de software que visa criar sistemas robustos, fáceis de manter e testar. A filosofia central é a **separação de interesses (separation of concerns)**, garantindo que a lógica de negócio do projeto seja completamente independente de detalhes de implementação como frameworks, banco de dados ou interfaces de usuário.

O princípio fundamental que guia toda a estrutura é a **Regra da Dependência**: as dependências do código-fonte devem sempre apontar para dentro, em direção às camadas centrais. Isso significa que as camadas internas, que contêm a lógica de negócio, não devem saber nada sobre as camadas externas, que lidam com tecnologia e detalhes de infraestrutura.

Para aplicar este modelo de forma prática em um projeto Java/Spring, o backend foi organizado em três módulos Maven, cada um com responsabilidades bem definidas.

### As Camadas do Projeto

#### 📁 `core` - O Núcleo do Negócio

Esta é a camada mais interna e fundamental. Ela contém a essência da aplicação e é totalmente isolada de qualquer tecnologia externa.

* **Entidades (`Entities`):** Representam os objetos de negócio fundamentais (ex: `Expense`, `Category`). Elas encapsulam os dados e as regras de negócio mais críticas, que são universais para a aplicação, independentemente de como ela é apresentada ou onde os dados são armazenados.
* **Casos de Uso (`Use Cases`):** Contêm a lógica específica da aplicação. Cada caso de uso orquestra o fluxo de dados para executar uma tarefa de negócio, como `RegisterExpenseUseCase`. Eles definem as operações que o sistema pode realizar, validam entradas e garantem que as regras das entidades sejam respeitadas.
* **Interfaces de Repositório:** Funcionam como "contratos" ou "portas" que o `core` define para as operações de persistência. O `core` especifica *o que* precisa ser feito (ex: `save`, `findById`), mas delega a responsabilidade de *como* fazer para as camadas externas.

#### 📁 `infrastructure` - Conectores e Ferramentas Externas

Esta camada contém todos os detalhes técnicos e as ferramentas que interagem com o mundo exterior. Sua função é "adaptar" as tecnologias externas para servir às necessidades definidas pelo `core`.

* **Controllers:** São os adaptadores de entrada da API. Eles recebem requisições HTTP, traduzem os dados para um formato que os `Use Cases` entendem e, por fim, convertem a resposta do `core` de volta para um formato HTTP.
* **Implementações de Repositório:** São as classes que implementam os contratos de repositório definidos no `core`. É aqui que a tecnologia de persistência, como Spring Data JPA e PostgreSQL, é de fato utilizada. Esta camada sabe como executar as operações de banco de dados.
* **Mappers:** Classes utilitárias que convertem objetos entre as camadas. Por exemplo, transformam uma entidade de domínio (`Expense`) em uma entidade JPA (`ExpenseJpaEntity`), garantindo que a camada `core` nunca precise conhecer os detalhes do JPA.

#### 📁 `application` - O Ponto de Partida e Configuração

Este módulo serve como o ponto de entrada e o configurador central da aplicação. Sua principal função é realizar a **Composição da Raiz (Composition Root)**.

* **Classe Principal:** Contém o método `main` e a anotação `@SpringBootApplication`, responsável por iniciar o contêiner do Spring e carregar todo o sistema.
* **Configuração de Beans:** Utilizando classes com `@Configuration`, este módulo implementa o **Princípio da Inversão de Dependência (Dependency Inversion Principle)**. É aqui que as abstrações do `core` (as interfaces) são conectadas às suas implementações concretas do `infrastructure`. O Spring injeta as dependências, garantindo que o `core` permaneça desacoplado e não precise saber quais tecnologias específicas estão sendo usadas.

### Fluxo de Controle: Exemplo de Registro de Despesa

1.  Uma requisição `POST /expenses` é enviada pelo frontend.
2.  O **`ExpenseController`** (`infrastructure`) a recebe. Ele valida o DTO da requisição e prepara os dados.
3.  O `Controller` invoca o **`RegisterExpenseUseCase`** (`core`), passando os dados de forma simples e agnóstica à tecnologia.
4.  O `UseCase` executa a lógica de negócio (validações, regras).
5.  O `UseCase` chama o método `save` da interface **`ExpenseRepository`** (um contrato do `core`).
6.  O Spring, configurado pelo módulo `application`, fornece a implementação concreta: **`ExpenseRepositoryImpl`** (`infrastructure`).
7.  A `ExpenseRepositoryImpl` usa um **`Mapper`** para converter a entidade do `core` em uma entidade JPA.
8.  A implementação utiliza o **Spring Data JPA** para persistir a entidade no banco de dados PostgreSQL.
9.  O resultado retorna pelas camadas, e o `Controller` envia uma resposta HTTP `201 Created`.

### Benefícios Desta Abordagem

* **Alta Testabilidade:** A lógica de negócio no `core` pode ser testada de forma isolada, sem a complexidade de frameworks ou bancos de dados, resultando em testes unitários rápidos e confiáveis.
* **Independência Tecnológica:** O `core` é agnóstico a frameworks. A decisão de usar Spring pode ser alterada no futuro com impacto mínimo na lógica de negócio, que é o ativo mais valioso do software.
* **Manutenção Simplificada:** Com responsabilidades bem definidas, encontrar e corrigir bugs ou adicionar novas funcionalidades torna-se uma tarefa mais direta e segura.
* **Flexibilidade:** A arquitetura permite trocar componentes externos com facilidade. Mudar de PostgreSQL para outro banco de dados, por exemplo, requer alterações apenas na camada de `infrastructure`, sem afetar o `core`.

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
