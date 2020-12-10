$(document).ready(function () {
    const add_button = document.getElementById("AdminCode");
    let x = 0;

    $(add_button).click(function (e) {
        e.preventDefault();
        if(x==0){
            $(add_button).parent('div').parent('div').append('<div id="Code" class="col-md-6">\n' +
                '<input name="adminCode" type="number" class="form-control" placeholder="Ingrese el código para registrarse como administrador">\n' +
                '<br>\n' +
                '</div>')
            x = 1;
        }else{
            document.getElementById("Code").remove();
            x = 0;
        }
    });
});