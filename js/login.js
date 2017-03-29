//=============test if external js is loaded================
/*var len = $('script[src*="js/login.js"]').length; 

	if (len === 0) {
        alert('script not loaded');

        loadScript('js/login.js');

        if ($('script[src*="js/login.js"]').length === 0) {
            alert('still not loaded');
        }
        else {
            alert('loaded now');
        }
    }
    else {
        alert('login.js loaded');
    }*/
	//=============test end======================
function mouseDown() {
    document.getElementById("student-btn").style.backgroundColor = "#e03535";
    document.getElementById("student-btn").style.borderColor = "red";
    document.getElementById("prof-btn").style.backgroundColor = "#e03535";
    document.getElementById("prof-btn").style.borderColor = "red";
    //document.getElementById("prof-schedule").style.backgroundColor = "red";
}

$(document).ready(function(){
    

    $('.form-group').hide();

    $('#student-btn').click(function(){
        $("#student-form").toggle();
    });

    $('#prof-btn').click(function(){
        $('#prof-form').toggle();
    });

});