const playerShip = document.querySelector('.player');
const playArea = document.querySelector('#main-game');

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
    
    if (topPosition !== "50px") {
        let position = parseInt(topPosition);
        position -= 10;
        playerShip.style.top = `${position}px`; 
    }
}

// mover nave para baixo
function moveDown() {
    let topPosition = getComputedStyle(playerShip).getPropertyValue('top');
    
    if (topPosition !== "550px") {
        let position = parseInt(topPosition);
        position += 10;
        playerShip.style.top = `${position}px`; 
    }
}

// atirar
function fireLaser() {
    let laser = createLaserElement();
    playArea.appendChild(laser);
    moveLaser(laser);
}

// cria o elemento laser
function createLaserElement() {
    let x = parseInt(window.getComputedStyle(playerShip).getPropertyValue('left'));
    let y = parseInt(window.getComputedStyle(playerShip).getPropertyValue('top'));

    let newLaser = document.createElement('img');
    newLaser.src = "assets/img/shoot.png";
    newLaser.classList.add('laser');
    newLaser.style.left = `${x}px`;
    newLaser.style.top = `${y - 30}px`;
    
    return newLaser;
}

function moveLaser(laser) {
    let laserInterval = setInterval(() => {
        let x = parseInt(laser.style.left);

        if (x === 1040) laser.remove();
        else laser.style.left = `${x + 4}px`;
    }, 10);
}







window.addEventListener('keydown', flyShip);
