window.fbAsyncInit = function() {
	FB.init({
		appId : '1418739005046928',
		status : true,
		cookie : true,
		xfbml : true
	});
};


(function(d) {
	var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
	if (d.getElementById(id)) {
		return;
	}
	js = d.createElement('script');
	js.id = id;
	js.async = true;
	js.src = "http://connect.facebook.net/et_EE/all.js#xfbml=1&appId=1418739005046928";
	ref.parentNode.insertBefore(js, ref);
}(document));

function facebookLogin(redirect_url) {
	FB.getLoginStatus(function(response) {
		if (response.status === 'connected') {
			FB.api('/me', function (user) {
				var data = {
					email: String(user.email),
					first_name: String(user.first_name),
					last_name: String(user.last_name),
				};
				$.ajax({
					url:"/facebookLogin/"+redirect_url,
					contentType: "application/json",
					type: "POST",
					data: JSON.stringify(data),
					success:function(result){				
						window.location.replace(result);
				}});
			});
		} else {
			FB.login();
		}
	});
}