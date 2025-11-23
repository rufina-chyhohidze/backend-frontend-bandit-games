# BanditGames Platform – Frontend

This is the **React + Vite** frontend for the BanditGames platform.

It currently implements the user story:

> _As a Player, I want to see a list of playable games, so I can choose which game I want to play._

The frontend:

- Calls the backend endpoint `GET /api/games`.
- Shows the list of available games in a Material UI grid.
- Allows the user to:
    - Open the **external Chess game** (teachers’ app).
    - Navigate to an internal **Connect Four** game route (placeholder for now).

---

## Tech Stack

- [Vite](https://vitejs.dev/) (dev server + bundler)
- React + TypeScript
- React Router v6
- Material UI (`@mui/material`)

---

## Ports

- **Frontend (Vite dev server)**: `5173`
- **Backend (Spring Boot)**: `8080`
- **External Chess frontend (teachers’ image)**: `3333`

The frontend never talks directly to `8080`; during development it uses a **proxy** (see below).

---

## Prerequisites

- Node.js (LTS) + npm or yarn
- Backend running on `http://localhost:8080` with:
    - `GET /api/games` implemented and accessible
- Docker infra running (for the chess game & RabbitMQ etc.), e.g.:

  ```bash
  cd ../infrastructure
  docker compose up -d
