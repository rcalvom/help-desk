$(document).ready(function () {
    const wrapper = $('.add_category');
    const add_button = $('.add_button');
    let x = 0;

    $(add_button).click(function (e) {
        e.preventDefault();
        if(x===0){
                $(wrapper).append('<div class="form-group">\n' +
                '<div class="col-md-2 text-center">\n' +
                '<label class="formLabel" for="category">Nombre: </label>\n' +
                '</div>\n' +
                '<div class="col-md-5">\n' +
                '<input class="form-control " name="name" id="category" placeholder="Ingrese el nombre de la nueva categoria">\n' +
                '</div>\n' +
                '<div class="col-md-2">\n' +
                '<button class="btn btn-primary" type="submit">Agregar</button>\n' +
                '<button class="btn btn-default btn-default delete" type="button">Cancelar</button>\n' +
                '</div>\n' +
                '</div>');
        }
        x++;
    });

    $(wrapper).on("click", ".delete", function(e) {
        e.preventDefault();
        $(this).parent('div').parent('div').remove();
        x = 0;
    });

});