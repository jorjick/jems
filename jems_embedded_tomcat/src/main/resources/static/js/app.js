/**
 * Created by gora on 1/5/17.
 */
$.ajaxSetup({
	headers : {
		'X-CSRF-TOKEN' : $('meta[name="csrf-token"]').attr('content')
	}
});

$(function() {
	$('.ui.dropdown').dropdown({
		transition : 'horizontal flip'
	});
});
$(function() {
	$('.ui.checkbox').checkbox();
});


$(function() {
	$('#logout').click(function() {
		logout();
	})
});



function logout() {
	var csrf_token = $('meta[name="_csrf"]').attr('content');
	axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf_token;
	axios.post('/jems/logout')
		.then(function(response) {
			console.log(response);
			window.location.assign("login");
		})
		.catch(function(error) {
			console.log(error)
		})
}