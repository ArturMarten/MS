$("#wrap a").click(function(){
	$("#wrap").load(this.href + " #articlewrap");
	return false
})