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

## 🏛️ Arquitetura Usada: Clean Architecture (Do Jeito Certo!)

A gente sabe como projeto de faculdade pode virar uma bagunça, né? Pra evitar isso, o backend foi organizado usando a **Clean Architecture**. A ideia principal é separar o código em camadas, pra que uma coisa não dependa da outra e o projeto fique fácil de mexer no futuro.

A regra de ouro é a **Regra da Dependência**: o código só pode "olhar" pra dentro. Pensa assim: o chefão (círculo de dentro) não pode saber quem são os estagiários (círculos de fora). A comunicação é sempre de fora pra dentro.

Pra forçar essa organização, o projeto foi quebrado em 3 "mini-projetos" (módulos Maven).

### As Camadas do Projeto

#### 📁 `core` - O Cérebro do Rolê

Essa é a parte mais importante e protegida. Aqui ficam as "regras do jogo" do nosso aplicativo, e o mais legal: é Java puro, sem nenhuma frescura de Spring, JPA ou qualquer outra coisa.

* **Entidades (`Entities`):** São como as "cartinhas" do nosso jogo. Classes simples como `Expense` e `Category` que representam as coisas do nosso sistema.
* **Casos de Uso (`Use Cases`):** São as "jogadas" que a gente pode fazer. Classes como `RegisterExpenseUseCase` que contêm a lógica do que acontece quando o usuário faz uma ação.
* **Interfaces de Repositório:** São os "contratos". O `core` diz: "Preciso que alguém salve essa despesa no banco", mas ele não quer saber *como* isso vai ser feito. Ele só define o contrato.

#### 📁 `infrastructure` - A Galera do Trabalho Pesado

É aqui que a mágica do Spring e do banco de dados acontece. Essa camada faz o trabalho sujo que o `core` pediu.

* **Controllers:** É o "porteiro" da nossa API. Ele recebe as requisições da internet (do frontend), pega os dados e passa a bola pro `core` resolver.
* **Implementações de Repositório:** É o "funcionário" que assinou o contrato do `core`. Ele sabe falar a língua do PostgreSQL e usa o Spring Data JPA pra de fato salvar as coisas no banco.
* **Mappers:** São os "tradutores". Como o `core` e a `infrastructure` usam objetos um pouco diferentes, os mappers ficam no meio do caminho convertendo um pro outro.

#### 📁 `application` - O Eletricista que Liga Tudo

Esse módulo não tem muita lógica, mas é super importante. Ele é a "cola" que junta todas as peças.

* **Classe Principal:** Onde fica o `main` que liga o projeto todo. O botão de ON/OFF.
* **Configuração de Beans:** A parte mais genial. É aqui que a gente fala pro Spring: "Ó, quando o `core` pedir um `CategoryRepository` (o contrato), entrega pra ele o `CategoryRepositoryImpl` (o funcionário) que tá na `infrastructure`". O `core` nunca fica sabendo quem fez o trabalho, mantendo tudo separado e organizado.

### Como Funciona na Prática (Criando uma Despesa)

1. O frontend manda um `POST /expenses`.
2. O **`Controller`** (`infrastructure`), nosso porteiro, recebe a requisição.
3. Ele chama o **`RegisterExpenseUseCase`** (`core`), o cérebro, e entrega os dados.
4. O `UseCase` faz as validações (o valor é positivo? a categoria existe?).
5. O `UseCase` fala: "Preciso salvar isso!", e chama o método `save` do contrato `ExpenseRepository` (`core`).
6. O Spring, que foi configurado pelo `application`, entra em ação e entrega o `ExpenseRepositoryImpl` (`infrastructure`) pra fazer o serviço.
7. O `RepositoryImpl` usa o `Mapper` pra traduzir o objeto, e manda o Spring Data JPA salvar no PostgreSQL.
8. Pronto! A resposta volta pelo mesmo caminho e o frontend recebe um "OK, tudo certo!".

### E pra que essa trabalheira toda?

* **Testar fica fácil:** Dá pra testar as regras do jogo no `core` sem precisar ligar o servidor inteiro.
* **Sem ficar preso:** O `core` tá nem aí pro Spring. Se amanhã a gente quiser usar outro framework, a parte mais importante do código continua funcionando.
* **Manutenção de boa:** Fica muito mais fácil de achar um bug ou adicionar uma função nova sem quebrar o resto do projeto.
* **Flexibilidade:** Quer trocar o PostgreSQL por outro banco? É só mexer na `infrastructure`. O `core` nem vai perceber.

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
