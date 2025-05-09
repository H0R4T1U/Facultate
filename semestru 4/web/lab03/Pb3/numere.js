var board = [
    [1, 2, 3, 4],
    [1, 2, 3, 4],
    [5, 6, 7, 8],
    [5, 6, 7, 8]
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
        if (cell.textContent || selectedCells.length === 2) return;
        cell.textContent = board[i * 4 + j];
        selectedCells.push(cell);
        if (selectedCells.length === 2) {
            var cells = document.querySelectorAll('.cell');
            cells.forEach(function(cell) {
                cell.removeEventListener('click', handleClick(i, j));
            });
            if (selectedCells[0].textContent === selectedCells[1].textContent) {
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
                    selectedCells[0].textContent = '';
                    selectedCells[1].textContent = '';
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
