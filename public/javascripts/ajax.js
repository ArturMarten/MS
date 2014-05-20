$("#wrap a").click(function(){
	var state = this.getAttribute('href');
	window.history.pushState(state, state, state);
	$("#prewrap").load(this.href + " #wrap", function(){
		setComments();
	});
	return false;
})
$("#sorteerimine a").click(function(){
	var state = this.getAttribute('href');
	window.history.pushState(state, state, state);
	$("#prewrap").load(this.href + " #wrap", function(){
		setAjax();
	});
	return false;
})
$("#teemad a").click(function(){
	var state = this.getAttribute('href');
	window.history.pushState(state, state, state);
	$("#prewrap").load(this.href + " #wrap", function(){
		setAjax();
	});
	return false;
})
function setAjax() {
	$("#wrap a").click(function(){
		var state = this.getAttribute('href');
		window.history.pushState(state, state, state);
		$("#prewrap").load(this.href + " #wrap", function(){
			setComments();
		});
		return false;
	})
}
$(window).on('popstate', function() {
	window.location.href = document.location;
});

/*
	var temp = $('');
	temp...
		$("#wrap").replace(temp);
*/