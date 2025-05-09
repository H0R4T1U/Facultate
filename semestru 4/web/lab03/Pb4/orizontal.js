
function sortTable(columnIndex, tableId) {
    var table, rows, switching, i, x, y, shouldSwitch, direction, switchCount = 0;
    table = document.getElementById(tableId);
    switching = true;
    direction = "asc";
    while (switching) {
        switching = false;
        rows = table.rows;
        for (i = 1; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("TD")[columnIndex];
            y = rows[i + 1].getElementsByTagName("TD")[columnIndex];
            var xVal = isNaN(parseFloat(x.innerHTML)) ? x.innerHTML.toLowerCase() : parseFloat(x.innerHTML);
            var yVal = isNaN(parseFloat(y.innerHTML)) ? y.innerHTML.toLowerCase() : parseFloat(y.innerHTML);
            if (direction == "asc") {
                if (xVal > yVal) {
                    shouldSwitch = true;
                    break;
                }
            } else if (direction == "desc") {
                if (xVal < yVal) {
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            switchCount ++;
        } else {
            if (switchCount == 0 && direction == "asc") {
                direction = "desc";
                switching = true;
            }
        }
    }
}

document.querySelectorAll('#fruitTable th').forEach((header, index) => {
    header.addEventListener('click', () => sortTable(index, 'fruitTable'));
});
document.querySelectorAll('#legumeTable th').forEach((header, index) => {
    header.addEventListener('click', () => sortTable(index, 'legumeTable'));
});
