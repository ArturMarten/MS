window.onload = function() {
	document.getElementById("logisisse").href = "#";
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