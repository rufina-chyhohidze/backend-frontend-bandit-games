# BanditGames Platform Backend

This is the **platform backend** for BanditGames.  
It exposes a REST API that the React frontend uses to show a list of playable games (Connect Four + Chess), and will later handle more platform features (lobby, friends, achievements, etc.).

---

## Tech Stack

- Java / Spring Boot
- Hexagonal / Clean architecture
    - `domain/`
    - `port/in`, `port/out`
    - `core/` (use case implementations)
    - `adapter/in` (REST)
    - `adapter/out` (in-memory repo for now)
- MongoDB (for gameplay logging, not used by the game list yet)
- RabbitMQ (for events, not used in this user story yet)

---

## Ports

- **Backend (this app)**: `8080`
- **MongoDB**: `27017`
- **RabbitMQ** (AMQP): `5672`
- **RabbitMQ UI**: `15672`
- **Keycloak**: `8180`
- **Chess frontend (external game)**: `3333`
- **Chess backend (teachers’ service)**: internal in Docker network, not exposed on host

---

## Infrastructure (Docker Compose)

The `infrastructure/docker-compose.yml` file starts:

- `gameplay_mongodb` – MongoDB
- `app_rabbitmq` – RabbitMQ + management UI
- `idp_mysql` + `idp_keycloak` – identity provider database + Keycloak
- `chess_postgres` – Postgres only for the external chess game
- `app_chessgame_back` – teachers’ chess backend
- `app_chessgame_front` – teachers’ chess React frontend

To start infra:

```bash
cd infrastructure
docker compose up -d
