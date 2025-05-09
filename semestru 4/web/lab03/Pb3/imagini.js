var board = [
    ['./spanac.webp', 'macaroane.jpg', './vanata.jpg', './condiment.jpg'],
    ['./spanac.webp', 'macaroane.jpg', './cartof.jpg', './cartof.jpg'],
    ['rosii.jpg', 'castraveti.jpg', './ceapa.jpg', './condiment.jpg'],
    ['rosii.jpg', 'castraveti.jpg', './ceapa.jpg', './vanata.jpg']
];
board = board.flat().sort(() => Math.random() - 0.5);
var selectedCells = [];
var foundPairs = 0;

function createBoard() {
    var table = document.getElementById('game-board');
    for (var i = 0; i < 4; i++) {
        var row = document.createElement('tr');
        for (var j = 0; j < 4; j++) {
            var cell = document.createElement('td');
            cell.textContent = '';
            cell.classList.add('cell');
            cell.addEventListener('click', handleClick(i, j));
            row.appendChild(cell);
        }
        table.appendChild(row);
    }
}

function handleClick(i, j) {
    return function() {
        var cell = this;
        if (cell.hasChildNodes() || selectedCells.length === 2) return;
        var img = document.createElement('img');
        img.src = board[i * 4 + j];
        img.style.width = '200px';
        img.style.height = '200px';
        cell.appendChild(img);
        selectedCells.push(cell);
        if (selectedCells.length === 2) {
            var cells = document.querySelectorAll('.cell');
            cells.forEach(function(cell) {
                cell.removeEventListener('click', handleClick(i, j));
            });
            if (selectedCells[0].firstChild.src === selectedCells[1].firstChild.src) {
                foundPairs++;
                selectedCells = [];
                if (foundPairs === 8) {
                    setTimeout(function() {
                        alert('You won!');
                    }, 1);
                }
                cells.forEach(function(cell) {
                    cell.addEventListener('click', handleClick(i, j));
                });
            } else {
                setTimeout(function() {
                    selectedCells[0].removeChild(selectedCells[0].firstChild);
                    selectedCells[1].removeChild(selectedCells[1].firstChild);
                    selectedCells = [];
                    cells.forEach(function(cell) {
                        cell.addEventListener('click', handleClick(i, j));
                    });
                }, 500);
            }
        }
    };
}

createBoard();
