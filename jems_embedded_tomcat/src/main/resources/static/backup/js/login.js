/**
 * Created by gora on 1/3/17.
 */
$(document).ready(function(){
    $("#login-btn").click(login)
})
function login() {
    $.ajax({
        type: 'POST',
        url: '/login',
        data: $("#login-form").serialize(),
        cache: false,
        dataType: 'json',
        crossDomain: false,
        success: function (data) {
            var response = jQuery.parseJSON(data);
            if (response.success == true) {
                console.info("Authentication success");
                window.location.href("/");
            } else {
                console.error("Unable to login");
                $("#login-error > label").css("visibility", true).text("login failed");
            }
        }
    });
}
