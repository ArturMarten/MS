$("#wrap a").click(function(){
	var state = this.getAttribute('href');
	window.history.pushState(state, state, state);
	$("#wrap").load(this.href + " #wrap");
	return false;
})
$("#sorteerimine a").click(function(){
	var state = this.getAttribute('href');
	window.history.pushState(state, state, state);
	$("#wrap").load(this.href + " #wrap");
	return false;
})
$("#teemad a").click(function(){
	var state = this.getAttribute('href');
	window.history.pushState(state, state, state);
	$("#wrap").load(this.href + " #wrap");
	return false;
})