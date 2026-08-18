# Task Manager API

Sistema de gerenciamento de tarefas construído para praticar um stack completo cloud-native: API em Java/Spring Boot, mensageria com Kafka, containerização com Docker, orquestração com Kubernetes, pipeline de CI com GitHub Actions e frontend em React.

## Stack

- **Backend:** Java 17, Spring Boot, Spring Data JPA, Spring for Apache Kafka
- **Banco de dados:** PostgreSQL
- **Mensageria:** Apache Kafka
- **Frontend:** React (Vite)
- **Containerização:** Docker, Docker Compose
- **Orquestração:** Kubernetes (manifests inclusos, testado localmente com Minikube)
- **CI:** GitHub Actions (build, testes e build da imagem Docker a cada push)

## Arquitetura

Usuário (navegador)
       │
       ▼
Frontend React (localhost:5173)
       │  HTTP/REST
       ▼
API Java - Spring Boot (localhost:8080)
       │
       ├──► PostgreSQL (persistência das tarefas)
       │
       └──► Kafka (tópico "task-events")
                  │
                  ▼
            Serviço consumidor
        (loga a notificação simulada)

Toda vez que uma tarefa é criada via 'POST /tasks', a API publica um evento no Kafka. Um consumidor separado escuta esse evento de forma assíncrona e desacoplada — simulando, por exemplo, um serviço de notificação que não trava a resposta da API esperando ser executado.

## Como rodar localmente

### Pré-requisitos
- Docker e Docker Compose
- Node.js (para rodar o frontend)

### Subindo o backend (API + Postgres + Kafka)

- bash
docker compose up -d --build

Isso sobe três containers: 'task-manager-postgres', 'task-manager-kafka' e 'task-manager-api'. A API fica disponível em 'http://localhost:8080'.

### Subindo o frontend

- bash
cd task-manager-frontend
npm install
npm run dev

Disponível em 'http://localhost:5173'.

## Endpoints da API

| Método | Rota      | Descrição                          |
|--------|-----------|-------------------------------------|
| GET    | '/tasks'  | Lista todas as tarefas              |
| POST   | '/tasks'  | Cria uma nova tarefa                |

Exemplo de corpo para criação:

- json
{
  "title": "Estudar Kubernetes"
}

## Rodando em um cluster Kubernetes (local, via Minikube)

Os manifests estão na pasta 'k8s/'.

- bash
docker build -t task-manager-api:latest .
minikube image load task-manager-api:latest
kubectl apply -f k8s/
kubectl get pods
minikube service task-manager-api

Isso cria Deployments e Services para a API (2 réplicas), Postgres e Kafka, todos rodando dentro do cluster.

## CI/CD

O pipeline em '.github/workflows/ci.yml' roda automaticamente a cada push na branch 'main':
1. Compila o projeto com Maven
2. Roda os testes automatizados (com Postgres e Kafka temporários disponíveis via GitHub Actions services)
3. Builda a imagem Docker, validando que o 'Dockerfile' está correto

## Estrutura do projeto

task-manager-api/
├── src/main/java/com/yanfink/taskmanagerapi/
│   ├── controller/     # Endpoints REST
│   ├── model/          # Entidades JPA
│   ├── repository/     # Interfaces Spring Data JPA
│   └── kafka/          # Producer e Consumer de eventos
├── k8s/                # Manifests Kubernetes
├── .github/workflows/  # Pipeline de CI
├── docker-compose.yml
└── Dockerfile

## Autor

Yan Fink — [LinkedIn](https://linkedin.com/in/yan-fink)
