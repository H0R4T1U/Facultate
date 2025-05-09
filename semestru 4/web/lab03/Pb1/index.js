function mutaElement(sursaId, destinatieId) {
    var sursa = document.getElementById(sursaId);
    var destinatie = document.getElementById(destinatieId);
    var optiuneSelectata = sursa.options[sursa.selectedIndex];
    sursa.remove(sursa.selectedIndex);
    destinatie.add(optiuneSelectata);
}
