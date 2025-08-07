# Sistema de Propostas de Crédito - Backend

![Status do Projeto](https://img.shields.io/badge/status-concluído-green)
![Linguagem](https://img.shields.io/badge/java-17-blue)
![Framework](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Database](https://img.shields.io/badge/PostgreSQL-15-blue)

Aplicação backend para um sistema de propostas de crédito, desenvolvida em Java com Spring Boot. O sistema oferece endpoints para criar, consultar e gerenciar o pagamento de propostas, com persistência de dados em um banco PostgreSQL.

---

## 📋 Índice

- [Funcionalidades](#-funcionalidades)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Pré-requisitos](#-pré-requisitos)
- [Como Começar](#-como-começar)
  - [1. Configurando o Banco de Dados](#1-configurando-o-banco-de-dados)
  - [2. Rodando a Aplicação](#2-rodando-a-aplicação)
- [Endpoints da API](#-endpoints-da-api)
  - [Criar Nova Proposta](#criar-nova-proposta)
  - [Buscar Proposta por ID](#buscar-proposta-por-id)
  - [Listar Propostas (Paginado)](#listar-propostas-paginado)
  - [Pagar uma Parcela](#pagar-uma-parcela)
- [Regras de Negócio e Validações](#-regras-de-negócio-e-validações)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Contato](#-contato)

---

## ✨ Funcionalidades

-   [x] Criação de novas propostas de crédito.
-   [x] Geração automática de parcelas para cada proposta.
-   [x] Listagem paginada de todas as propostas.
-   [x] Consulta de uma proposta específica por seu ID.
-   [x] Registro de pagamento de parcelas individuais.

---

## 🛠️ Tecnologias Utilizadas

-   **Java 17**: Versão da linguagem de programação.
-   **Spring Boot 3.x**: Framework principal para construção da aplicação.
-   **Spring Data JPA / Hibernate**: Para persistência de dados e mapeamento objeto-relacional.
-   **PostgreSQL 15**: Banco de dados relacional.
-   **Maven**: Gerenciador de dependências e build do projeto.
-   **Docker / Docker Compose**: Para conteinerização do banco de dados, facilitando o setup.
-   **Bean Validation (Jakarta Validation)**: Para validação dos dados de entrada.
-   **Lombok**: Para reduzir código boilerplate (getters, setters, construtores, etc.).

---

## 🚦 Pré-requisitos

Antes de começar, garanta que você tenha o seguinte instalado e configurado:

-   Java 17 (`JAVA_HOME` configurado).
-   Docker e Docker Compose (com o Docker Desktop rodando).
-   Maven (ou pode usar o wrapper `./mvnw` incluído no projeto).
-   Uma IDE de sua preferência (IntelliJ, VS Code, Eclipse).

---

### 📄 Configuração do ambiente

Antes de rodar o projeto, é necessário configurar as propriedades da aplicação.

1. **Copie o arquivo de exemplo:**

   ```bash
   cp src/main/resources/application-example.properties src/main/resources/application.properties
   ```

2. **Edite o arquivo `application.properties` com os dados reais do seu ambiente** 
(usuário, senha, nome do banco, etc).

3. **Copie o arquivo `.env.example` para `.env`: com os dados reais do seu ambiente**

```bash
cp .env.example .env
```

## 🚀 Como Começar

Siga os passos abaixo para configurar e rodar o projeto localmente.

### 1. Configurando o Banco de Dados

O banco de dados PostgreSQL é gerenciado pelo Docker. Para iniciá-lo, execute o comando na raiz do projeto:

```bash
docker-compose -f docker-compose.yaml up -d
```

O comando acima irá baixar a imagem do PostgreSQL e criar o contêiner em background.

### 2. Rodando a Aplicação

Você pode iniciar a aplicação Spring Boot de duas maneiras:

-   **Usando o Maven Wrapper (recomendado):**

```bash
./mvnw spring-boot:run
```

-   **Se você tem o Maven instalado globalmente:**

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

---

## 🔌 Endpoints da API

### Criar Nova Proposta

-   **Método:** `POST`
-   **Endpoint:** `/propostas`
-   **Descrição:** Cria uma nova proposta de crédito e gera suas parcelas.
-   **Request Body:**

```json
{
  "cpf": "00011122233",
  "valorSolicitado": 1000.00,
  "quantidadeParcelas": 12,
  "dataSolicitacao": "2023-10-01"
}
```

-   **Resposta de Sucesso:** `201 Created` com o ID da proposta criada no `header Location`.

### Buscar Proposta por ID

-   **Método:** `GET`
-   **Endpoint:** `/propostas/{id}`
-   **Descrição:** Retorna os dados de uma proposta específica e suas parcelas.
-   **Resposta de Sucesso:** `200 OK` com os dados da proposta.

### Listar Propostas (Paginado)

-   **Método:** `GET`
-   **Endpoint:** `/propostas`
-   **Descrição:** Retorna uma lista paginada de todas as propostas.
-   **Query Params (Opcionais):**
    -   `page`: Número da página (padrão: `0`).
    -   `size`: Quantidade de itens por página (padrão: `10`).
-   **Exemplo:** `GET /propostas?page=0&size=5`
-   **Resposta de Sucesso:** `200 OK` com a lista paginada de propostas.

### Pagar uma Parcela

-   **Método:** `POST`
-   **Endpoint:** `/propostas/{id}/parcelas/{numero}/pagar`
-   **Descrição:** Atualiza o status de uma parcela específica para "Paga".
-   `{id}`: ID da proposta.
-   `{numero}`: Número da parcela a ser paga.
-   **Resposta de Sucesso:** `200 OK`.

---

## ⚖️ Regras de Negócio e Validações

-   **CPF:** Deve ser um valor válido contendo 11 dígitos numéricos.
-   **Valor Solicitado:** O valor mínimo é de R$ 100,00.
-   **Quantidade de Parcelas:** Deve estar entre 1 e 24.
-   **Parcelas:** São geradas automaticamente na criação da proposta com o status inicial "Em Aberto".
-   **Pagamento:** Ao pagar uma parcela, seu status é permanentemente alterado para "Paga".

---

## 📂 Estrutura do Projeto

O código-fonte segue uma arquitetura em camadas para organizar as responsabilidades:

-   `model`: Contém as entidades JPA que mapeiam as tabelas do banco de dados.
-   `repository`: Interfaces do Spring Data JPA para acesso aos dados.
-   `service`: Onde a lógica de negócio da aplicação é implementada.
-   `controller`: Responsável por expor a API REST, receber requisições e retornar respostas.

---

## 📞 Contato

**Daniel Frey - daniel._.frey@hotmail.com)**

<!-- Link do Projeto: `(link do repositório no GitHub)` -->

---

Obrigado por conferir o projeto!