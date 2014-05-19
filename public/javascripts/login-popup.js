window.onload = function() {
	try{
		document.getElementById("logisisse").href = "#";
	} catch (err){}
	return false;
};
$(document).ready(function() {
    $(".popup_button").click(function() {
		$(".popup").show();
		document.getElementById("emailInput").focus();
		$(".sulge_popup").click(function() {
			$(".popup").hide();
			
		});
    });
});