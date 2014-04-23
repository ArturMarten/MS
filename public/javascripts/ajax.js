$("#wrap a").click(function(){
	//window.location.hash = "article/"+this.href.substring(this.href.lastIndexOf("/")+1,this.href.length);	
	var state = "article/"+this.href.substring(this.href.lastIndexOf("/")+1,this.href.length);
	window.history.pushState(state, state, state);
	$("#wrap").load(this.href + " #wrap");
	return false;
})
$("#sorteerimine a").click(function(){
	var state = this.href.substring(this.href.lastIndexOf("/")+1,this.href.length);
	window.history.pushState(state, state, state);
	$("#wrap").load(this.href + " #wrap");
	return false;
})
$("#teemad a").click(function(){
	var state = this.href.substring(this.href.lastIndexOf("/")+1,this.href.length);
	window.history.pushState(state, state, state);
	$("#wrap").load(this.href + " #wrap");
	return false;
})
window.onpopstate = function(event) {
  window.location.href = document.location;
};

/*
if (window.location.hash) {
	window.location.replace("/" + window.location.hash.substring(1,window.location.hash.length));
};
*/