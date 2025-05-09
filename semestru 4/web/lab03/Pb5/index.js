
var listItems = document.querySelectorAll('#myList li');
var currentIndex = 0;
var numItems = listItems.length;
var intervalNo;

function showNextItem() {
    listItems[currentIndex].style.display = 'none';
    currentIndex = (currentIndex + 1) % numItems;
    listItems[currentIndex].style.display = 'flex';
    resetInterval();
}

function showPrevItem() {
    listItems[currentIndex].style.display = 'none';
    currentIndex = (currentIndex - 1 + numItems) % numItems;
    listItems[currentIndex].style.display = 'flex';
    resetInterval();
}

document.getElementById('next').addEventListener('click', showNextItem);
document.getElementById('prev').addEventListener('click', showPrevItem);

function resetInterval() {
    clearInterval(intervalNo);
    intervalNo = setInterval(showNextItem, 5000);
}

resetInterval();
