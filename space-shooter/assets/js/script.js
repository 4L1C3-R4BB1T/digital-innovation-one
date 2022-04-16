const playerShip = document.querySelector('.player');
const playArea = document.querySelector('#main-game');
const gameInfo = document.querySelector('.game-info');
const startButton = document.querySelector('.start');

let alienInterval;

// movimento e tiro da nave
function flyShip(event) {
    event.preventDefault();

    if (event.key === 'ArrowUp') moveUp();
    if (event.key === 'ArrowDown') moveDown();
    if (event.key === ' ') fireLaser();
}

// mover nave para cima
function moveUp() {
    let topPosition = getComputedStyle(playerShip).getPropertyValue('top');
    
    if (topPosition !== '50px') {
        let position = parseInt(topPosition);
        position -= 50;
        playerShip.style.top = `${position}px`; 
    }
}

// mover nave para baixo
function moveDown() {
    let topPosition = getComputedStyle(playerShip).getPropertyValue('top');
    
    if (topPosition !== '550px') {
        let position = parseInt(topPosition);
        position += 50;
        playerShip.style.top = `${position}px`; 
    }
}

// atirar
function fireLaser() {
    let laser = createLaserElement();
    playArea.appendChild(laser);
    moveLaser(laser);
    console.log(laser.style.left);
}

// criar laser
function createLaserElement() {
    let x = parseInt(window.getComputedStyle(playerShip).getPropertyValue('left'));
    let y = parseInt(window.getComputedStyle(playerShip).getPropertyValue('top'));

    let newLaser = document.createElement('img');
    newLaser.src = 'assets/img/shoot.png';
    newLaser.classList.add('laser');
    newLaser.style.left = `140px`;
    newLaser.style.top = `${y - 30}px`;

    return newLaser;
}

function moveLaser(laser) {
    setInterval(() => {
        let x = parseInt(laser.style.left);
        let aliens = document.querySelectorAll('.alien');

        // verifica se alien foi atingido 
        aliens.forEach((alien) => {
            if (laserCollision(laser, alien)) {
                laser.remove();
                alien.src = 'assets/img/explosion.png';
                alien.classList.remove('alien');
                alien.classList.add('dead-alien');
            }
        });

        if (x === 1040) laser.remove();
        else laser.style.left = `${x + 4}px`;
    }, 10);
}

// criar aliens 
function createAliens() {
    let alien = Math.floor(Math.random() * 3) + 1; 
    
    let newAlien = document.createElement('img');
    newAlien.src = `assets/img/monster-${alien}.png`; 
    newAlien.classList.add('alien');
    newAlien.classList.add('alien-transition');
    newAlien.style.left = '980px';
    newAlien.style.top = `${Math.floor(Math.random() * 540) + 30}px`;

    playArea.appendChild(newAlien);

    moveAlien(newAlien);
}

// mover alien
function moveAlien(alien) {
    setInterval(() => {
        let x = parseInt(window.getComputedStyle(alien).getPropertyValue('left'));
        
        if (x <= 280) {
            if (Array.from(alien.classList).includes('dead-alien')) alien.remove();
            else gameOver();
        } else {
            alien.style.left = `${x - 2}px`;
        }
    }, 30);
}

// colisao entre laser e alien
function laserCollision(laser, alien) {
    let laserTop = parseInt(laser.style.top);
    let laserLeft = parseInt(laser.style.left);
        
    let alienTop = parseInt(alien.style.top);
    let alienLeft = parseInt(alien.style.left);
    let alienBottom = alienTop - 100;   

    if (laserLeft != 1040 && laserLeft + 120 != alienLeft) {
        if (laserTop <= alienTop && laserTop >= alienBottom 
            && laserLeft + 200 >= alienLeft) {
            return true;
        } else {
            return false;
        }
    } 

    return false;
}

// game over
function gameOver() {
    window.removeEventListener('keydown', flyShip);

    document.querySelectorAll('.alien').forEach((alien) => alien.remove());
    document.querySelectorAll('.laser').forEach((laser) => laser.remove());

    clearInterval(alienInterval);

    gameInfo.style.display = 'flex';
    playerShip.style.top = '300px';
    
    alert('Game Over!');
}

// iniciar jogo 
function playGame() {
    gameInfo.style.display = 'none';

    window.addEventListener('keydown', flyShip);

    createAliens();
    
    alienInterval = setInterval(() => {
        createAliens();
    }, 4000);
}
