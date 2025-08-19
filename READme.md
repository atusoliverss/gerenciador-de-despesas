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

O backend deste projeto foi meticulosamente estruturado seguindo os princípios da **Clean Architecture**, popularizada por Robert C. Martin. O objetivo central é criar um sistema desacoplado, testável e independente de detalhes externos como frameworks, banco de dados e interface de usuário. Isso é alcançado através de uma estrita separação de responsabilidades em camadas concêntricas, governadas pela **Regra da Dependência**.

> **A Regra da Dependência:** O código-fonte só pode ter dependências que apontam para o interior. Nada em um círculo interno pode saber qualquer coisa sobre um círculo externo. Em particular, o nome de algo declarado em um círculo externo não deve ser mencionado pelo código em um círculo interno.

Para implementar essa arquitetura em um projeto Java/Spring, o backend foi dividido em 3 módulos Maven distintos, cada um representando uma ou mais camadas da arquitetura.

### As Camadas do Projeto

#### 📁 `core` - O Coração do Negócio (Camadas de Entidades e Casos de Uso)

Esta é a camada mais interna e protegida. Ela encapsula toda a lógica de negócio da aplicação e não possui nenhuma dependência de frameworks externos.

* **Entidades (`Entities`):** São os objetos de negócio puros (POJOs) que representam os conceitos centrais do domínio (ex: `Expense`, `Category`). Elas contêm os atributos e as regras de negócio mais críticas e são as menos propensas a mudar quando detalhes externos mudam.

* **Casos de Uso (`Use Cases`):** Orquestram o fluxo de dados de e para as entidades para executar uma tarefa específica do negócio (ex: `RegisterExpenseUseCase`). Eles contêm a lógica da aplicação, validam entradas e aplicam as regras.

* **Interfaces de Repositório:** São os "contratos" definidos pelo `core` que ditam as operações de persistência necessárias (ex: `save`, `findById`). O `core` define *o que* precisa ser feito, mas não se importa com *como* será feito.

#### 📁 `infrastructure` - Detalhes de Implementação (Camada de Adaptadores e Frameworks)

Esta camada externa contém todas as ferramentas e tecnologias. Ela se "adapta" para servir às necessidades do `core`.

* **Controllers:** Atuam como adaptadores que convertem as requisições HTTP da web em chamadas para os `Use Cases` do `core`. Eles são a porta de entrada da API REST.

* **Implementações de Repositório:** São as classes concretas que implementam as interfaces de repositório do `core`. É aqui que a tecnologia de persistência (Spring Data JPA, PostgreSQL) é de fato utilizada para executar as operações no banco de dados.

* **Mappers:** Classes utilitárias essenciais que traduzem os objetos entre as camadas. Por exemplo, convertem uma entidade de domínio pura (`Category`) em uma entidade JPA anotada (`CategoryJpaEntity`) e vice-versa.

#### 📁 `application` - O Ponto de Partida (Composição e Iniciação)

Este módulo atua como a "cola" que une o sistema. Sua principal responsabilidade é a **Composição da Raiz (Composition Root)**.

* **Classe Principal:** Contém o método `main` e a anotação `@SpringBootApplication` que inicia todo o contexto do Spring.

* **Configuração de Beans:** Através de classes com `@Configuration`, este módulo implementa a **Inversão de Dependência**. É aqui que dizemos ao Spring Framework como "conectar" as abstrações do `core` (as interfaces de repositório) com as implementações concretas do `infrastructure`. O `core` nunca conhece o `infrastructure` diretamente; é o `application` que faz essa ligação em tempo de execução.

### Fluxo de Controle: Um Exemplo Prático (Criar uma Despesa)

1. Uma requisição `POST /expenses` chega do frontend.

2. O **`ExpenseController`** (`infrastructure`) recebe a requisição, valida o corpo (DTO) e extrai os dados brutos.

3. O `Controller` chama o método `execute` do **`RegisterExpenseUseCase`** (`core`), passando os dados de forma simples e pura.

4. O `UseCase` executa suas regras de negócio (ex: verifica se o valor é positivo, se a categoria existe).

5. O `UseCase` chama o método `save` da interface **`ExpenseRepository`** (`core`).

6. O Spring, configurado pelo módulo `application`, injeta a implementação **`ExpenseRepositoryImpl`** (`infrastructure`).

7. A `ExpenseRepositoryImpl` usa seu **`Mapper`** para converter a entidade de domínio `Expense` em uma `ExpenseJpaEntity`.

8. A `ExpenseRepositoryImpl` usa o **Spring Data JPA** para persistir a `ExpenseJpaEntity` no banco de dados PostgreSQL.

9. A resposta flui de volta pelas camadas até o `Controller`, que retorna um status HTTP `201 Created`.

### Benefícios Alcançados

* **Testabilidade:** A lógica de negócio no `core` pode ser testada de forma unitária, sem a necessidade de rodar um servidor web ou um banco de dados.

* **Independência de Frameworks:** O `core` não sabe sobre o Spring. Se um dia quisermos migrar para outro framework (como Quarkus ou Micronaut), a lógica de negócio permanece intacta.

* **Manutenibilidade:** A separação clara de responsabilidades torna o código mais fácil de entender, depurar e estender. Novas funcionalidades podem ser adicionadas com impacto mínimo nas existentes.

* **Flexibilidade:** Trocar o banco de dados, a interface do usuário ou qualquer outro detalhe externo se torna uma tarefa muito mais simples, pois essas tecnologias estão isoladas na camada de `infrastructure`.

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
