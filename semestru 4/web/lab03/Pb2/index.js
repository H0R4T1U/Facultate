
function validare() {
    var nume = document.getElementById('nume');
    var dataNasterii = document.getElementById('dataNasterii');
    var varsta = document.getElementById('varsta');
    var email = document.getElementById('email');
    var mesaj = document.getElementById('mesaj');

    nume.classList.remove('invalid');
    dataNasterii.classList.remove('invalid');
    varsta.classList.remove('invalid');
    email.classList.remove('invalid');
    mesaj.textContent = '';

    if (!nume.value || !dataNasterii.value || !varsta.value || !email.value || varsta.value <= 0) {

        var mesajValidareNume = '';
        var mesajValidareDataNasterii = '';
        var mesajValidareVarsta = '';
        var mesajValidareEmail = '';

        if (!nume.value) {
            nume.classList.add('invalid');
            mesajValidareNume += 'nume';
        }
        if (!dataNasterii.value) {
            dataNasterii.classList.add('invalid');
            mesajValidareDataNasterii += 'data nasterii';

        }
        if (!varsta.value || varsta.value <= 0) {
            varsta.classList.add('invalid');
            mesajValidareVarsta += 'varsta';

        }
        if (!email.value) {
            email.classList.add('invalid');
            mesajValidareEmail += 'email';

        }
        var mesajValidare = '';
        if (mesajValidareNume !== '')
            mesajValidare += mesajValidareNume;
        if (mesajValidareDataNasterii !== '' && mesajValidare !== '' && email.value && varsta.value)
            mesajValidare = mesajValidare + ' si ' + mesajValidareDataNasterii;
        else if (mesajValidareDataNasterii !== '' && mesajValidare !== '')
            mesajValidare = mesajValidare + ', ' + mesajValidareDataNasterii;
        else if (mesajValidareDataNasterii !== '')
            mesajValidare += mesajValidareDataNasterii;
        if (mesajValidareVarsta !== '' && mesajValidare !== '' && email.value)
            mesajValidare = mesajValidare + ' si ' + mesajValidareVarsta;
        else if (mesajValidareVarsta !== '' && mesajValidare !== '')
            mesajValidare = mesajValidare + ', ' + mesajValidareVarsta;
        else if (mesajValidareVarsta !== '')
            mesajValidare += mesajValidareVarsta;
        if (mesajValidareEmail !== '' && mesajValidare !== '')
            mesajValidare = mesajValidare + ' si ' + mesajValidareEmail;
        else if (mesajValidareEmail !== '')
            mesajValidare += mesajValidareEmail;
        mesaj.textContent = `Câmpurile ${mesajValidare} nu sunt completate corect!`;
        return;
}

    mesaj.textContent = 'Datele sunt completate corect';
}
