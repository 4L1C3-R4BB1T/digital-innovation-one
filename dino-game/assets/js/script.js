const dino = document.querySelector('.dino');
const background = document.querySelector('.background');
let isJumping = false;
let position = 0;
let score = 0;
let scoreId = null;
let stopCreateCactus = false;

function handleKeyUp(event) {
    if (event.keyCode === 32 || event.keyCode === 38) {
        if (!isJumping) jump();
    }
}

function jump() {
    isJumping = true;

    let upInterval = setInterval(() => {
        if (position >= 110) {
            clearInterval(upInterval);
            let downInterval = setInterval(() => {
                if (position <= 0) {
                    clearInterval(downInterval);
                    isJumping = false;
                } else {
                    position -= 10;
                    dino.style.bottom = position + 'px';
                }
            }, 25);
        } else {
            position += 10;
            dino.style.bottom = position + 'px';
        }
    }, 25);
}

function createCactus() {
    const cactus = document.createElement('div');
    let cactusPosition = 700;
    let randomTime = Math.random() * 6000;

    cactus.classList.add('cactus');
    cactus.style.left = 700 + 'px';
    background.appendChild(cactus);

    let leftInterval = setInterval(() => {
        if (cactusPosition < -30) {
            clearInterval(leftInterval);
            background.removeChild(cactus);
        } else if (cactusPosition < 124 && cactusPosition > 80 && position < 51) {
            stopCreateCactus = true;
            clearInterval(leftInterval);
            clearTimeout(scoreId);
            document.body.innerHTML = '<h1 class="game-over">Game Over<br><a href="index.html"><img src="assets/img/button.png"></a></h1>';
        } else {
            cactusPosition -= 10;
            cactus.style.left = cactusPosition + 'px';
        }
    }, 40);

    if (!stopCreateCactus) setTimeout(createCactus, randomTime);
}

function totalScore() {
    score++;
    document.getElementById('scoreId').innerHTML = score;
    scoreId = setTimeout(totalScore, 1000);
}

createCactus();
totalScore();

document.addEventListener('keyup', handleKeyUp);
