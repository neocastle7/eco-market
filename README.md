# EcoMarket 🍃

Sistema de EcoVarejo desenvolvido como requisito de avaliação para a disciplina de **Programação Orientada a Objetos (POO)** na **Universidade Católica de Brasília (UCB) - Campus Taguatinga**.

O objetivo do projeto é aplicar os conceitos do paradigma de POO em um sistema completo (Backend + Banco de Dados + Interface Gráfica), focado no tema de Sustentabilidade.

---

## 🏗️ Arquitetura do Projeto

O sistema foi estruturado seguindo o padrão arquitetural em camadas exigido:
- `model`: Entidades de dados encapsuladas (`Produto`, `Usuario`).
- `repository`: Interfaces de comunicação com o Banco de Dados (Spring Data JPA).
- `service`: Camada de regras de negócio e tratamento de exceções.
- `controller`: Endpoints da API REST para integração com o Frontend.
- `util`: Classes utilitárias do sistema.

---

## 👥 Divisão de Responsabilidades da Equipe

Para garantir o bom andamento e cumprir o tempo de apresentação (7 minutos), dividimos as frentes de trabalho da seguinte forma:

* **Lucas & Jackson:** Desenvolvimento do **Backend** (Java / Spring Boot), estrutura de banco de dados e Sistema de Autenticação.
* **Jennyfer & Luan:** Desenvolvimento do **Frontend** (Interface do Usuário / CRUDs integrados à API).
* **Leonardo:** Modelagem técnica, diagramas estruturais (Classes/Casos de Uso) e organização da apresentação.

---

## 🛠️ Tecnologias Utilizadas

- **Backend:** Java 17+ / Spring Boot 3
- **Persistência:** Spring Data JPA
- **Banco de Dados:** SGBD Relacional
