$(document).ready(function () {
    const wrapper = $('.agent-selector');
    const add_button = $('.add_button');
    const options = $('.agentUserName option');
    let x = 0;
    let y = 0;

    $(add_button).click(function (e) {
        e.preventDefault();
        let s = '<div ' + 'id="' + x + '"' + '><select id="agentUserName" class="form-control" name="agentUsername">';
        $(options).each(function(){
            s = s + '<option value="' + $(this).val() + '">' + $(this).val() + '</option>';
        });
        s = s + '</select><br></div>';
        $(wrapper).append(s);
        document.getElementById("remover").classList.remove("hidden");
        x++;
    });

    $(wrapper).parent().on("click", ".delete", function(e) {
        e.preventDefault();
        document.getElementById(y).remove();
        y++;
        if (y===x){
            document.getElementById("remover").classList.add("hidden");
        }
    });

});