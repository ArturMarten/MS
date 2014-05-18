function setComments() {
	var button = document.getElementById("loeVeelKommentaare");
	var myDiv = document.getElementById("commentsBody");
	if (button == null){
		return;
	}
	function show() {
	    myDiv.style.visibility = "visible";
		myDiv.style.display = "block";
	}
	function hide() {
	    myDiv.style.visibility = "hidden";
		myDiv.style.display = "none";
	}
	function toggle() {
	    if (myDiv.style.visibility === "hidden") {
	        show();
	    } else {
	        hide();
	    }
	}
	hide();
	button.addEventListener("click", toggle, false);
}
$(document).ready(setComments);