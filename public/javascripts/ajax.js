window.onload = function() {
     initialiseStateFromURL();
     setInterval(initialiseStateFromURL, 1000);
   }
/*
   var recentHash = "";
   function pollHash() {
  
     if (window.location.hash==recentHash) {
       return; // Nothing's changed since last polled.
     }
     recentHash = window.location.hash;
  
     // URL has changed, update the UI accordingly.
     openTab(initialTab);
  
   }*/
function initialiseStateFromURL() {
 var initialTab = window.location.hash;
 openTab(initialTab);
}
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
	window.location.hash = "";
	$("#wrap").load(this.href + " #wrap");
	return false
})