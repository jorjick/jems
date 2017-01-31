var csrf_token = $('meta[name="_csrf"]').attr('content');
axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf_token;
var login = new Vue({
	el: '#login_form',
	data: {},
	http: {
		headers: {
			token : csrf_token
		}
	},
	methods: {
		login: function() {
			axios.post(
				'login',
				$('#login_form').serialize()
			).then (function(response) {
				var data = response.data
				if (data.success) {
					window.location.assign(data.redirectUrl);
				} else {
					console.log(data);
				}	
			})
			.catch(function(error){
				$('.error').show();
				console.log(error)
			})
		}
	}
});
