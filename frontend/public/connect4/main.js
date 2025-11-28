const API_BASE = "http://localhost:8000/games";

let gameId = null;
let currentPlayer = 1;
let board = createEmptyBoard();
let gameOver = false;

// DOM Elements
const newGameBtn = document.getElementById("new-game-btn");
const statusText = document.getElementById("status-text");
const boardDiv = document.getElementById("board");
const errorBox = document.getElementById("error-message");

const player1Card = document.getElementById("player1");
const player2Card = document.getElementById("player2");

// ------------------------------
// HELPERS
// ------------------------------
function createEmptyBoard() {
    return Array.from({ length: 6 }, () =>
        Array.from({ length: 7 }, () => 0)
    );
}

function showError(msg) {
    errorBox.style.display = "block";
    errorBox.textContent = msg;

    setTimeout(() => {
        errorBox.style.display = "none";
    }, 2000);
}

function updatePlayerHighlight() {
    if (!gameId || gameOver) return;

    player1Card.classList.remove("active", "winner", "loser");
    player2Card.classList.remove("active", "winner", "loser");

    if (currentPlayer === 1) {
        player1Card.classList.add("active");
    } else {
        player2Card.classList.add("active");
    }
}

function applyWinnerHighlight(winner) {
    player1Card.classList.remove("active");
    player2Card.classList.remove("active");

    if (winner === 1) {
        player1Card.classList.add("winner");
        player2Card.classList.add("loser");
    } else if (winner === 2) {
        player2Card.classList.add("winner");
        player1Card.classList.add("loser");
    }
}

// ------------------------------
// INITIAL RENDER
// ------------------------------
document.addEventListener("DOMContentLoaded", () => {
    renderBoard();
    updatePlayerHighlight();
});

// ------------------------------
// GAME INITIALIZATION
// ------------------------------
newGameBtn.addEventListener("click", async () => {
    const res = await fetch(`${API_BASE}/create`, { method: "POST" });
    const data = await res.json();

    gameId = data.game_id;
    gameOver = false;

    boardDiv.classList.remove("board-locked");

    // reset UI
    player1Card.classList.remove("winner", "loser");
    player2Card.classList.remove("winner", "loser");
    errorBox.style.display = "none";

    await fetchState();
    renderBoard();
    updatePlayerHighlight();
});

// ------------------------------
// FETCH GAME STATE
// ------------------------------
async function fetchState() {
    const res = await fetch(`${API_BASE}/${gameId}/state`);
    const data = await res.json();

    board = data.board;
    currentPlayer = data.current_player;

    if (data.winner === null) {
        // Game ongoing
        statusText.textContent = `Player ${currentPlayer}'s turn`;
        gameOver = false;
        return;
    }

    // --- Game Over ---
    gameOver = true;
    boardDiv.classList.add("board-locked");

    if (data.winner === 0) {
        // Draw
        statusText.textContent = `Draw! 🤝`;
        player1Card.classList.remove("active");
        player2Card.classList.remove("active");
    } else {
        // Normal win
        applyWinnerHighlight(data.winner);
        statusText.textContent = `Player ${data.winner} wins! 🎉`;
    }
}


// ------------------------------
// RENDER BOARD
// ------------------------------
function renderBoard() {
    boardDiv.innerHTML = "";

    for (let r = 0; r < 6; r++) {
        for (let c = 0; c < 7; c++) {
            const cell = document.createElement("div");
            cell.classList.add("cell");

            const token = document.createElement("div");
            token.classList.add("token");

            const val = board[r][c];
            if (val === 1) token.classList.add("player1");
            else if (val === 2) token.classList.add("player2");
            else token.classList.add("empty");

            cell.appendChild(token);

            if (!gameOver) {
                cell.addEventListener("click", () => handleMove(c));
            }

            boardDiv.appendChild(cell);
        }
    }
}

// ------------------------------
// HANDLE MOVE
// ------------------------------
async function handleMove(column) {
    if (gameOver) return;

    const res = await fetch(`${API_BASE}/${gameId}/move`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ column }),
    });

    if (!res.ok) {
        const err = await res.json();
        if (err.detail.includes("column") || err.detail.includes("full")) {
            showError("Column is full!");
        } else {
            showError(err.detail);
        }
        return;
    }

    const data = await res.json();
    board = data.board;
    currentPlayer = data.current_player;

    renderBoard();

    if (data.winner !== null) {
        gameOver = true;
        boardDiv.classList.add("board-locked");

        if (data.winner === 0) {
            statusText.textContent = "Draw! 🤝";
            player1Card.classList.remove("active");
            player2Card.classList.remove("active");
        } else {
            applyWinnerHighlight(data.winner);
            statusText.textContent = `Player ${data.winner} wins! 🎉`;
        }
    } else {
        statusText.textContent = `Player ${currentPlayer}'s turn`;
    }


    updatePlayerHighlight();
}
