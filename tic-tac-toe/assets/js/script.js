let player = null;
let finished = false;

let fields = document.getElementsByClassName('field');
let selectedPlayer = document.getElementById('selected-player');
let winner = document.getElementById('winner');

swapPlayer('X');

function chooseField(id) {
    if (finished) return;

    let field = document.getElementById(id);

    if (field.innerHTML !== '-') return;

    field.innerHTML = player;
    field.style.color = '#000';

    if (player === 'X') swapPlayer('O');
    else swapPlayer('X');

    checkMatch();
}

function swapPlayer(value) {
    player = value;
    selectedPlayer.innerHTML = player;
}

function checkMatch() {
    fields = document.getElementsByClassName('field');
    
    // verifica horizontal
    for (let i = 0; i < 9; i += 3) {
        if (isMatch(fields[i], fields[i + 1], fields[i + 2])) {
            changeFieldColor(fields[i], fields[i + 1], fields[i + 2]);
            setWinner(fields[i]);
            return;
        }
    }

    // verifica vertical
    for (let i = 0; i < 3; i++) {
        if (isMatch(fields[i], fields[i + 3], fields[i + 6])) {
            changeFieldColor(fields[i], fields[i + 3], fields[i + 6]);
            setWinner(fields[i]);
            return;
        }
    }

    // verifica diagonais
    if (isMatch(fields[0], fields[4], fields[8])) {
        changeFieldColor(fields[0], fields[4], fields[8]);
        setWinner(fields[0]);
        return;
    }

    if (isMatch(fields[2], fields[4], fields[6])) {
        changeFieldColor(fields[2], fields[4], fields[6]);
        setWinner(fields[2]);
        return;
    }
}

function isMatch(field1, field2, field3) {
    return (field1.innerHTML !== '-' && 
            field1.innerHTML === field2.innerHTML && 
            field2.innerHTML === field3.innerHTML);
}

function changeFieldColor(field1, field2, field3) {
    field1.style.background = '#0F0';
    field2.style.background = '#0F0';
    field3.style.background = '#0F0';
}

function setWinner(field) {
    winner.innerHTML = field.innerHTML;
    finished = true;
}

function restart() {
    finished = false;
    winner.innerHTML = '';
    
    Array.from(fields).forEach((field) => {
        field.style.background = '#EEE';
        field.style.color = '#EEE';
        field.innerHTML = '-';
    });

    swapPlayer('X');
}
