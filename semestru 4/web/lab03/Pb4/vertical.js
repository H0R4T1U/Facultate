
function sortTable(rowIndex, tableId) {
    var table, switching, i, x, y, shouldSwitch, direction, switchCount = 0;
    table = document.getElementById(tableId);
    switching = true;
    direction = "asc";
    while (switching) {
        switching = false;
        for (i = 1; i < (table.rows[rowIndex].cells.length - 1); i++) {
            shouldSwitch = false;
            x = table.rows[rowIndex].cells[i];
            y = table.rows[rowIndex].cells[i + 1];
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
            for (let j = 0; j < table.rows.length; j++) {
                table.rows[j].insertBefore(table.rows[j].cells[i + 1], table.rows[j].cells[i]);
            }
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

document.querySelectorAll('#legumeTable tr td:nth-child(1)').forEach((header, index) => {
    header.addEventListener('click', () => sortTable(index + 1, 'legumeTable'));
});
