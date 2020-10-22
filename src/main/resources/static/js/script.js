var page = window.location.protocol + "//" + window.location.host + "/" + window.location.pathname;
var newPathname = page.split('/');
page1 = newPathname[4];
$(document).ready(function(page){
    if (page1!="") {
        document.getElementById(page1).className += 'active';
    }
    $('nav ul li a').click(function(){
        page = this;
        $(page).addClass('active');
    });

});