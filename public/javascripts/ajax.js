$("#wrap a").click(function(){
	window.location.hash = "article"+this.href.substring(this.href.lastIndexOf("/")+1,this.href.length);
	$("#wrap").load(this.href + " #wrap");
	return false
})
$("#sorteerimine a").click(function(){
	window.location.hash = this.href.substring(this.href.lastIndexOf("/"),this.href.length);
	$("#wrap").load(this.href + " #wrap");
	return false
})
$("#teemad a").click(function(){
	window.location.hash = this.href.substring(this.href.lastIndexOf("/"),this.href.length);
	$("#wrap").load(this.href + " #wrap");
	return false
})
/*
var recentHash = "";
var checkHash = function() {
  var hash = document.location.hash;
  if (hash) {
	hash = hash.substr(1);
	if (hash == recentHash) {
	  return;
	}
	recentHash = hash;
	loadPage(hash);
  }
}
setInterval(checkHash, 1000);
*/