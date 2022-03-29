let order = [];
let clickedOrder = [];

let score = 0;

// 0 - green | 1 - red | 2 - yellow | 3 - blue
const green = document.querySelector('.green');
const red = document.querySelector('.red');
const yellow = document.querySelector('.yellow');
const blue = document.querySelector('.blue');

// cria ordem aleatoria de cores
let shuffleOrder = () => {
    let colorOrder = Math.floor(Math.random() * 4);
    order[order.length] = colorOrder;
    clickedOrder = [];

    for (let i in order) {
        let elementColor = createElementColor(order[i]);
        lightColor(elementColor, Number(i) + 1);
    }
}

// acende a proxima cor
let lightColor = (element, number) => {
    number *= 500;
    
    setTimeout(() => {
        element.classList.add('selected');
    }, number - 250);

    setTimeout(() => {
        element.classList.remove('selected');
    }, number);
}

// checa se os botões clicados são os mesmos da ordem gerada no jogo
let checkOrder = () => {
    let wrong = false;

    for (let i in clickedOrder) {
        if (clickedOrder[i] != order[i]) {
            gameOver();
            wrong = true;
            break;
        }
    }

    if (clickedOrder.length == order.length && !wrong) {
        alert(`Você acertou! Iniciando próximo nível!`);
        document.getElementById('score').innerHTML = score;
        nextLevel();
    }
}

// clique do usuário
let click = (color) => {
    clickedOrder[clickedOrder.length] = color;
    createElementColor(color).classList.add('selected');

    setTimeout(() => {
        createElementColor(color).classList.remove('selected');
        checkOrder();
    }, 250);
}

// retorna a cor
let createElementColor = (color) => {
    if (color == 0) return green;
    if (color == 1) return red;
    if (color == 2) return yellow;
    if (color == 3) return blue;
}

// proximo level
let nextLevel = () => {
    score++;
    shuffleOrder();
}

// game over
let gameOver = () => {
    alert(`Você perdeu o jogo :(`);
    order = [];
    clickedOrder = [];
    document.querySelector('.btn-play').disabled = false;

    // remover eventos de clique
    green.onclick = "";
    red.onclick = "";
    yellow.onclick = "";
    blue.onclick = "";
}

// iniciar jogo
let playGame = () => {
    // eventos de clique para as cores
    green.onclick = () => click(0);
    red.onclick = () => click(1);
    yellow.onclick = () => click(2);
    blue.onclick = () => click(3);

    alert(`Bem vindo ao Genius! Iniciando novo jogo!`);
    
    score = 0;
    
    document.getElementById('score').innerHTML = score;
    document.querySelector('.btn-play').disabled = true;
    
    nextLevel();
}
