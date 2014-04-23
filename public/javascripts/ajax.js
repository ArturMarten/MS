$("#wrap a").click(function(){
	window.location.hash = "article/"+this.href.substring(this.href.lastIndexOf("/")+1,this.href.length);	
	$("#wrap").load(this.href + " #wrap");
	return false;
})
$("#sorteerimine a").click(function(){
	window.location.hash = this.href.substring(this.href.lastIndexOf("/")+1,this.href.length);
	$("#wrap").load(this.href + " #wrap");
	return false;
})
$("#teemad a").click(function(){
	window.location.hash = this.href.substring(this.href.lastIndexOf("/")+1,this.href.length);
	$("#wrap").load(this.href + " #wrap");
	return false;
})

if (window.location.hash) {
	window.location.replace("/" + window.location.hash.substring(1,window.location.hash.length));
};