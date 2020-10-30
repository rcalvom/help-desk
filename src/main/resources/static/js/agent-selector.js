$(document).ready(function () {
    const wrapper = $('.agent-selector');
    const add_button = $('.add_button');
    const options = $('.agentUserName option');

    $(add_button).click(function (e) {
        e.preventDefault();
        let s = '<div><select id="agentUserName" class="form-control" name="agentUsername">';
        $(options).each(function(){
            s = s + '<option value="' + $(this).val() + '">' + $(this).val() + "</option>";
        });
        s = s + '</select><button type="button" class="delete">Quitar</button><br></div>';
        $(wrapper).append(s);
    });

    $(wrapper).on("click", ".delete", function(e) {
        e.preventDefault();
        $(this).parent('div').remove();
    });

});