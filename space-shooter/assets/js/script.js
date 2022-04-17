const playArea = document.querySelector('#main-game');
const gameInfo = document.querySelector('.game-info');
const startButton = document.querySelector('.start');

let bgm = document.getElementById("bgm");
let shot = document.getElementById("shot");
let gameover = document.getElementById("gameover");
let explosion = document.getElementById("explosion");

bgm.volume -= .8;
gameover.volume -= .7;

let playerShip;
let alienInterval;
let canShot = true;
let velocity = 4;
let score = 0;
let time = 3000;

// movimento e tiro da nave
function flyShip(event) {
    event.preventDefault();

    if (event.key === 'ArrowUp') {
        moveUp();
    }
    
    if (event.key === 'ArrowDown') {
        moveDown();
    }
    
    if (event.key === ' ' && canShot)  {
        canShot = false;
        fireLaser();
    }
}

// mover nave para cima
function moveUp() {
    let topPosition = getComputedStyle(playerShip).getPropertyValue('top');
    
    if (topPosition !== '10px') {
        let position = parseInt(topPosition);
        position -= 20;
        playerShip.style.top = `${position}px`; 
    }
}

// mover nave para baixo
function moveDown() {
    let topPosition = getComputedStyle(playerShip).getPropertyValue('top');
    
    if (topPosition !== '530px') {
        let position = parseInt(topPosition);
        position += 20;
        playerShip.style.top = `${position}px`; 
    }
}

// atirar
function fireLaser() {
    shot.play();

    let laser = createLaserElement();
    playArea.appendChild(laser);
    moveLaser(laser);
}

// criar laser
function createLaserElement() {
    let y = parseInt(window.getComputedStyle(playerShip).getPropertyValue('top'));

    let newLaser = document.createElement('img');
    newLaser.src = 'assets/img/laser.png';
    newLaser.classList.add('laser');
    newLaser.style.left = '0px';
    newLaser.style.top = `${y - 166}px`;

    return newLaser;
}

function moveLaser(laser) {
    setInterval(() => {
        /*
            mesmo apos removido o laser continuava contando 
            como existente, entao atribui null para o valor 
            do laser enquanto um novo laser nao eh criado
        */
        if (laser) {
            let laserLeft = parseInt(laser.style.left);
            let aliens = document.querySelectorAll('.alien');

            if (laserLeft >= 930) {
                laser.remove();
                
                laser = null;
                canShot = true;
            } else {
                laser.style.left = `${laserLeft + 3}px`;

                // verifica se alien foi atingido 
                aliens.forEach((alien) => {
                    if (laser && laserCollision(laser, alien)) {
                        explosion.play();
                        
                        laser.remove();
                        
                        laser = null;
                        canShot = true;
                    
                        alien.src = 'assets/img/explosion.png';
                        alien.classList.remove('alien');
                        alien.classList.add('dead-alien');
                                
                        velocity += 0.1;
                        score += 10;
                        time -= 5;
                        
                        document.getElementById('score').innerHTML = score;

                        setTimeout(() => {
                            alien.remove();
                        }, 1000);
                    } 
                });
            }
        }
    }, 5);
}

// criar aliens 
function createAlien() {
    let alien = Math.floor(Math.random() * 3) + 1; 
    
    let newAlien = document.createElement('img');
    newAlien.src = `assets/img/alien-${alien}.png`; 
    newAlien.classList.add('alien');
    newAlien.classList.add('alien-transition');
    newAlien.style.left = '970px';
    newAlien.style.top = `${Math.floor(Math.random() * 430)}px`;

    playArea.appendChild(newAlien);

    moveAlien(newAlien);
}

// mover alien
function moveAlien(alien) {
    setInterval(() => {
        let alienLeft = parseInt(window.getComputedStyle(alien).getPropertyValue('left'));
        
        if (alienLeft <= 125 && 
            Array.from(alien.classList).includes('alien')) {
            gameOver();
        } else {
            alien.style.left = `${alienLeft - velocity}px`;
        }
    }, 30);
}

// colisao entre laser e alien
function laserCollision(laser, alien) {
    let laserTop = parseInt(laser.style.top);
    let laserLeft = parseInt(laser.style.left);
    let laserRight = laserLeft + 37;
        
    let alienTop = parseInt(alien.style.top) - 20;
    let alienLeft = parseInt(alien.style.left) - 124;
    let alienBottom = alienTop - 142;   

    if (laserLeft != 1040 && laserRight != alienLeft) {
        if (laserTop <= alienTop && 
            laserTop >= alienBottom && 
            laserRight >= alienLeft) {
            return true;
        } 
        return false;
    } 

    return false;
}

// game over
function gameOver() {
    bgm.pause();
    bgm.currentTime = 0; 

    gameover.play();

    window.removeEventListener('keydown', flyShip);

    document.querySelectorAll('.alien').forEach((alien) => alien.remove());
    document.querySelectorAll('.laser').forEach((laser) => laser.remove());

    clearInterval(alienInterval);

    playArea.classList.remove('bg-animation');
    playerShip.remove();

    gameInfo.style.display = 'flex';

    setTimeout(() => {
        alert('GAME OVER :(');
    }, 100);
}

// iniciar jogo 
function playGame() {
    gameInfo.style.display = 'none';
    playArea.classList.add('bg-animation');

    // insere o player na area de jogo
    let playerDiv = document.createElement('div');
    playerDiv.classList.add('player');
    playArea.appendChild(playerDiv);    
    playerShip = document.querySelector('.player');
    
    document.getElementById('score').innerHTML = 0;

    window.addEventListener('keydown', flyShip);

    bgm.addEventListener("ended", () => { 
        bgm.currentTime = 0; 
        bgm.play(); 
    }, false);
	
    bgm.play();

    createAlien();
    
    alienInterval = setInterval(() => {
        createAlien();
    }, time);
}
