/**
 * Created by gora on 1/12/17.
 */
var csrf_token = $('meta[name="_csrf"]').attr('content');
axios.defaults.headers.common['X-CSRF-TOKEN'] = csrf_token;

var users = new Vue({
	el: '#app',
	data: function() {
		return {
			rows: [],
			dataReady: false
		}
	},
	http: {
		headers: {
			token : csrf_token
		}
	},
	methods: {
		initTable: function() {
			var users = this;
			axios.post('users', {
				dataType: 'json',
			      headers: {
			        'Accept': 'application/json',
			        'Content-Type': 'application/json'
			      }
			})
				.then (function(response) {
					users.rows = response.data.content;
					users.dataReady = true;
					console.log(response.data.content);
				}).catch(function(error){
					console.log(error)
				})
		},
		show_add_user_modal: function() {
			$('#add_user_modal').modal('show');
		},
		save_user: function() {
			var users = this;
			axios.post('users/create',
				$('#add_user_form').serializeJSON()
			)
			.then(function(response) {
				users.initTable();
				console.log(response);
			}).catch(function(error){
				console.log(error);
			})
		}
	}
});


$.fn.serializeJSON=function() {
    var json = {};
    $.map($(this).serializeArray(), function(n, i){
        json[n['name']] = n['value'];
    });
    return json;
};

/*$.fn.serializeJSON = function() {
	var form = $(this);
	var obj = {};
	var elements = form.querySelectorAll( "input, select, textarea" );
	for( var i = 0; i < elements.length; ++i ) {
		var element = elements[i];
		var name = element.name;
		var value = element.value;

		if( name ) {
			obj[ name ] = value;
		}
	}

	return JSON.stringify( obj );
}*/

users.initTable();


