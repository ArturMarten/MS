$("#wrap a").click(function(){
	window.location.hash = "#article";
	$("#wrap").load(this.href + " #articlewrap");
	return false
})
$("#sorteerimine a").click(function(){
	window.location.hash = "#sort";
	$("#wrap").load(this.href + " #wrap");
	return false
})
$("#teemad a").click(function(){
	window.location.hash = "#teemad";
	$("#wrap").load(this.href + " #wrap");
	return false
})