let canvas = document.getElementById('snake');
let context = canvas.getContext('2d');
let box = 32;
let snake = [];
let score = 0;

snake[0] = { x: 8 * box, y: 8 * box }

let direction = 'right';

let food = {
    x: Math.floor(Math.random() * 15 + 1) * box,
    y: Math.floor(Math.random() * 15 + 1) * box
}

// DESENHA O BACKGROUND
function drawBackground() {
    context.fillStyle = '#b3ffb3';
    context.fillRect(0, 0, 16 * box, 16 * box);
}

// DESENHA A COBRA
function drawSnake() {
    for (i = 0; i < snake.length; i++) {
        context.fillStyle = 'green';
        context.fillRect(snake[i].x, snake[i].y, box, box);
    }
}

// REDESENHA A COBRA CASO ELA SAIA DA TELA
function redrawSnake() {
    if (snake[0].x > 15 * box && direction == 'right') {
        snake[0].x = 0;
    }
    if (snake[0].x < 0 && direction == 'left') {
        snake[0].x = 16 * box;
    }
    if (snake[0].y > 15 * box && direction == 'down') {
        snake[0].y = 0;
    }
    if (snake[0].y < 0 && direction == 'up') {
        snake[0].y = 16 * box;
    }
}

// DESENHA A COMIDINHA
function drawFood() {
    context.fillStyle = 'red';
    context.fillRect(food.x, food.y, box, box);
}

// VERIFICA SE A COBRA COMEU A COMIDINHA
function eatFood(snakeX, snakeY) {
    if (snakeX != food.x || snakeY != food.y) {
        snake.pop();
    } else {
        food.x = Math.floor(Math.random() * 15 + 1) * box;
        food.y = Math.floor(Math.random() * 15 + 1) * box;
        
        score++;
        document.getElementById('score').innerHTML = score;
    }
}

// MUDA A DIREÇÃO DA COBRA
function changeDirection(event) {
    if (event.keyCode == 37 && direction != 'right') {
        direction = 'left';
    }
    if (event.keyCode == 38 && direction != 'down') {
        direction = 'up';
    }
    if (event.keyCode == 39 && direction != 'left') {
        direction = 'right';
    }
    if (event.keyCode == 40 && direction != 'up') {
        direction = 'down';
    }
}

// VERIFICA SE A COBRA ENCOSTOU NELA MESMA
function gameOver() {
    for (i = 1; i < snake.length; i++) {
        if (snake[0].x == snake[i].x && snake[0].y == snake[i].y) {
            clearInterval(game);
            // alert('GAME OVER :(');
            document.body.innerHTML = '<h1 class="game-over">Game Over<br><a href="index.html"><button>Jogar de Novo</button></a></h1>';
        }
    }
}

// INICIA O JOGO
function startGame() {
    redrawSnake();
    
    gameOver();
    
    drawBackground();
    drawSnake();
    drawFood();

    let snakeX = snake[0].x;
    let snakeY = snake[0].y;

    // FAZ A COBRA SE MOVIMENTAR
    if (direction == 'right') {
        snakeX += box;
    }
    if (direction == 'left') {
        snakeX -= box;
    }
    if (direction == 'up') {
        snakeY -= box;
    }
    if (direction == 'down') {
        snakeY += box;
    }

    eatFood(snakeX, snakeY);

    let newHead = { x: snakeX, y: snakeY }
    snake.unshift(newHead);
}

document.addEventListener('keydown', changeDirection);

let game = setInterval(startGame, 100);