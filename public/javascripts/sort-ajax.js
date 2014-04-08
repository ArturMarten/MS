
$("#sorteerimine a").click(function(){
	$("#wrap").load(this.href + " #wrap");
	return false
})	